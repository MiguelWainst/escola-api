package com.escola.escola_api.service;

import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public void salvar(Aluno aluno) {
        alunoRepository.save(aluno);
    }
}
