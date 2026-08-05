package com.escola.escola_api.controller.dto.curso;

import com.escola.escola_api.controller.dto.usuario.UsuarioPesquisaDTO;

public record CursoPesquisaDTO(
        String id,
        String nome,
        Integer cargaHoras,
        UsuarioPesquisaDTO usuarioAtualizacao
) {
}
