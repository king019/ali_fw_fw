package com.k.dubbo.client.service.notify;


import com.k.dubbo.client.model.Person;

public interface Nofify {
    void onreturn(Person msg, String id);
    void onthrow(Throwable ex, String id);
}
