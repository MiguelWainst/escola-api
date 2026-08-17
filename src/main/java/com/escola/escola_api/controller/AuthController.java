package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.usuario.UsuarioLoginDTO;
import com.escola.escola_api.controller.dto.usuario.UsuarioTokenRespostaDTO;
import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioTokenRespostaDTO login(@RequestBody UsuarioLoginDTO loginDTO) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.senha()));
        Usuario principal = (Usuario) authenticate.getPrincipal();
        String token = tokenService.gerarToken(
                principal.getUsername(),
                principal.getRoles(),
                principal.getId()
        );
        return new UsuarioTokenRespostaDTO(token);
    }
}
