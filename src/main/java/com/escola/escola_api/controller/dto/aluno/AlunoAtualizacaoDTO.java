package com.escola.escola_api.controller.dto.aluno;

import com.escola.escola_api.validator.DominioEmailValido;
import com.escola.escola_api.validator.ValidConst;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record AlunoAtualizacaoDTO(
        @Size(max = ValidConst.NOME_MAX, min = ValidConst.NOME_MIN, message = "O nome deve ter entre {min} e {max} caracteres")
        String nome,
        @Email(message = "O email deve ser válido")
        @DominioEmailValido(message = "O domínio do email é inválido")
        String email,
        @Past(message = "A data de nascimento deve ser uma data passada")
        LocalDate dataNascimento,
        @CPF(message = "O CPF é inválido")
        @Size(max = ValidConst.CPF_MAX, min = ValidConst.CPF_MIN, message = "CPF deve estar entre {min} e {max} caracteres")
        String cpf
) {
}
