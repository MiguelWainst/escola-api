package com.escola.escola_api.controller.common;

import com.escola.escola_api.controller.dto.ErroResposta;
import com.escola.escola_api.exception.DuplicateRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(org.springframework.http.HttpStatus.CONFLICT)
    public ErroResposta handleDuplicateRegisterException(DuplicateRegisterException e) {
        return new ErroResposta(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                List.of()
        );
    }
}
