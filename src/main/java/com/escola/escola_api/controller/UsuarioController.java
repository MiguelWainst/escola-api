package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.usuario.UsuarioCadastroDTO;
import com.escola.escola_api.controller.dto.usuario.UsuarioPesquisaDTO;
import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController implements GenericController{

    private final UsuarioService usuarioService;

    @PreAuthorize("permitAll()")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public URI cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO dto) {
        Usuario usuario = usuarioService.cadastrar(dto);
        return gerarHeaderLocation(usuario.getId());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioPesquisaDTO buscarUsuarioId(@PathVariable UUID id) {
        return usuarioService.obterPorId(id);
    }
}
