package com.k.dubbo.client.service.single;


import com.k.dubbo.client.model.Person;

public interface TestRegistrySingleService {
    String helloSingle1(String name) throws  Exception;

    String helloSingle2(Person persion);
}