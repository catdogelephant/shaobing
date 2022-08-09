package com.zhumuchang.dongqu.commons.service;

import com.zhumuchang.dongqu.service.feign.LogsFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <br>
 * created date 9.3 14:30
 */
@Slf4j
@Service
public class DataOperateServiceImpl implements DataOperateService {

    @Resource
    private LogsFeignService logsFeignService;

    @Override
    public void insertLogsRecord(String sql, String sqlCommandType, String databaseName, String tokenUserId, String tokenUserName) {
        logsFeignService.testLogs(sql, sqlCommandType, databaseName, tokenUserId, tokenUserName);
    }
}
