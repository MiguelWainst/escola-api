package com.escola.escola_api.controller.dto;

import java.util.List;
import java.util.UUID;

public record UsuarioDTO(
    UUID id,
    String nome,
    String email,
    List<String> roles
) {
}
