package com.zhumuchang.dongqu.api.service.commodity;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCategory;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqOneParamDto;
import com.zhumuchang.dongqu.config.interceptor.TokenUser;

/**
 * <p>
 * 品类 服务类
 * </p>
 *
 * @author sx
 * @since 2022-03-31
 */
public interface SesameCategoryService extends IService<SesameCategory> {

    /**
     * 新增品类
     *
     * @param param     品类名称
     * @param tokenUser tokenUser
     * @return 异常信息
     */
    String addCategory(ReqOneParamDto param, TokenUser tokenUser);
}
