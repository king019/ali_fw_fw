package com.k.dubbo.service.impl;

import com.k.dubbo.client.model.Person;
import com.k.dubbo.client.service.single.TestRegistrySingleService;
import com.k.dep.common.util.NetworkUtils;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
        //(version = "2.2")
//(executes = 10 ,delay = 10)
public class TestRegistrySingleServiceImpl2 implements TestRegistrySingleService {
    @Override
    public String helloSingle1(String name) throws  Exception {
        return NetworkUtils.getHostIP() + name + "_OK";
    }

    @Override
    public String helloSingle2(Person persion) {
        return NetworkUtils.getHostIP() + persion.toString();
    }
}
