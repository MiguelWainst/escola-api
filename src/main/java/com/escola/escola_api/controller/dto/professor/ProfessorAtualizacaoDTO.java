package com.escola.escola_api.controller.dto.professor;

import com.escola.escola_api.validator.DominioEmailValido;
import com.escola.escola_api.validator.ValidConst;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record ProfessorAtualizacaoDTO(
        @Size(max = ValidConst.NOME_MAX, min = ValidConst.NOME_MIN, message = "O nome deve ter entre {min} e {max} caracteres")
        String nome,
        @Email(message = "O email deve ser válido")
        @DominioEmailValido(message = "O domínio do email é inválido")
        String email,
        @Past(message = "A data de nascimento deve ser uma data do passado")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,
        @Size(max = ValidConst.CPF_MAX, min = ValidConst.CPF_MIN, message = "CPF deve estar entre {min} e {max} caracteres")
        @CPF(message = "CPF inválido. Certifique-se de enviar os 11 dígitos corretos")
        String cpf
) {
}
