package com.jdbc.rest.web.controller;

import com.jdbc.rest.persistence.entity.Color;
import com.jdbc.rest.service.api.ColorService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static com.jdbc.rest.constants.ApplicationConstants.APPLICATION_PREFIX;

@RestController
@RequestMapping(APPLICATION_PREFIX + "/color")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;

    @PostMapping
    public ResponseEntity<Color> createColor(@RequestBody Color color) {
        return ResponseEntity.ok(colorService.create(color));
    }

    @PutMapping
    public ResponseEntity<Color> saveColor(@RequestBody Color color) {
        return ResponseEntity.ok(colorService.save(color));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getColorById(@PathVariable(name = "id") String id) {
        return ResponseEntity.of(colorService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Object>> getAllColor() {
        return ResponseEntity.ok(colorService.getAll());
    }

    @DeleteMapping("/{id}")
    public void deleteColorById(@PathVariable(name = "id") String id) {
        colorService.deleteById(id);
    }
}
