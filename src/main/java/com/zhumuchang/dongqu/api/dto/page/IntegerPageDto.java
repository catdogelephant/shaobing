package com.zhumuchang.dongqu.api.dto.page;

import com.zhumuchang.dongqu.api.bean.PageBean;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 字符串请求分页参数
 * @Date 2022/10/17 18:41
 */
@Data
public class IntegerPageDto extends PageBean implements Serializable {
    private static final long serialVersionUID = 2406644665228241785L;

    private Integer intParam;
}
