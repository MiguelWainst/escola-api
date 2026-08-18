package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.usuario.Roles;
import com.escola.escola_api.controller.dto.usuario.UsuarioCadastroDTO;
import com.escola.escola_api.controller.dto.usuario.UsuarioPesquisaDTO;
import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController implements GenericController{

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<URI> cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO dto) {
        Usuario usuario = usuarioService.cadastrar(dto);
        return ResponseEntity.created(gerarHeaderLocation(usuario.getId())).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioPesquisaDTO buscarUsuarioId(@PathVariable UUID id) {
        return usuarioService.obterPorId(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/promote/{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    public void promover(@PathVariable UUID idUsuario, @RequestBody Set<Roles> roles) {
        usuarioService.promover(idUsuario, roles);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/demote/{idUsuario}")
    @ResponseStatus(HttpStatus.OK)
    public void removerRole(@PathVariable UUID idUsuario, @RequestBody Set<Roles> roles) {
        usuarioService.removerRole(idUsuario, roles);
    }

    // Comming soon
//    @PostMapping("/professor/{matricula}/{idUsuario}")
//    public void vincularUsuarioProfessor(@PathVariable Integer matricula, @PathVariable UUID idUsuario) {
//        usuarioService.vincularUsuarioProfessor(matricula, idUsuario);
//    }
//
//    @PostMapping("/aluno/{matricula}/{idUsuario}")
//    public void vincularUsuarioAluno(@PathVariable Integer matricula, @PathVariable UUID idUsuario) {
//        usuarioService.vincularUsuarioAluno(matricula, idUsuario);
//    }
}
