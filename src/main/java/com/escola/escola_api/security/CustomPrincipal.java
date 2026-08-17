package com.escola.escola_api.security;

import java.util.UUID;

public record CustomPrincipal(UUID id, String username) {
}
