package com.zhumuchang.dongqu.mapper.commodity;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCategory;
import com.zhumuchang.dongqu.api.dto.commodity.resp.RespCategoryPageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * <p>
 * 品类 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-03-31
 */
@Repository
@Mapper
public interface SesameCategoryMapper extends BaseMapper<SesameCategory> {

    /**
     * 获取正常状态的品类数量
     *
     * @return 品类数量
     */
    Integer countByNotDel();

    /**
     * 获取正常状态下重名的品类数量
     *
     * @param name 品类名称
     * @return 品类数量
     */
    Integer countByName(@Param("name") String name);

    /**
     * 根据品类ID更新品类停启用状态
     *
     * @param categoryId 品类ID
     * @param enable     停启用状态 0.停用 1.启用
     * @param updateId   更新人ID
     * @return true：成功 false：失败
     */
    Boolean updateEnableById(@Param("categoryId") Integer categoryId, @Param("enable") Integer enable, @Param("updateId") String updateId);

    /**
     * 根据对外ID获取品类信息
     *
     * @param openId 品类对外ID
     * @return 品类信息
     */
    SesameCategory getByOpenId(String openId);

    /**
     * 品类分页列表
     *
     * @param page           分页
     * @param categoryName   品类名称
     * @param categoryEnable 品类状态 0.停用 1.启用
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @return 分页列表
     */
    Page<RespCategoryPageDto> categoryPage(@Param("page") Page<RespCategoryPageDto> page, @Param("categoryName") String categoryName,
                                           @Param("categoryEnable") Integer categoryEnable,
                                           @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 根据ID获取品类详情
     *
     * @param openId 品类对外ID
     * @return 品类详情
     */
    RespCategoryPageDto categoryDetailByOpenId(String openId);

    /**
     * 根据对外ID删除品类
     *
     * @param openId   品类对外ID
     * @param updateId 操作人ID
     */
    void delCategoryByOpenId(@Param("updateId") String updateId, @Param("openId") String openId);
}
