package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.curso.*;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController implements GenericController{

    private final CursoService cursoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<URI> salvar(@RequestBody @Valid CursoCadastroDTO dto) {
        Curso curso = cursoService.salvar(dto);
        return ResponseEntity.created(gerarHeaderLocation(curso.getId())).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CursoView> listar(
            @RequestParam(required = false) UUID id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer cargaHora,
            @RequestParam(required = false) UUID usuarioAtualizacao,
            Pageable pageable
    ) {
        return cursoService.listar(id, nome, cargaHora, usuarioAtualizacao, pageable);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CursoResumoDTO buscarPorIdPublico(@PathVariable UUID id) {
        return cursoService.buscarPorId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CursoPesquisaDTO buscarPorIdAdmin(@PathVariable UUID id) {
        return cursoService.buscarPorIdAdmin(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable UUID id, @RequestBody @Valid CursoAtualizacaoDTO dto) {
        cursoService.atualizar(id, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        cursoService.deletar(id);
    }
}
