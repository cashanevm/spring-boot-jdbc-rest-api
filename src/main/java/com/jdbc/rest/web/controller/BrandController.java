package com.jdbc.rest.web.controller;

import com.jdbc.rest.persistence.entity.Brand;
import com.jdbc.rest.service.api.BrandService;

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
@RequestMapping(APPLICATION_PREFIX + "/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @PostMapping()
    public ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        brandService.create(brand);
        return ResponseEntity.of(Optional.of(brand));
    }

    @PutMapping
    public ResponseEntity<Brand> saveBrand(@RequestBody Brand brand) {
        return ResponseEntity.ok(brandService.save(brand));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBrandById(@PathVariable(name = "id") String id) {
        return ResponseEntity.of(brandService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Object>> getAllBrand() {
        return ResponseEntity.ok(brandService.getAll());
    }

    @DeleteMapping("/{id}")
    public void deleteBrandById(@PathVariable(name = "id") String id) {
        brandService.deleteById(id);
    }
}
