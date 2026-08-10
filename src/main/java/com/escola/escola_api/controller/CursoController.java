package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.curso.CursoCadastroDTO;
import com.escola.escola_api.controller.dto.curso.CursoPesquisaDTO;
import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController implements GenericController{

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoResumoDTO>> listar() {
        return ResponseEntity.ok(cursoService.buscarTodos());
    }

    @PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CursoCadastroDTO dto) {
        Curso curso = cursoService.salvar(dto);
        return ResponseEntity.created(gerarHeaderLocation(curso.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoPesquisaDTO> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(cursoService.buscarPorId(id));
    }

    @PreAuthorize("hasAnyRole('PROFESSOR', 'ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable UUID id, @RequestBody @Valid CursoCadastroDTO dto) {
        cursoService.atualizar(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        cursoService.deletar(id);
    }
}
