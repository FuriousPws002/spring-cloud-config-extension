package com.example.server.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.model.ApplicationVO;
import com.example.server.service.ApplicationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public void post(@RequestBody ApplicationVO applicationVO) {

    }

    @DeleteMapping
    public void delete(@RequestBody ApplicationVO applicationVO) {

    }

    @PutMapping
    public void put(@RequestBody ApplicationVO applicationVO) {

    }

    @GetMapping
    public List<ApplicationVO> get() {
        return Collections.emptyList();
    }
}
