package com.zhumuchang.dongqu.service.impl.commodity;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCommodityCategorySort;
import com.zhumuchang.dongqu.api.service.commodity.SesameCommodityCategorySortService;
import com.zhumuchang.dongqu.mapper.commodity.SesameCommodityCategorySortMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 商品品类排序表 服务实现类
 * </p>
 *
 * @author sx
 * @since 2022-07-11
 */
@Service
public class SesameCommodityCategorySortServiceImpl extends ServiceImpl<SesameCommodityCategorySortMapper, SesameCommodityCategorySort> implements SesameCommodityCategorySortService {

    @Autowired
    private SesameCommodityCategorySortMapper sesameCommodityCategorySortMapper;

    /**
     * 根据商品主键ID和品类主键ID获取排序表中关联的数量
     *
     * @param commodityId 商品主键ID
     * @param categoryId  品类主键ID
     * @return 关联的数量
     */
    @Override
    public Integer countByCommodityIdAndCategoryId(Integer commodityId, Integer categoryId) {
        Integer count = sesameCommodityCategorySortMapper.countByCommodityIdAndCategoryId(commodityId, categoryId);
        return count;
    }

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
    @Override
    public SesameCommodityCategorySort createBean(Integer shopId, Integer commodityId, Integer categoryId, Integer sort, String userId, String userName) {
        SesameCommodityCategorySort sesameCommodityCategorySort = new SesameCommodityCategorySort();
        sesameCommodityCategorySort.setSesameShopId(shopId);
        sesameCommodityCategorySort.setSesameCommodityId(commodityId);
        sesameCommodityCategorySort.setSesameCategoryId(categoryId);
        sesameCommodityCategorySort.setSort(sort);
        sesameCommodityCategorySort.setDelFlag(1);
        sesameCommodityCategorySort.setCreatedId(userId);
        sesameCommodityCategorySort.setCreatedName(userName);
        sesameCommodityCategorySort.setCreatedTime(LocalDateTime.now());
        sesameCommodityCategorySort.setUpdatedId(userId);
        sesameCommodityCategorySort.setUpdatedName(userName);
        return sesameCommodityCategorySort;
    }

    /**
     * 获取该店铺在当前品类下已存在的商品数量
     *
     * @param shopId     店铺主键ID
     * @param categoryId 品类主键ID
     * @return 商品数量
     */
    @Override
    public Integer countByShopIdAndCategoryId(Integer shopId, Integer categoryId) {
        Integer count = sesameCommodityCategorySortMapper.countByShopIdAndCategoryId(shopId, categoryId);
        return count;
    }
}
