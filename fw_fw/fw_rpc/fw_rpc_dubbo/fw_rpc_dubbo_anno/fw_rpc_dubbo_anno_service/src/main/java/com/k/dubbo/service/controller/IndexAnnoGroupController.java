package com.k.dubbo.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

@Slf4j
@Controller
public class IndexAnnoGroupController implements Serializable {

    @GetMapping("/index")
    public String index(String name) throws  Exception {
        return name;
    }
}
