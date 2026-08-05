package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.AlunoCadastroDTO;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.repository.mapper.AlunoMapper;
import com.escola.escola_api.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;
    private final AlunoMapper alunoMapper;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody AlunoCadastroDTO dto) {
        alunoService.salvar(alunoMapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

}