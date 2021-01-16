package com.k.dubbo.service.impl;

import com.k.dubbo.client.model.Person;
import com.k.dubbo.client.service.group.TestRegistryGroupService;
import com.k.dep.common.util.NetworkUtils;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@DubboService
        //(group = "bb",loadbalance = "consistenthash")
public class TestRegistryGroupServiceImpl2 implements TestRegistryGroupService {
    @Override
    public List<String> helloGroup1(String name) {
        return List.of( NetworkUtils.getHostIP() + name);
    }

    @Override
    public List<String> helloGroup2(Person persion) {
        return  List.of(NetworkUtils.getHostIP() + persion.toString());
    }}
