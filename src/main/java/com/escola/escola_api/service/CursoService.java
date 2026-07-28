package com.escola.escola_api.service;

import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public void salvar(Curso curso){
        cursoRepository.save(curso);
    }

    public List<Curso> buscarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(UUID id) {
        return cursoRepository.findById(id);
    }

    public void atualizar(Curso curso) {
        cursoRepository.save(curso);
    }
}
