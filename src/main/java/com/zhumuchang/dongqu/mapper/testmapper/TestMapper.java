package com.zhumuchang.dongqu.mapper.testmapper;

import com.zhumuchang.dongqu.api.dto.testdto.Encrypt;
import com.zhumuchang.dongqu.api.dto.user.resp.SystemUserEncryptPhoneDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Author sx
 * @Description 测试Mapper类
 * @Date 2022/8/8 11:04
 */
@Repository
@Mapper
public interface TestMapper {

    /**
     * 更新用户手机号并对手机号加密
     *
     * @param userId 用户主键ID
     * @param phone  用户未加密手机号
     * @return 更新条数
     */
    Integer updateEncryptUserPhone(@Param("userId") Integer userId, @Param("phone") Encrypt phone);

    /**
     * 根据手机号获取系统用户
     *
     * @param phone 手机号加密类
     * @return 系统用户
     */
    SystemUserEncryptPhoneDto findSesameSystemUserDtoByPhone(@Param("phone") Encrypt phone);
}
