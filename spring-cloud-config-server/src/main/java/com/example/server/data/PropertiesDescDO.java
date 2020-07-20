package com.example.server.data;

import lombok.Data;

@Data
public class PropertiesDescDO {

    private Long id;
    private String application;
    private String profile;
    private String label;
    private String type;
    private String value;
    private Long rel;
}
