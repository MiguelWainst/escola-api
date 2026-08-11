package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController implements GenericController {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AlunoCadastroDTO dto) {
        Aluno entity = alunoService.salvar(dto);
        URI location = gerarHeaderLocationMatricula(entity.getMatricula());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<AlunoPesquisaDTO>> listar() {
        return ResponseEntity.ok(alunoService.listar());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<AlunoPesquisaDTO> buscarPorMatricula(@PathVariable Integer matricula) {
        return ResponseEntity.ok(alunoService.buscarPorMatricula(matricula));
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer matricula, @RequestBody @Valid AlunoCadastroDTO dto) {
        alunoService.atualizar(matricula, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> excluir(@PathVariable Integer matricula) {
        alunoService.excluir(matricula);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{matricula}/curso")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desvincularAluno(@PathVariable Integer matricula) {
        alunoService.desvincular(matricula);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{matricula}/curso")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vincularAluno(@PathVariable Integer matricula, @RequestParam UUID idCurso) {
        alunoService.vincular(matricula, idCurso);
    }
}