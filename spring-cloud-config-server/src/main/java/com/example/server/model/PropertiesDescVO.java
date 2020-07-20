package com.example.server.model;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;

@Data
public class PropertiesDescVO implements Serializable {

    private static final long serialVersionUID = 6298347201995414634L;


    private Long id;
    private String application;
    private String profile;
    private String label;
    private Type type;
    private String value;
    private PropertiesDescVO rel;

    public enum Type {
        PROPERTIES("properties"),
        YAML("yaml");

        @Getter
        private String type;

        Type(String type) {
            this.type = type;
        }
    }
}
