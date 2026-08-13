package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.professor.ProfessorCadastroDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorPesquisaDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorView;
import com.escola.escola_api.model.entity.Professor;
import com.escola.escola_api.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
public class ProfessorController implements GenericController{

    private final ProfessorService professorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public URI salvar(@RequestBody @Valid ProfessorCadastroDTO dto) {
        Professor entity = professorService.salvar(dto);
        return gerarHeaderLocationMatricula(entity.getMatricula());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'PROFESSOR')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<ProfessorView> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Integer matricula,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) UUID usuarioAtualizacao,
            Pageable pageable
    ) {
        return professorService.listar(nome, matricula, cpf, usuarioAtualizacao, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'PROFESSOR')")
    @GetMapping("/{matricula}")
    @ResponseStatus(HttpStatus.OK)
    public ProfessorPesquisaDTO obterPorMatricula(@PathVariable Integer matricula) {
        return professorService.obterPorMatricula(matricula);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @PutMapping("/{matricula}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer matricula, @RequestBody @Valid ProfessorCadastroDTO dto) {
        professorService.atualizar(matricula, dto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{matricula}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer matricula) {
        professorService.deletar(matricula);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{matricula}/cursos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularCurso(@PathVariable Integer matricula, @PathVariable UUID id) {
        professorService.desvincularCurso(matricula, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{matricula}/cursos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularCurso(@PathVariable Integer matricula, @PathVariable UUID id) {
        professorService.vincularCurso(matricula, id);
    }
}
