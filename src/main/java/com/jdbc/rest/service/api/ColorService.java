package com.jdbc.rest.service.api;

import com.jdbc.rest.persistence.entity.Color;

import java.util.List;
import java.util.Optional;

public interface ColorService {
    Color create(Color color);

    Color save(Color color);

    void deleteById(String id);

    Optional<Object> getById(String id);

    List<Object> getAll();
}
