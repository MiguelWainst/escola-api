package com.escola.escola_api.controller.dto.usuario;

import java.util.List;

public record UsuarioPesquisaDTO(
    String id,
    String username,
    String email,
    List<String> roles
) {
}
