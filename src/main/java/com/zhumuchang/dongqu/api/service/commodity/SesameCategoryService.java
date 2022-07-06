package com.zhumuchang.dongqu.api.service.commodity;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCategory;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqCategoryPageDto;
import com.zhumuchang.dongqu.api.dto.commodity.req.ReqOneParamDto;
import com.zhumuchang.dongqu.api.dto.user.ResultDto;
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

    /**
     * 停启用品类
     *
     * @param param     品类ID
     * @param tokenUser tokenUser
     * @return 响应对象
     */
    ResultDto enableCategory(ReqOneParamDto param, TokenUser tokenUser);

    /**
     * 品类分页列表
     *
     * @param param 请求参数
     * @return 品类分页列表
     */
    ResultDto categoryPage(ReqCategoryPageDto param);
}
