package com.escola.escola_api.controller.dto.aluno;

import com.escola.escola_api.validator.DominioEmailValido;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.UUID;

public record AlunoCadastroDTO(
        @NotBlank
        @Size(max = 70, min = 2, message = "O nome deve ter entre 2 e 70 caracteres")
        String nome,
        @Email
        @DominioEmailValido(message = "O domínio do email é inválido")
        @NotBlank(message = "O email é obrigatório")
        String email,
        @Past(message = "A data de nascimento deve ser uma data passada")
        LocalDate dataNascimento,
        @NotBlank(message = "O CPF é obrigatório")
        @CPF(message = "O CPF é inválido")
        String cpf,
        UUID idCurso
) {
}
