package com.jdbc.rest.persistence.entity;

import com.jdbc.rest.persistence.entity.api.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Brand implements Entity {
    public String name;
    private String id;
}
