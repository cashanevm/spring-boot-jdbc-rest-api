package com.jdbc.rest.service.impl;

import com.jdbc.rest.persistence.entity.api.Entity;
import com.jdbc.rest.service.api.JdbcService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.jdbc.rest.constants.ApplicationConstants.SQL_FILE_PATH;

@Service
@Slf4j
@RequiredArgsConstructor
public class JdbcServiceImpl implements JdbcService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void initDb() {
        String initSqlFileName = "database";

        jdbcTemplate.execute(this.getSql(initSqlFileName));
        log.info("Database initialization finished!");
    }

    @Override
    public <T extends Entity> T create(T objectToSave) {
        objectToSave.setId(UUID.randomUUID().toString());
        jdbcTemplate.execute(getInsertSql(objectToSave));
        return objectToSave;
    }

    @Override
    public <T extends Entity> T update(T objectToSave) {
        jdbcTemplate.execute(getUpdateSql(objectToSave));
        return objectToSave;
    }

    @Override
    public <T extends Entity> Optional<Object> findById(String id, Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        return jdbcTemplate.query(String.format("SELECT * FROM %s WHERE id = ?", tableName), new Object[]{id},
                (rs, rowNum) -> this.get(rs, clazz)
        ).stream().findFirst();
    }

    @Override
    public void deleteById(String id, Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        jdbcTemplate.execute(String.format("DELETE FROM %s WHERE id = '%s'", tableName, id));
    }

    @Override
    public <T extends Entity> List<Object> findAll(Class<?> clazz) {
        String tableName = clazz.getSimpleName().toLowerCase();
        return jdbcTemplate.query(String.format("SELECT * FROM %s", tableName),
                (rs, rowNum) -> this.get(rs, clazz)
        );
    }

    private Object get(ResultSet rs, Class<?> clazz) {
        try {
            Object object = clazz.newInstance();
            Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
                String fieldName = field.getName();
                try {
                    if (isEntity(field.getType().newInstance())) {
                        if (rs.getObject(fieldName) != null) {
                            this.setFieldValue(field, object, this.findById(rs.getObject(fieldName).toString(), field.getType()));
                        }
                    } else {
                        this.setFieldValue(field, object, rs.getObject(fieldName));
                    }
                } catch (SQLException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            return object;
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    private String getSql(String fileName) {
        try {
            return Files.readString(Path.of(String.format("%s/%s.sql", SQL_FILE_PATH, fileName)));
        } catch (IOException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sql file not found!");
        }
    }

    private <T extends Entity> String getUpdateSql(T objectToInsert) {
        String insertSql = "UPDATE %s SET %s = %s WHERE id = '%s'";
        String tableName = objectToInsert.getClass().getSimpleName().toLowerCase();
        String tableFields = getFields(objectToInsert);
        String fieldsValue = getSavedFieldsValues(objectToInsert);

        return String.format(insertSql, tableName, tableFields, fieldsValue, objectToInsert.getId());
    }

    private <T extends Entity> String getInsertSql(T objectToInsert) {
        String insertSql = "INSERT INTO %s %s VALUES %s";
        String tableName = objectToInsert.getClass().getSimpleName().toLowerCase();
        String tableFields = getFields(objectToInsert);
        String fieldsValue = getCreatedFieldsValues(objectToInsert);

        System.out.println(String.format(insertSql, tableName, tableFields, fieldsValue));

        return String.format(insertSql, tableName, tableFields, fieldsValue);
    }

    private <T extends Entity> String getFields(T objectToInsert) {
        return this.getSqlArrayAsString(Arrays.stream(objectToInsert.getClass().getDeclaredFields())
                .map(Field::getName)
                .map(x -> x = "\"" + x + "\"")
                .collect(Collectors.toList()));
    }

    private <T extends Entity> String getSavedFieldsValues(T objectToInsert) {
        return this.getSqlArrayAsString(Arrays.stream(objectToInsert.getClass().getDeclaredFields())
                .map(field -> getSavedValue(field, objectToInsert))
                .collect(Collectors.toList()));
    }

    private <T extends Entity> String getCreatedFieldsValues(T objectToInsert) {
        return this.getSqlArrayAsString(Arrays.stream(objectToInsert.getClass().getDeclaredFields())
                .map(field -> getCreatedValue(field, objectToInsert))
                .collect(Collectors.toList()));
    }

    private String getSqlArrayAsString(List<String> strings) {
        return strings.toString()
                .replace("[", "(")
                .replace("]", ")");
    }

    private String getSavedValue(Field field, Object object) {
        Object fieldValue = this.getFieldValue(field, object);

        return isEntity(fieldValue) ?
                this.saveEntityFieldAndGetId((Entity) fieldValue) :
                this.getSqlFieldValue(fieldValue);
    }

    private String getCreatedValue(Field field, Object object) {
        Object fieldValue = this.getFieldValue(field, object);

        return isEntity(fieldValue) ?
                this.createEntityFieldAndGetId((Entity) fieldValue) :
                this.getSqlFieldValue(fieldValue);
    }

    private String getSqlFieldValue(Object object) {
        return object instanceof String || object instanceof OffsetDateTime ? "'" + object + "'" : object == null ? "null" : object.toString();
    }

    private String saveEntityFieldAndGetId(Entity entity) {
        this.update(entity);
        return entity != null ? "'" + entity.getId() + "'" : null;
    }

    private String createEntityFieldAndGetId(Entity entity) {
        this.create(entity);
        return entity != null ? "'" + entity.getId() + "'" : null;
    }

    private boolean isEntity(Object object) {
        return object instanceof Entity;
    }

    private Object getFieldValue(Field field, Object object) {
        try {
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null;
        } finally {
            field.setAccessible(false);
        }
    }

    private void setFieldValue(Field field, Object object, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value instanceof Optional ? ((Optional<?>) value).get() : value);
        } catch (IllegalAccessException e) {

        } finally {
            field.setAccessible(false);
        }
    }
}
