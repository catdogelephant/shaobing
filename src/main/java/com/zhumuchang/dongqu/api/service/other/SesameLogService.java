package com.zhumuchang.dongqu.api.service.other;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.other.SesameLog;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author sx
 * @since 2022-08-16
 */
public interface SesameLogService extends IService<SesameLog> {

    /**
     * 异步保存操作日志
     *
     * @param operLog    保存内容
     * @param requestLog 请求接口
     */
    void asyncSave(SesameLog operLog, String requestLog);

}
