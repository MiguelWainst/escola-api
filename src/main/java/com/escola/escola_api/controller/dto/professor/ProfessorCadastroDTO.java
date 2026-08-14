package com.escola.escola_api.controller.dto.professor;

import com.escola.escola_api.validator.DominioEmailValido;
import com.escola.escola_api.validator.ValidConst;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProfessorCadastroDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = ValidConst.NOME_MAX, min = ValidConst.NOME_MIN, message = "O nome deve ter entre {min} e {max} caracteres")
        String nome,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        @DominioEmailValido(message = "O domínio do email é inválido")
        String email,
        @Past(message = "A data de nascimento deve ser uma data do passado")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,
        @NotBlank(message = "O CPF é obrigatório")
        @Size(max = ValidConst.CPF_MAX, min = ValidConst.CPF_MIN, message = "CPF deve estar entre {min} e {max} caracteres")
        @CPF(message = "CPF inválido. Certifique-se de enviar os 11 dígitos corretos")
        String cpf,
        List<UUID> idCurso
) {
}
