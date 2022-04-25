package com.jdbc.rest.service.impl;

import com.jdbc.rest.persistence.entity.Brand;
import com.jdbc.rest.service.api.BrandService;
import com.jdbc.rest.service.api.JdbcService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final JdbcService jdbcService;

    @Override
    public Brand create(Brand brand) {
        jdbcService.create(brand);
        return brand;
    }

    @Override
    public Brand save(Brand brand) {
        jdbcService.update(brand);
        return brand;
    }

    @Override
    public void deleteById(String id) {
        jdbcService.deleteById(id, Brand.class);
    }

    @Override
    public Optional<Object> getById(String id) {
        return jdbcService.findById(id, Brand.class);
    }

    @Override
    public List<Object> getAll() {
        return jdbcService.findAll(Brand.class);
    }
}
