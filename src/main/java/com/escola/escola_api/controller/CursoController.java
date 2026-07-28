package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.CursoDTO;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.mapper.CursoMapper;
import com.escola.escola_api.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController implements GenericController{

    private final CursoService cursoService;
    private final CursoMapper cursoMapper;

    @GetMapping
    public ResponseEntity<List<CursoDTO>> listar() {
        List<Curso> cursos = cursoService.buscarTodos();
        List<CursoDTO> dtos = cursos
                .stream()
                .map(cursoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CursoDTO dto) {
        Curso entity = cursoMapper.toEntity(dto);
        cursoService.salvar(entity);

        URI location = gerarHeaderLocation(entity.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> buscarPorId(@PathVariable String id) {
        return cursoService.buscarPorId(UUID.fromString(id))
                .map(curso -> ResponseEntity.ok(cursoMapper.toDTO(curso)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
