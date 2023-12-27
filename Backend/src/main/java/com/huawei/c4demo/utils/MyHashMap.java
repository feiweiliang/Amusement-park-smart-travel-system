package com.huawei.c4demo.utils;

import java.util.HashMap;

/**
 * 实现对于相同的key值，不做覆盖，将重复key的value值，叠加起来
 */
public class MyHashMap<K>  extends HashMap<K,Integer> {
    @Override
    public Integer put(K key, Integer value) {

        //  定义一个新的value 接收 后面put的新的value值
        Integer NewVaule = value;

        //containsKey  判断这个 key  是否已经存在？
        if (containsKey(key)){

            // 获得旧的value 值
            Integer oldValue = get(key);

            //将旧值 和 后面put 的新值相加
            NewVaule = oldValue + NewVaule;
        }

        // 返回相加后的newvalue
        return super.put(key, NewVaule);
    }
}
