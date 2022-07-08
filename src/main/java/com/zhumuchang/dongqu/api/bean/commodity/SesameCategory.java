package com.zhumuchang.dongqu.api.bean.commodity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 品类
 * </p>
 *
 * @author sx
 * @since 2022-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SesameCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对外id
     */
    private String openId;

    /**
     * 品类名称
     */
    private String name;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 停启用 0.停用 1.启用
     */
    private Integer enable;

    /**
     * 删除状态 0.删除 1.正常
     */
    private Integer delFlag;

    /**
     * 乐观锁
     */
    private String revision;

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
