package com.jdbc.rest.persistence.entity;

import com.jdbc.rest.persistence.entity.api.Entity;

import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cloth implements Entity {
    private String id;
    private String full_name;
    private OffsetDateTime created_at;
    private Color color;
    private Brand brand;
    private Size size;
    private boolean is_sold;
    private int price;
}
