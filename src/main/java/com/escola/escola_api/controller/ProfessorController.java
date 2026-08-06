package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.professor.ProfessorCadastroDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorPesquisaDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorResumoDTO;
import com.escola.escola_api.model.entity.Professor;
import com.escola.escola_api.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
public class ProfessorController implements GenericController{

    private final ProfessorService professorService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody @Valid ProfessorCadastroDTO dto) {
        Professor entity = professorService.salvar(dto);
        URI location = gerarHeaderLocationMatricula(entity.getMatricula());
        return ResponseEntity.created(location).build();
    }

//    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'PROFESSOR')")
//    @GetMapping
//    public ResponseEntity<List<ProfessorResumoDTO>> listar() {
//        List<Professor> entityList = professorService.obterTodos();
//        List<ProfessorResumoDTO> dtos = entityList
//                .stream()
//                .map(professorMapper::toResumoDTO)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(dtos);
//    }
//
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'PROFESSOR')")
//    @GetMapping("/{matricula}")
//    public ResponseEntity<ProfessorPesquisaDTO> obterPorMatricula(@PathVariable Integer matricula) {
//        return professorService.obterPorMatricula(matricula).map(professor -> {
//            ProfessorPesquisaDTO dto = professorMapper.toDTO(professor);
//            return ResponseEntity.ok(dto);
//        }).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
//    @PutMapping("/{matricula}")
//    public ResponseEntity<?> atualizar(@PathVariable Integer matricula, @RequestBody @Valid ProfessorCadastroDTO dto) {
//        return professorService.obterPorMatricula(matricula).map(professor -> {
//            professorMapper.updateEntityFromDTO(dto, professor);
//            professorService.atualizar(professor);
//            return ResponseEntity.noContent().build();
//        }).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @PreAuthorize("hasAnyRole('ADMIN')")
//    @DeleteMapping("/{matricula}")
//    public ResponseEntity<?> deletar(@PathVariable Integer matricula) {
//        return professorService.obterPorMatricula(matricula).map(professor -> {
//            professorService.deletar(professor);
//            return ResponseEntity.noContent().build();
//        }).orElseGet(() -> ResponseEntity.notFound().build());
//    }

}
