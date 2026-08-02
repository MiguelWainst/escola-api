package com.escola.escola_api.service;

import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.repository.UsuarioRepository;
import com.escola.escola_api.validator.UsuarioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioValidator validator;

    public void salvar(Usuario usuario) {
        validator.validar(usuario);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obterPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Optional<Usuario> obterPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
