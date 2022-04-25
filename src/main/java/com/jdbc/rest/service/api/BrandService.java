package com.jdbc.rest.service.api;

import com.jdbc.rest.persistence.entity.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand create(Brand brand);

    Brand save(Brand brand);

    void deleteById(String id);

    Optional<Object> getById(String id);

    List<Object> getAll();
}
