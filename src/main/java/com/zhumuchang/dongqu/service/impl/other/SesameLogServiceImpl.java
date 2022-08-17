package com.zhumuchang.dongqu.service.impl.other;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.other.SesameLog;
import com.zhumuchang.dongqu.api.service.other.SesameLogService;
import com.zhumuchang.dongqu.mapper.other.SesameLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-08-16
 */
@Slf4j
@Service
public class SesameLogServiceImpl extends ServiceImpl<SesameLogMapper, SesameLog> implements SesameLogService {

    @Resource
    private ThreadPoolTaskExecutor threadPool;

    /**
     * 异步保存操作日志
     *
     * @param operLog    保存内容
     * @param requestLog 请求接口
     */
    @Override
    public void asyncSave(SesameLog operLog, String requestLog) {
        threadPool.execute(() -> {
            try {
                boolean save = this.save(operLog);
                if (!save) {
                    log.info("异步保存操作日志 - 请求接口：{} - 保存失败 - operLog={}", requestLog, JSONObject.toJSONString(operLog));
                }
            } catch (Exception e) {
                log.info("异步保存操作日志 - 请求接口：{} - 保存异常 - operLog={}", requestLog, JSONObject.toJSONString(operLog));
                log.error("异步保存操作日志 - 异常", e);
            }
        });
    }
}
