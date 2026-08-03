package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.ProfessorCadastroDTO;
import com.escola.escola_api.model.entity.Professor;
import com.escola.escola_api.repository.mapper.ProfessorMapper;
import com.escola.escola_api.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody ProfessorCadastroDTO dto) {
        Professor professor = professorMapper.toEntity(dto);
        professorService.salvar(professor, dto.idCurso());
        return ResponseEntity.ok().build();
    }
}
