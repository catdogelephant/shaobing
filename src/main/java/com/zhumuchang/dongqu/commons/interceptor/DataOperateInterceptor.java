package com.zhumuchang.dongqu.commons.interceptor;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.handlers.AbstractSqlParserHandler;
import com.zhumuchang.dongqu.commons.service.DataOperateServiceImpl;
import com.zhumuchang.dongqu.commons.utils.RequestLocal;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static com.baomidou.mybatisplus.core.toolkit.StringPool.SEMICOLON;
import static com.zhumuchang.dongqu.commons.constants.SqlConst.*;
import static com.zhumuchang.dongqu.commons.utils.SqlUtils.*;

/**
 * @Author sx
 * @Description 数据库操作拦截器
 * @Date 2022/8/9 11:43
 */
@Slf4j
@AllArgsConstructor
@Intercepts({@Signature(type = StatementHandler.class, method = "update", args = {Statement.class})
        , @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
@Component
public class DataOperateInterceptor extends AbstractSqlParserHandler implements Interceptor {

    @Value("${shaobing.intercept.tableNames:null}")
    private String[] tableNames;

    private List<String> tableNameList;

    @Resource
    private ThreadPoolTaskExecutor taskExecutor;

    @Resource
    private DataOperateServiceImpl dataOperateServiceImpl;

    @PostConstruct
    private void init() {
        tableNameList = Arrays.asList(tableNames);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            //获取用户信息
            String tokenUserId = RequestLocal.getTokenUserId();
            String tokenUserName = RequestLocal.getTokenUserName();
            log.info("tokenUserId:" + tokenUserId);
            log.info("tokenUserName:" + tokenUserName);
            //获取Statement
            Statement statement = getStatement(invocation);
            //获取执行sql
            String originalSql = manageSql(statement);
            log.info("执行sql:" + originalSql);
            //判断是否是需要执行的表名
            String tableName = getTableName(originalSql);
            log.info("表名:" + tableName);
            if (StringUtils.isBlank(tableName)) {
                return invocation.proceed();
            }
            if (StringUtils.isNotBlank(tableName) && tableNameList != null && !tableNameList.contains(tableName.toLowerCase())) {
                return invocation.proceed();
            }
            //获取sql执行类型
            SqlCommandType sqlCommandType = getSqlCommandType(invocation.getTarget());
            //处理数据
            manageData(originalSql, statement, sqlCommandType, tokenUserId, tokenUserName);
        } catch (Exception e) {
            log.error("数据库操作拦截器异常：", e);
            return invocation.proceed();
        }
        long endTime = System.currentTimeMillis();
        log.info("执行耗时:{}", endTime - startTime);
        //继续执行
        return invocation.proceed();
    }

    /**
     * 处理数据
     */
    private void manageData(String originalSql, Statement statement, SqlCommandType sqlCommandType, String tokenUserId, String tokenUserName) {
        //获取目标库
        String databaseName = null;
        try {
            databaseName = statement.getConnection().getMetaData().getURL();
            databaseName = databaseName.substring(databaseName.lastIndexOf("/") + 1, databaseName.indexOf("?"));
            log.info("目标库:" + databaseName);
        } catch (Exception e) {
            log.error("获取数据库连接信息失败: ", e);
        }
        //使用
        String finalDatabaseName = databaseName;
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                manageData(originalSql, sqlCommandType, finalDatabaseName, tokenUserId, tokenUserName);
            }
        });
    }

    /**
     * 处理数据
     *
     * @param originalSql    sql语句解析
     * @param sqlCommandType sql执行类型
     */
    private void manageData(String originalSql, SqlCommandType sqlCommandType, String databaseName, String tokenUserId, String tokenUserName) {
        String type = "";
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            type = INSERT;
        }
        if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
            type = UPDATE;
        }
        if (SqlCommandType.DELETE.equals(sqlCommandType)) {
            type = DELETE;
        }
        List<String> sqlList = null;
        //判断sql是否是values的批量插入
        if (originalSql.toLowerCase().contains(VALUES)) {
            sqlList = resolveBatchSql(originalSql);
        }
        //判断sql是否是批量的修改
        else if (originalSql.contains(SEMICOLON) && originalSql.toUpperCase().contains(UPDATE)) {
            sqlList = Arrays.asList(originalSql.split(SEMICOLON));
        }
        if (sqlList != null && !sqlList.isEmpty()) {
            for (String sql : sqlList) {
                if (StringUtils.isBlank(sql)) {
                    continue;
                }
                dataOperateServiceImpl.insertLogsRecord(sql, type, databaseName, tokenUserId, tokenUserName);
            }
        } else {
            dataOperateServiceImpl.insertLogsRecord(originalSql, type, databaseName, tokenUserId, tokenUserName);
        }

    }

    /**
     * 获取Statement
     *
     * @param invocation invocation
     * @return Statement
     */
    private Statement getStatement(Invocation invocation) {
        Statement statement = null;
        Object firstArg = invocation.getArgs()[0];
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement) firstArg;
        }
        MetaObject stmtMetaObject = SystemMetaObject.forObject(statement);
        if (stmtMetaObject.hasGetter("delegate")) {
            try {
                statement = (Statement) stmtMetaObject.getValue("delegate");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statement;
    }

    /**
     * 获取sql执行类型
     *
     * @param target target
     * @return sql执行类型
     */
    private SqlCommandType getSqlCommandType(Object target) {
        StatementHandler statementHandler = PluginUtils.realTarget(target);
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        return mappedStatement.getSqlCommandType();
    }

    /**
     * 处理sql语句
     *
     * @param statement statement
     * @return 处理后的sql语句
     */
    private String manageSql(Statement statement) {
        //获取执行sql
        String originalSql = String.valueOf(statement);
        originalSql = originalSql.replaceAll("[\\s]", StringPool.SPACE);
        int index = indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }
        originalSql = originalSql.replace("where", "WHERE");
        return originalSql;
    }
}
