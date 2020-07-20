package com.example.server.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.model.PropertiesDescVO;
import com.example.server.model.PropertiesVO;
import com.example.server.service.PropertiesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("properties")
public class PropertiesController {

    private final PropertiesService propertiesService;

    /**
     * 添加配置
     * {
     *   "application":"app-1",
     *   "profile":"test",
     *   "label":"label4",
     *   "type":"PROPERTIES",
     *   "value":"server.port=8081\napp-2.ribbon.listOfServers=localhost:8080\n#app-3.url=localhost:8080\n#app-4.ribbon.listOfServers=localhost:8080\napp-5.ribbon.listOfServers=localhost:8080"
     * }
     * 修改配置
     * {
     *   "id":6,
     *   "application":"app-1",
     *   "profile":"test",
     *   "label":"label4",
     *   "type":"PROPERTIES",
     *   "value":"server.port=8082\napp-2.ribbon.listOfServers=localhost:8080\n#app-3.url=localhost:8080\n#app-4.ribbon.listOfServers=localhost:8080\napp-5.ribbon.listOfServers=localhost:8080"
     * }
     * @param propertiesDescVO
     */
    @PostMapping
    public void post(@RequestBody PropertiesDescVO propertiesDescVO) {
        propertiesService.post(propertiesDescVO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

    }

    @PutMapping("/{id}")
    public void put(@PathVariable Long id, @RequestBody PropertiesVO propertiesVO) {

    }

    @GetMapping
    public List<PropertiesVO> get() {
        return Collections.emptyList();
    }
}
