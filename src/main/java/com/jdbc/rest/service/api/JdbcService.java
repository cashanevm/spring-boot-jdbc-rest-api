package com.jdbc.rest.service.api;

import com.jdbc.rest.persistence.entity.api.Entity;

import java.util.List;
import java.util.Optional;

public interface JdbcService {
    void initDb();

    <T extends Entity> T create(T objectToSave);

    <T extends Entity> T update(T objectToSave);

    <T extends Entity> Optional<Object> findById(String id, Class<?> clazz);

    void deleteById(String id, Class<?> clazz);

    <T extends Entity> List<Object> findAll(Class<?> clazz);
}
