package com.example.server.model;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class ApplicationVO implements Serializable {

    private static final long serialVersionUID = 5948980637181115694L;

    private Long id;
    private String name;
    private String desc;
    private Instant date;
}
