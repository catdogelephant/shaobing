package com.zhumuchang.dongqu.api.service.commodity;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodityCategorySort;

/**
 * <p>
 * 商品品类排序表 服务类
 * </p>
 *
 * @author sx
 * @since 2022-07-11
 */
public interface SesameCommodityCategorySortService extends IService<SesameCommodityCategorySort> {

    /**
     * 根据商品主键ID和品类主键ID获取排序表中关联的数量
     *
     * @param commodityId 商品主键ID
     * @param categoryId  品类主键ID
     * @return 关联的数量
     */
    Integer countByCommodityIdAndCategoryId(Integer commodityId, Integer categoryId);

    /**
     * 创建对象
     *
     * @param shopId      店铺主键ID
     * @param commodityId 商品主键ID
     * @param categoryId  品类主键ID
     * @param sort        序号
     * @param userId      操作人ID
     * @param userName    操作人姓名
     * @return 对象实体
     */
    SesameCommodityCategorySort createBean(Integer shopId, Integer commodityId, Integer categoryId, Integer sort, String userId, String userName);

    /**
     * 获取该店铺在当前品类下已存在的商品数量
     *
     * @param shopId     店铺主键ID
     * @param categoryId 品类主键ID
     * @return 商品数量
     */
    Integer countByShopIdAndCategoryId(Integer shopId, Integer categoryId);
}
