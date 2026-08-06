package com.escola.escola_api.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<ErroCampo> erros) {
    public static ErroResposta notFound(String mensagem) {
        return new ErroResposta(
                HttpStatus.NOT_FOUND.value(),
                mensagem,
                List.of()
        );
    };
}
