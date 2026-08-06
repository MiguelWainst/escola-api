package com.escola.escola_api.validator;

import com.escola.escola_api.exception.DuplicateRegisterException;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlunoValidator {

    private final AlunoRepository alunoRepository;

    public void validar(Aluno aluno) {
        if (existeEmail(aluno)) {
            throw new DuplicateRegisterException("Email já cadastrado");
        }
        if (existeCpf(aluno)) {
            throw new DuplicateRegisterException("CPF já cadastrado");
        }
    }

    private boolean existeDuplicate(Optional<Aluno> optionalAluno, Optional<Integer> matricula) {
        return matricula
                .map(m -> optionalAluno.filter(a -> !a.getMatricula().equals(m)).isPresent())
                .orElse(optionalAluno.isPresent());
    }

    private boolean existeEmail(Aluno aluno) {
        return existeDuplicate(alunoRepository.findByEmail(aluno.getEmail()), aluno.getMatricula());
    }

    private boolean existeCpf(Aluno aluno) {
        return existeDuplicate(alunoRepository.findByCpf(aluno.getCpf()), aluno.getMatricula());
    }
}
