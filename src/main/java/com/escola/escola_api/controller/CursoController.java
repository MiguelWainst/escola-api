package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.CursoDTO;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.mapper.CursoMapper;
import com.escola.escola_api.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController implements GenericController{

    private final CursoService cursoService;
    private final CursoMapper cursoMapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody CursoDTO dto) {
        Curso entity = cursoMapper.toEntity(dto);
        cursoService.salvar(entity);

        URI location = gerarHeaderLocation(entity.getId());

        return ResponseEntity.created(location).build();
    }
}
