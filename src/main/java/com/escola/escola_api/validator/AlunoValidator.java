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

    private boolean existeEmail(Aluno aluno) {
        Optional<Aluno> optionalAluno = alunoRepository.findByEmail(aluno.getEmail());
        if (aluno.getMatricula() == null) {
            return optionalAluno.isPresent();
        }
        return optionalAluno.filter(a -> !a.getMatricula().equals(aluno.getMatricula())).isPresent();
    }

    private boolean existeCpf(Aluno aluno) {
        Optional<Aluno> optionalAluno = alunoRepository.findByCpf(aluno.getCpf());
        if (aluno.getMatricula() == null) {
            return optionalAluno.isPresent();
        }
        return optionalAluno.filter(a -> !a.getMatricula().equals(aluno.getMatricula())).isPresent();
    }
}
