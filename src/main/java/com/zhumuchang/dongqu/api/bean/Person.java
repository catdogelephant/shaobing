package com.zhumuchang.dongqu.api.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "person")
@Data
public class Person implements Serializable{
    private static final long serialVersionUID = -4844279736597447508L;

    private String name;

    private List<Integer> petList;
}
