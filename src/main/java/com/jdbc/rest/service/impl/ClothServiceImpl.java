package com.jdbc.rest.service.impl;

import com.jdbc.rest.persistence.entity.Cloth;
import com.jdbc.rest.service.api.ClothService;
import com.jdbc.rest.service.api.JdbcService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClothServiceImpl implements ClothService {
    private final JdbcService jdbcService;

    @Override
    public Cloth create(Cloth cloth) {
        jdbcService.create(cloth);
        return cloth;
    }

    @Override
    public Cloth save(Cloth cloth) {
        jdbcService.update(cloth);
        return cloth;
    }

    @Override
    public void deleteById(String id) {
        jdbcService.deleteById(id, Cloth.class);
    }

    @Override
    public Optional<Object> getById(String id) {
        return jdbcService.findById(id, Cloth.class);
    }

    @Override
    public List<Object> getAll() {
        return jdbcService.findAll(Cloth.class);
    }
}
