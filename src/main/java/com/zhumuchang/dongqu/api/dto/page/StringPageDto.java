package com.zhumuchang.dongqu.api.dto.page;

import com.zhumuchang.dongqu.api.bean.PageBean;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author sx
 * @Description 字符串请求分页参数
 * @Date 2022/7/15 15:11
 */
@Data
public class StringPageDto extends PageBean implements Serializable {
    private static final long serialVersionUID = 1614019878634193687L;

    private String strParam;
}
