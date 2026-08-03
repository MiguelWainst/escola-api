package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.ProfessorCadastroDTO;
import com.escola.escola_api.controller.dto.ProfessorPesquisaDTO;
import com.escola.escola_api.model.entity.Professor;
import com.escola.escola_api.repository.mapper.ProfessorMapper;
import com.escola.escola_api.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
public class ProfessorController implements GenericController{

    private final ProfessorService professorService;
    private final ProfessorMapper professorMapper;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid ProfessorCadastroDTO dto) {
        Professor professor = professorMapper.toEntity(dto);
        professorService.salvar(professor, dto.idCurso());
        URI location = gerarHeaderLocationMatricula(professor.getMatricula());
        return ResponseEntity.ok(location);
    }

    @GetMapping
    public ResponseEntity<List<ProfessorPesquisaDTO>> listar() {
        List<Professor> entityList = professorService.listar();
        List<ProfessorPesquisaDTO> dtos = entityList.stream().map(professorMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
