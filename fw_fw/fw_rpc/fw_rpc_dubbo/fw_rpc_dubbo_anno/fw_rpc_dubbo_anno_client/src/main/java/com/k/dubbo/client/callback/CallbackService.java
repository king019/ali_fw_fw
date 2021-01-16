package com.k.dubbo.client.callback;


public interface CallbackService {
    void addListener(String key, CallbackListener listener);
}