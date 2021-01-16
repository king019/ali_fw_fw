package com.k.dubbo.service.impl;

import com.k.dubbo.client.model.Person;
import com.k.dubbo.client.service.group.TestRegistryGroupService;
import com.k.dep.common.util.NetworkUtils;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@DubboService
        //(group = "aa", connections = 10,loadbalance = "leastactive")
public class TestRegistryGroupServiceImpl1 implements TestRegistryGroupService {
    @Override
    public List<String> helloGroup1(String name) {
        return List.of(NetworkUtils.getHostIP() + name);
    }

    @Override
    public List<String> helloGroup2(Person persion) {
        return List.of(NetworkUtils.getHostIP() + persion.toString());
    }
}
