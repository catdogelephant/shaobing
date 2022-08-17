package com.zhumuchang.dongqu.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.other.SesameLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-08-16
 */
@Repository
@Mapper
public interface SesameLogMapper extends BaseMapper<SesameLog> {

}
