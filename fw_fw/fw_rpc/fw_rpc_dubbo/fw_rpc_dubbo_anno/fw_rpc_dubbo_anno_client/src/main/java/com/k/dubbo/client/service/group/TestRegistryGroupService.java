package com.k.dubbo.client.service.group;


import com.k.dubbo.client.model.Person;

import java.util.List;

public interface TestRegistryGroupService {
    List<String> helloGroup1(String name);

    List<String>  helloGroup2(Person persion);
}