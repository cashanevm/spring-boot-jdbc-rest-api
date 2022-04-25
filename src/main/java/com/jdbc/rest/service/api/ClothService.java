package com.jdbc.rest.service.api;

import com.jdbc.rest.persistence.entity.Cloth;

import java.util.List;
import java.util.Optional;

public interface ClothService {
    Cloth create(Cloth color);

    Cloth save(Cloth color);

    void deleteById(String id);

    Optional<Object> getById(String id);

    List<Object> getAll();
}
