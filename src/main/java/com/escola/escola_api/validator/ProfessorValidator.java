package com.escola.escola_api.validator;

import com.escola.escola_api.exception.DuplicateRegisterException;
import com.escola.escola_api.model.entity.Professor;
import com.escola.escola_api.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProfessorValidator {

    private final ProfessorRepository professorRepository;

    public void validar(Professor professor) {
        if (existeCpf(professor)) {
            throw new DuplicateRegisterException("Erro: Cpf já cadastrado");
        }
        if (existeEmail(professor)) {
            throw new DuplicateRegisterException("Erro: Email já cadastrado");
        }
    }

    private boolean existeCpf(Professor professor) {
        Optional<Professor> professorOptional = professorRepository.findByCpf(professor.getCpf());
        if (professor.getMatricula() == null) {
            return professorOptional.isPresent();
        }
        return professorOptional.isPresent() && !professor.getMatricula().equals(professorOptional.get().getMatricula());
    }

    private boolean existeEmail(Professor professor) {
        Optional<Professor> professorOptional = professorRepository.findByEmail(professor.getEmail());
        if (professor.getMatricula() == null) {
            return professorOptional.isPresent();
        }
        return professorOptional.isPresent() && !professor.getMatricula().equals(professorOptional.get().getMatricula());
    }
}
