package com.escola.escola_api.controller.dto.professor;

import com.escola.escola_api.validator.DominioEmailValido;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
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
        @DominioEmailValido(message = "O domínio do email é inválido")
        String email,
        @Past(message = "A data de nascimento deve ser uma data do passado") // 👈 Impede datas futuras!
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataNascimento,
        @Size(max = 14, min = 11, message = "O CPF não pode ter mais de 14 caracteres")
        @CPF(message = "CPF inválido. Certifique-se de enviar os 11 dígitos corretos")
        String cpf,
        List<UUID> idCurso
) {
}
