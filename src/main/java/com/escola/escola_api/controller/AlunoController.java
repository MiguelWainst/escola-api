package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoResumoDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoView;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.service.AlunoService;
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
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController implements GenericController {

    private final AlunoService alunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public URI salvar(@RequestBody @Valid AlunoCadastroDTO dto) {
        Aluno entity = alunoService.salvar(dto);
        return gerarHeaderLocationMatricula(entity.getMatricula());
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/{matricula}")
    @ResponseStatus(HttpStatus.OK)
    public AlunoPesquisaDTO buscarPorMatriculaAdmin(@PathVariable Integer matricula) {
        return alunoService.buscarPorMatriculaAdmin(matricula);
    }

    @GetMapping("/publico/{matricula}")
    public AlunoResumoDTO buscarPorMatriculaPublico(@PathVariable Integer matricula) {
        return alunoService.buscarPorMatriculaPublico(matricula);
    }

    @PutMapping("/{matricula}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer matricula, @RequestBody @Valid AlunoCadastroDTO dto) {
        alunoService.atualizar(matricula, dto);
    }

    @DeleteMapping("/{matricula}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Integer matricula) {
        alunoService.excluir(matricula);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{matricula}/curso")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularCurso(@PathVariable Integer matricula) {
        alunoService.desvincular(matricula);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{matricula}/curso/{idCurso}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularCurso(@PathVariable Integer matricula, @PathVariable UUID idCurso) {
        alunoService.vincular(matricula, idCurso);
    }
}