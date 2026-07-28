package com.escola.escola_api.service;

import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public void salvar(Curso curso){
        cursoRepository.save(curso);
    }
}
