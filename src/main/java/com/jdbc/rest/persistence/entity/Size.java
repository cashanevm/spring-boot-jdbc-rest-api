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
public class Size implements Entity {
    private String id;
    private String name;
    private int size;
}
