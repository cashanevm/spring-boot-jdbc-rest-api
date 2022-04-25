package com.jdbc.rest.web.controller.advice;

import com.jdbc.rest.web.dto.response.ApplicationExceptionResponseDto;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    /**
     * Method for handing MethodArgumentNotValidExceptions.
     *
     * @param exception - MethodArgumentNotValidException
     * @return ResponseEntity<ApplicationExceptionResponseDto>
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApplicationExceptionResponseDto> handleException(DuplicateKeyException exception) {
        return new ResponseEntity<>(ApplicationExceptionResponseDto.builder()
                .message(toMessage(exception))
                .status(422)
                .build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApplicationExceptionResponseDto> handleException(DataIntegrityViolationException exception) {
        return new ResponseEntity<>(ApplicationExceptionResponseDto.builder()
                .message(exception.getMessage())
                .status(422)
                .build(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private String toMessage(DuplicateKeyException exception) {
        return String.format("Such key '%s' already exists.", exception.getMessage()
                .split("ERROR:")[1].split("\\\"")[1]);
    }
}
