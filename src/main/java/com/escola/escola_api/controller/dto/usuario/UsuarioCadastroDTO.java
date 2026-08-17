package com.escola.escola_api.controller.dto.usuario;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorCadastroDTO;
import com.escola.escola_api.validator.DominioEmailValido;
import com.escola.escola_api.validator.ValidConst;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record UsuarioCadastroDTO(
        @NotBlank(message = "O username é obrigatório")
        @Size(min = ValidConst.USUARIO_MIN, max = ValidConst.USUARIO_MAX, message = "O username deve ter entre {min} e {max} caracteres")
        String username,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = ValidConst.SENHA_MIN, max = ValidConst.SENHA_MAX, message = "A senha deve ter no mínimo {min} caracteres")
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
