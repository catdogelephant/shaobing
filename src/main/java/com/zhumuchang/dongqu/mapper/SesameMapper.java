package com.zhumuchang.dongqu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author sx
 * @Description 基Mapper
 * @Date 2022/7/7 18:37
 */
@Repository
@Mapper
public interface SesameMapper {

    /**
     * 根据表名和对外ID获取主键ID
     *
     * @param openId    对外ID
     * @param tableName 表名
     * @return 主键ID
     */
    Integer getIdByOpenId(@Param("openId") String openId, @Param("tableName") String tableName);
    Integer getNotDelIdByOpenId(@Param("openId") String openId, @Param("tableName") String tableName);
}
