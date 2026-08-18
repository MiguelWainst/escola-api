package com.escola.escola_api.controller.common;

import com.escola.escola_api.controller.dto.ErroCampo;
import com.escola.escola_api.controller.dto.ErroResposta;
import com.escola.escola_api.exception.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
                ))
                .toList();

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        List<ErroCampo> erros = new ArrayList<>();
        String mensagemPadrao = "Formato de dados inválidos no corpo da requisição";

        if (e.getCause() instanceof InvalidFormatException invalidFormatException) {
            String campo = invalidFormatException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));

            Object valorInvalido = invalidFormatException.getValue();
            String tipoEsperado = invalidFormatException.getTargetType().getSimpleName();

            String detalhe = String.format(
                    "O valor '%s' é inválido para o campo '%s'. Esperado o tipo: %s",
                    valorInvalido,
                    campo,
                    tipoEsperado
            );

            erros.add(new ErroCampo(campo, detalhe));

        } else if (e.getCause() instanceof JsonMappingException jsonMappingException) {
            String campo = jsonMappingException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));

            erros.add(new ErroCampo(
                    campo,
                    "Sintaxe ou formato inválido para este campo"
            ));
        }

        return new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                mensagemPadrao,
                erros
        );
    }

    @ExceptionHandler({
            AuthorizationDeniedException.class,
            AccessDeniedException.class,
            AcessoNegadoException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErroResposta handleAcessoNegado() {
        return new ErroResposta(
                HttpStatus.FORBIDDEN.value(),
                "Acesso negado: Não tem permissão necessária para a ação.",
                List.of()
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta handleEntityNotFoundException(EntityNotFoundException e) {
        return ErroResposta.notFound(e.getMessage());
    }

    @ExceptionHandler(CursoJaVinculadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleCursoJaVinculado(CursoJaVinculadoException e) {
        return new ErroResposta(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroResposta handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String mensagem = String.format(
                "O parâmetro '%s' deve ser do tipo '%s'.",
                e.getName(),
                e.getRequiredType() != null
                        ? e.getRequiredType().getSimpleName()
                        : "válido"
        );

        return new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                mensagem,
                List.of()
        );
    }

    @ExceptionHandler({
            AlunoComCursoException.class,
            CursoLotadoException.class,
            ProfessorLotadoException.class,
            EntidadeJaVinculadaException.class,
            UsuarioRolesException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleConflito(RuntimeException e) {
        return new ErroResposta(
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResposta handleBadCredentialsException(BadCredentialsException e) {
        return new ErroResposta(
                HttpStatus.UNAUTHORIZED.value(),
                e.getMessage(),
                List.of()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleRuntimeException(RuntimeException e) {
        log.error("Erro interno não tratado", e);

        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro: Ocorreu um erro interno no servidor.",
                List.of()
        );
    }
}