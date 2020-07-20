package com.example.server.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class PropertiesVO implements Serializable {

    private String application;
    private String profile;
    private String label;
    private String key;
    private String value;
}
