package com.escola.escola_api.controller.dto.usuario;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorCadastroDTO;
import com.escola.escola_api.validator.DominioEmailValido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record UsuarioCadastroDTO(
        @NotBlank(message = "O username é obrigatório")
        @Size(min = 3, max = 30, message = "O username deve ter entre 3 e 30 caracteres")
        String username,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, max = 100, message = "A senha deve ter no mínimo 8 caracteres")
        @Pattern(regexp = ".*[0-9].*", message = "A senha deve ter pelo menos um número")
        String senha,

        @NotBlank(message = "O email é obrigatório")
        @DominioEmailValido(message = "Domínio inválido")
        @Email(message = "O email deve ser válido")
        String email,

        @NotNull
        TipoConta tipoConta,

        @Valid
        AlunoCadastroDTO dadosAluno,

        @Valid
        ProfessorCadastroDTO dadosProfessor
) {
        @SuppressWarnings("unused")
        @AssertTrue(message = "Os dados enviados não correspondem ao tipo de conta escolhido.")
        public boolean isTipoContaConsistente() {
                if (tipoConta == null) return true;

                boolean temAluno = dadosAluno != null;
                boolean temProfessor = dadosProfessor != null;

                return switch (tipoConta) {
                    case ALUNO -> temAluno && !temProfessor;
                    case PROFESSOR -> temProfessor && !temAluno;
                };
        }
}
