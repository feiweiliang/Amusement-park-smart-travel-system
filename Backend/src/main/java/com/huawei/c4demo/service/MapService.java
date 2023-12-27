package com.huawei.c4demo.service;

import com.huawei.c4demo.common.ServerResponse;

public interface MapService {

    ServerResponse getMap();
    ServerResponse getGroundDesc(Integer id);
    ServerResponse getGroundWaitTime();
}
