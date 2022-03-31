package com.zhumuchang.dongqu.mapper.commodity;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.commodity.SesameCategory;
import org.apache.ibatis.annotations.Mapper;
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

}
