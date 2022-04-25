package com.jdbc.rest.web.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class ApplicationExceptionResponseDto {
    @EqualsAndHashCode.Exclude
    @Schema(description = "Time, when error occurred", example = "2020-04-21 12:00:04")
    @JsonProperty("timestamp")
    private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @Schema(description = "Status code", example = "422")
    @JsonProperty("status")
    private int status;

    @Schema(description = "Message", example = "Internal Server Error")
    @JsonProperty("message")
    private String message;

    @Schema(description = "Errors")
    @JsonProperty("errors")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;
}
