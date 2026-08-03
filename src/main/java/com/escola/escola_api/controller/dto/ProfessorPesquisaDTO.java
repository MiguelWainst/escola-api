package com.escola.escola_api.controller.dto;

import com.escola.escola_api.model.entity.Usuario;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ProfessorPesquisaDTO(
        String nome,
        String email,
        LocalDate dataNascimento,
        String cpf,
        LocalDate dataContratacao,
        LocalDateTime dataAtualizacao,
        String usuarioAtualizacao

) {
}
