package com.escola.escola_api.controller.dto.aluno;

import com.escola.escola_api.validator.DominioEmailValido;
import com.escola.escola_api.validator.ValidConst;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

public record AlunoCadastroDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = ValidConst.NOME_MAX, min = ValidConst.NOME_MIN, message = "O nome deve ter entre {min} e {max} caracteres")
        String nome,
        @Email
        @DominioEmailValido(message = "O domínio do email é inválido")
        @NotBlank(message = "O email é obrigatório")
        String email,
        @Past(message = "A data de nascimento deve ser uma data passada")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,
        @CPF(message = "O CPF é inválido")
        @NotBlank(message = "O CPF é obrigatório")
        @Size(max = ValidConst.CPF_MAX, min = ValidConst.CPF_MIN, message = "CPF deve estar entre {min} e {max} caracteres")
        String cpf,
        UUID idCurso
) {
}
