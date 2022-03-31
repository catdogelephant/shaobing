package com.zhumuchang.dongqu.mapper.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhumuchang.dongqu.api.bean.user.SesameSystemUser;
import com.zhumuchang.dongqu.api.dto.user.req.LoginDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 管理员 Mapper 接口
 * </p>
 *
 * @author sx
 * @since 2022-03-12
 */
@Repository
@Mapper
public interface SystemUserMapper extends BaseMapper<SesameSystemUser> {

    /**
     * 登录
     *
     * @param param 请求参数
     * @return userId
     */
    SesameSystemUser login(@Param("param") LoginDto param);

    /**
     * 新增系统管理员并返回主键id
     *
     * @param sesameSystemUser 请求参数
     * @return 主键id
     */
    Integer insertSystemUser(@Param("systemUser") SesameSystemUser sesameSystemUser);
}
