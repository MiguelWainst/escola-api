package com.escola.escola_api.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProfessorCadastroDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 70, message = "O nome não pode ter mais de 70 caracteres")
        String nome,
        @NotBlank(message = "O email é obrigatório")
        @Size(max = 50, message = "O email não pode ter mais de 50 caracteres")
        @Email(message = "O email deve ser válido")
        String email,
        @Past(message = "A data de nascimento deve ser uma data do passado") // 👈 Impede datas futuras!
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,
        @CPF(message = "CPF inválido. Certifique-se de enviar os 11 dígitos corretos")
        String cpf,
        List<UUID> idCurso
) {
}
