package com.k.dubbo.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class Person implements Serializable {
    private String id;
    private String name;
}
