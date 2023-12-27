package com.huawei.c4demo.service;

import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.LoginVO;
import com.huawei.c4demo.pojo.RegisterVO;

public interface LoginService {
    ServerResponse login(LoginVO loginVO);

    ServerResponse register(RegisterVO registerVO);
}
