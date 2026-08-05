package com.escola.escola_api.controller.dto.usuario;

import java.util.List;
import java.util.UUID;

public record UsuarioCadastroDTO(
    UUID id,
    String username,
    String senha,
    String email,
    List<String> roles
) {
}
