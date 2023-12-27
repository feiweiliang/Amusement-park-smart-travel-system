package com.huawei.c4demo.pojo.apjsonrelevant;

import lombok.Data;

@Data
public class ValidatorData {

    //根据云平台要求的校验格式 新建entity类
    //在此编码
    private String validator;
    public ValidatorData(String validator) {
        this.validator = validator;
    }

}
