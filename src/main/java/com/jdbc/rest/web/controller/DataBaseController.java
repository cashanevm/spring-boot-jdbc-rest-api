package com.jdbc.rest.web.controller;

import com.jdbc.rest.service.api.JdbcService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static com.jdbc.rest.constants.ApplicationConstants.APPLICATION_PREFIX;

@RestController
@RequestMapping(APPLICATION_PREFIX + "/data-base")
@RequiredArgsConstructor
public class DataBaseController {
    private final JdbcService jdbcService;

    /**
     * Init data base.
     */
    @PostMapping("/init")
    public void initDataBase() {
        jdbcService.initDb();
    }
}
