package com.escola.escola_api.controller.dto;

import java.util.List;

public record ErroResposta(int status, String mensagem, List<ErroCampo> erros) {
}
