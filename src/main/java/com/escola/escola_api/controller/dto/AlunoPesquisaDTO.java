package com.escola.escola_api.controller.dto;

public record AlunoPesquisaDTO(
        String matricula,
        String nome,
        String email,
        String cpf
) {
}
