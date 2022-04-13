package com.zhumuchang.dongqu.mapper.commodity;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
}
