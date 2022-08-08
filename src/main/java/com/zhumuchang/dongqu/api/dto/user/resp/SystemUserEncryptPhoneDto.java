package com.zhumuchang.dongqu.api.dto.user.resp;

import com.zhumuchang.dongqu.api.dto.testdto.Encrypt;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author sx
 * @Description 系统用户
 * @Date 2022/8/8 11:42
 */
@Data
public class SystemUserEncryptPhoneDto implements Serializable {
    private static final long serialVersionUID = 1022515758775437285L;

    /**
     * id
     */
    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 管理员姓名
     */
    private String name;

    /**
     * 管理员手机号
     */
    private Encrypt phone;

    /**
     * 管理员账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建人id
     */
    private String createdId;

    /**
     * 创建人
     */
    private String createdName;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新人id
     */
    private String updatedId;

    /**
     * 更新人
     */
    private String updatedName;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
}
