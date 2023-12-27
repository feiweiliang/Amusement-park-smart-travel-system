package com.huawei.c4demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class FavoriteVO {
    private Integer userId;
    private List<Integer> groundIds;
}
