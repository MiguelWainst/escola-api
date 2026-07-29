package com.escola.escola_api.controller.dto;

import java.util.List;

public record UsuarioDTO(
    Long id,
    String nome,
    String email,
    List<String> roles
) {
}
