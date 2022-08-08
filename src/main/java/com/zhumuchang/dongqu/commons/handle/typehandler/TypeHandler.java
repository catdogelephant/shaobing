package com.zhumuchang.dongqu.commons.handle.typehandler;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.zhumuchang.dongqu.api.dto.testdto.Encrypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author sx
 * @Description mybatis加解密类处理器
 * @MappedJdbcTypes 该注解：表示处理器处理的Jdbc类型
 * @MappedTypes 该注解：表示该处理器处理的java类型是什么
 * @Date 2022/8/8 10:28
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(Encrypt.class)
public class TypeHandler extends BaseTypeHandler<Encrypt> {

    /**
     * 密钥
     */
    private static final byte[] KEYS = "12345678asdfghjk".getBytes(StandardCharsets.UTF_8);

    /**
     * 设置参数
     *
     * @param preparedStatement
     * @param i
     * @param encrypt
     * @param jdbcType
     * @throws SQLException sql异常
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Encrypt encrypt, JdbcType jdbcType) throws SQLException {
        if (encrypt == null || StringUtils.isBlank(encrypt.getValue())) {
            preparedStatement.setString(i, null);
            return;
        }
        AES aes = SecureUtil.aes(KEYS);
        String encryptHex = aes.encryptHex(encrypt.getValue());
        preparedStatement.setString(i, encryptHex);
    }

    /**
     * 获取值
     *
     * @param resultSet
     * @param columnName
     * @return
     * @throws SQLException sql异常
     */
    @Override
    public Encrypt getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return decrypt(resultSet.getString(columnName));
    }

    /**
     * 获取值
     *
     * @param resultSet
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public Encrypt getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return decrypt(resultSet.getString(columnIndex));
    }

    /**
     * 获取值
     *
     * @param callableStatement
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public Encrypt getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return decrypt(callableStatement.getString(columnIndex));
    }

    /**
     * 解密
     *
     * @param encryptString 加密串
     * @return 加解密类，value值为解密后的值
     */
    public Encrypt decrypt(String encryptString) {
        if (StringUtils.isBlank(encryptString)) {
            return null;
        }
        return new Encrypt(SecureUtil.aes(KEYS).decryptStr(encryptString));
    }
}
