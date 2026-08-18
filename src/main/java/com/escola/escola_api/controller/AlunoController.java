package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.aluno.*;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.service.AlunoService;
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
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController implements GenericController {

    private final AlunoService alunoService;

    // Cadastro feito apenas por ADMIN, sem conta de usuário vinculado.
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<URI> salvar(@RequestBody @Valid AlunoCadastroDTO dto) {
        Aluno entity = alunoService.salvar(dto, null);
        return ResponseEntity.created(gerarHeaderLocationMatricula(entity.getMatricula())).build();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<AlunoView> listar(
            @RequestParam(required = false) Integer matricula,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) UUID usuarioAtualizacao,
            Pageable pageable
    ) {
        return (alunoService.listar(matricula, nome, cpf, usuarioAtualizacao, pageable));
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isDonoDoAluno(#matricula)")
    @GetMapping("/admin/{matricula}")
    @ResponseStatus(HttpStatus.OK)
    public AlunoPesquisaDTO buscarPorMatriculaDetalhes(@PathVariable Integer matricula) {
        return alunoService.buscarPorMatriculaAdmin(matricula);
    }

    @GetMapping("/{matricula}")
    public AlunoResumoDTO buscarPorMatriculaPublico(@PathVariable Integer matricula) {
        return alunoService.buscarPorMatriculaPublico(matricula);
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isDonoDoAluno(#matricula)")
    @PutMapping("/{matricula}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer matricula, @RequestBody @Valid AlunoAtualizacaoDTO dto) {
        alunoService.atualizar(matricula, dto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{matricula}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Integer matricula) {
        alunoService.excluir(matricula);
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isDonoDoAluno(#matricula)")
    @DeleteMapping("/{matricula}/curso")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularCurso(@PathVariable Integer matricula) {
        alunoService.desvincular(matricula);
    }

    @PreAuthorize("hasRole('ADMIN') or @securityService.isDonoDoAluno(#matricula)")
    @PutMapping("/{matricula}/curso/{idCurso}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularCurso(@PathVariable Integer matricula, @PathVariable UUID idCurso) {
        alunoService.vincular(matricula, idCurso);
    }
}