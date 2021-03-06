package com.jdbc.rest.web.controller;

import com.jdbc.rest.persistence.entity.Cloth;
import com.jdbc.rest.service.api.ClothService;

import java.util.List;
import java.util.Optional;

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
@RequestMapping(APPLICATION_PREFIX + "/cloth")
@RequiredArgsConstructor
public class ClothController {
    private final ClothService clothService;

    @PostMapping()
    public ResponseEntity<Cloth> createCloth(@RequestBody Cloth cloth) {
        clothService.create(cloth);
        return ResponseEntity.of(Optional.of(cloth));
    }

    @PutMapping
    public ResponseEntity<Cloth> saveCloth(@RequestBody Cloth cloth) {
        return ResponseEntity.ok(clothService.save(cloth));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getClothById(@PathVariable(name = "id") String id) {
        return ResponseEntity.of(clothService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Object>> getAllCloth() {
        return ResponseEntity.ok(clothService.getAll());
    }

    @DeleteMapping("/{id}")
    public void deleteColorById(@PathVariable(name = "id") String id) {
        clothService.deleteById(id);
    }
}
