package com.example.server.data;

import java.util.Collection;

import org.apache.ibatis.annotations.Param;

public interface PropertiesDao {

    void insert(Collection<PropertiesDO> propertiesDOS);

    void delete(@Param("application") String application, @Param("profile") String profile, @Param("label") String label);

}
