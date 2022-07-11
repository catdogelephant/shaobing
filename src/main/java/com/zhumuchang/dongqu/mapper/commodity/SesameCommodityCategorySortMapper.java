package com.zhumuchang.dongqu.mapper.commodity;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodityCategorySort;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 商品品类排序表 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-07-11
 */
@Repository
@Mapper
public interface SesameCommodityCategorySortMapper extends BaseMapper<SesameCommodityCategorySort> {

    /**
     * 根据商品主键ID和品类主键ID获取排序表中关联的数量
     *
     * @param commodityId 商品主键ID
     * @param categoryId  品类主键ID
     * @return 关联的数量
     */
    Integer countByCommodityIdAndCategoryId(@Param("commodityId") Integer commodityId, @Param("categoryId") Integer categoryId);

    /**
     * 获取该店铺在当前品类下已存在的商品数量
     *
     * @param shopId     店铺主键ID
     * @param categoryId 品类主键ID
     * @return 商品数量
     */
    Integer countByShopIdAndCategoryId(@Param("shopId") Integer shopId, @Param("categoryId") Integer categoryId);
}
