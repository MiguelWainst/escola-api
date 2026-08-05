package com.escola.escola_api.controller.dto.curso;

import java.util.UUID;

public record CursoPesquisaDTO(
        String id,
        String nome,
        Integer cargaHoras,
        UUID usuarioAtualizacao
) {
}
