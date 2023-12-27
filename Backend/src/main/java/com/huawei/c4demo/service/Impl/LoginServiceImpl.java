package com.huawei.c4demo.service.Impl;

import com.huawei.c4demo.common.ServerResponse;
import com.huawei.c4demo.pojo.LoginVO;
import com.huawei.c4demo.pojo.RegisterVO;
import com.huawei.c4demo.pojo.Usertab;
import com.huawei.c4demo.repository.UserRepository;
import com.huawei.c4demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ServerResponse login(LoginVO loginVO) {
        String mobile_phone = loginVO.getMobile_phone();
        String password = loginVO.getPassword();
//        System.out.println(mobile_phone+"  "+password);
        Usertab user = userRepository.findByMobilePhoneAndPassword(mobile_phone, password);
        if(user != null){
            Integer id = user.getId();
            HashMap<String, String> resMap = new HashMap<>();

            resMap.put("id",id.toString());
            resMap.put("phone",mobile_phone);

            return ServerResponse.success(resMap);
//            System.out.println("login");
        }else{
            return ServerResponse.error(3,"用户名或密码错误");
//            System.out.println("error");
        }
    }

    @Override
    public ServerResponse register(RegisterVO registerVO) {
        String mobile_phone = registerVO.getMobile_phone();
        String password = registerVO.getPassword();
        String mac = registerVO.getMac();

        Usertab oldUser = userRepository.findByMobilePhone(mobile_phone);
        if(oldUser != null){
            return ServerResponse.error(4,"该手机号已被注册");
        }
        Usertab user = new Usertab();
        user.setMobile_phone(mobile_phone);
        user.setMac(mac);
        user.setPassword(password);
        user.setRight(0);
        userRepository.save(user);
        userRepository.flush();

        HashMap<String, String> resMap = new HashMap<>();

        resMap.put("id",user.getId().toString());
        resMap.put("phone",mobile_phone);

        return ServerResponse.success(resMap);
    }
}
