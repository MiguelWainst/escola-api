package com.escola.escola_api.validator;

import com.escola.escola_api.exception.DuplicateRegisterException;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CursoValidator {

    private final CursoRepository cursoRepository;

    public void validar(Curso curso) {
        if (cursoIgual(curso)) {
            throw new DuplicateRegisterException("Erro: Curso já existe");
        }
    }

    private boolean cursoIgual(Curso curso) {
        Optional<Curso> cursoExistente = cursoRepository.findByNome(curso.getNome());
        if (curso.getId() == null) {
            return cursoExistente.isPresent();
        }
        return cursoExistente.isPresent() && !cursoExistente.get().getId().equals(curso.getId());
    }
}
