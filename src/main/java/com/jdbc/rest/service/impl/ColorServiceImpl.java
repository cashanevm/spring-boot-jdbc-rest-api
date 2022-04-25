package com.jdbc.rest.service.impl;

import com.jdbc.rest.persistence.entity.Color;
import com.jdbc.rest.service.api.ColorService;
import com.jdbc.rest.service.api.JdbcService;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final JdbcService jdbcService;

    @Override
    public Color create(Color color) {
        jdbcService.create(color);
        return color;
    }

    @Override
    public Color save(Color color) {
        jdbcService.update(color);
        return color;
    }

    @Override
    public void deleteById(String id) {
        jdbcService.deleteById(id, Color.class);
    }

    @Override
    public Optional<Object> getById(String id) {
        return jdbcService.findById(id, Color.class);
    }

    @Override
    public List<Object> getAll() {
        return jdbcService.findAll(Color.class);
    }
}
