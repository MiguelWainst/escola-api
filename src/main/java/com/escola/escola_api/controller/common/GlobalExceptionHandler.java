package com.escola.escola_api.controller.common;

import com.escola.escola_api.controller.dto.ErroCampo;
import com.escola.escola_api.controller.dto.ErroResposta;
import com.escola.escola_api.exception.DuplicateRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErroCampo> erros = e.getFieldErrors()
                .stream()
                .map(fieldError -> new ErroCampo(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
                )).toList();
        return new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Dados inválidos",
                erros
        );
    }

    @ExceptionHandler(DuplicateRegisterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleDuplicateRegisterException(DuplicateRegisterException e) {
        return new ErroResposta(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                List.of()
        );
    }
}
