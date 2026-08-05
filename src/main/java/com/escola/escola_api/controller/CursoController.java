package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.curso.CursoCadastroDTO;
import com.escola.escola_api.controller.dto.curso.CursoPesquisaDTO;
import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.mapper.CursoMapper;
import com.escola.escola_api.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<List<CursoResumoDTO>> listar() {
        List<Curso> cursos = cursoService.buscarTodos();
        List<CursoResumoDTO> dtos = cursos
                .stream()
                .map(cursoMapper::toResumoDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CursoCadastroDTO dto) {
        Curso entity = cursoMapper.toEntity(dto);
        cursoService.salvar(entity);
        URI location = gerarHeaderLocation(entity.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoPesquisaDTO> buscarPorId(@PathVariable String id) {
        return cursoService.buscarPorId(UUID.fromString(id))
                .map(curso -> ResponseEntity.ok(cursoMapper.toDTO(curso)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable String id, @RequestBody @Valid CursoCadastroDTO dto
    ) {
        return cursoService.buscarPorId(UUID.fromString(id))
                .map(cursoFound -> {
                    cursoMapper.updateEntityFromDTO(dto, cursoFound);
                    cursoService.atualizar(cursoFound);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        return cursoService.buscarPorId(UUID.fromString(id))
                .map(curso -> {
                    cursoService.delete(curso);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
