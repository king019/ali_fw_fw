package com.k.dubbo.anno.api.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.k.dubbo.client.callback.CallbackListener;
import com.k.dubbo.client.callback.CallbackService;
import com.k.dubbo.client.service.group.TestRegistryGroupService;
import com.k.dubbo.client.service.single.DubboCheckFalse;
import com.k.dubbo.client.service.single.TestRegistrySingleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Controller
public class IndexAnnoGroupController implements Serializable {

    //@Reference(retries = 1, cluster = "", loadbalance = "",url = "直接连接",validation = "true")
    //@Reference(retries = 1, cluster = "", loadbalance = "", validation = "true", cache = "", callbacks = 1, mock = "", version = "111", check = false)
    //@DubboReference(check = false)
    private DubboCheckFalse dubboCheckFalse;
    @DubboReference(cache = "lru")
            //(version = "2.2" )
    private TestRegistrySingleService testRegistrySingleService1;
    //@DubboReference(version = "2.2" )
    private TestRegistrySingleService testRegistrySingleService2;
    //@DubboReference(group = "aa",stub = "com.k.dubbo.anno.api.stub.TestRegistryGroupServiceStubImpl",mock = "force:com.k.dubbo.anno.api.stub.TestRegistryGroupServiceMockImpl")
    //@DubboReference(group = "aa,bb", merger = "true"  )
    private TestRegistryGroupService testRegistryGroupService;
    //@DubboReference
    private CallbackService callbackService;

    public IndexAnnoGroupController() {
    }

    @GetMapping("/index")
    @ResponseBody
    @SentinelResource
    public String index(String name) throws  Exception {
//        callbackService.addListener("key", new CallbackListener() {
//            @Override
//            public void changed(String msg) {
//                log.info("msg:{}", msg);
//            }
//        });
        name = testRegistrySingleService1.helloSingle1(name);
       //name = testRegistrySingleService2.helloSingle1(name);
        //name = testRegistryGroupService.helloGroup1(name).toString();
        Future fooFuture = RpcContext.getContext().getFuture();
        log.info("xx==" + name + fooFuture);
        return name;
    }
}
