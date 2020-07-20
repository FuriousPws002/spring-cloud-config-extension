package com.example.server.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.data.PropertiesDO;
import com.example.server.data.PropertiesDao;
import com.example.server.data.PropertiesDescDO;
import com.example.server.data.PropertiesDescDao;
import com.example.server.model.PropertiesDescVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PropertiesService {

    private final PropertiesPropertySourceLoader propertiesPropertySourceLoader;
    private final YamlPropertySourceLoader yamlPropertySourceLoader;
    private final PropertiesDescDao propertiesDescDao;
    private final PropertiesDao propertiesDao;

    @Transactional
    @SuppressWarnings("unchecked")
    public void post(PropertiesDescVO propertiesDescVO) {
        PropertiesDescVO.Type type = propertiesDescVO.getType();
        String value = propertiesDescVO.getValue();

        List<PropertySource<?>> sources = Collections.emptyList();
        ByteArrayResource resource = new ByteArrayResource(value.getBytes(StandardCharsets.UTF_8));
        try {
            if (type == PropertiesDescVO.Type.PROPERTIES) {
                sources = propertiesPropertySourceLoader.load(type.getType(), resource);
            } else if (type == PropertiesDescVO.Type.YAML) {
                sources = yamlPropertySourceLoader.load(type.getType(), resource);
            } else {
                throw new IllegalArgumentException("");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            //data format incorrect
//            throw new ...
        }

        Map<String, Object> map = new LinkedHashMap<>();
        for (PropertySource<?> source : sources) {
            Object s = source.getSource();
            if (s instanceof Map) {
                map.putAll((Map<String, Object>) s);
            }
        }

        PropertiesDescDO propertiesDescDO = new PropertiesDescDO();
        BeanUtils.copyProperties(propertiesDescVO, propertiesDescDO);
        propertiesDescDO.setType(propertiesDescVO.getType().getType());

        List<PropertiesDO> propertiesList = new ArrayList<>(map.size());
        map.forEach((k, v) -> {
            PropertiesDO propertiesDO = new PropertiesDO();
            propertiesDO.setApplication(propertiesDescDO.getApplication());
            propertiesDO.setProfile(propertiesDescDO.getProfile());
            propertiesDO.setLabel(propertiesDescDO.getLabel());
            propertiesDO.setKey(k);
            propertiesDO.setValue(Objects.toString(v));
            propertiesList.add(propertiesDO);
        });

        //判断是修改还是添加
        Long id = propertiesDescDO.getId();
        if (Objects.isNull(id)) {
            //添加
            propertiesDescDao.insert(propertiesDescDO);
        } else {
            propertiesDescDao.update(propertiesDescDO);
            propertiesDao.delete(propertiesDescDO.getApplication(), propertiesDescDO.getProfile(), propertiesDescDO.getLabel());
        }

        propertiesDao.insert(propertiesList);
    }
}
