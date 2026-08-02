package com.escola.escola_api.validator;

import com.escola.escola_api.exception.DuplicateRegisterException;
import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    public void validar(Usuario usuario) {
        if (emailIgual(usuario)) {
            throw new DuplicateRegisterException("Email já cadastrado");
        }
    }

    private boolean emailIgual(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuario.getId() == null) {
            return usuarioExistente.isPresent();
        }
        return usuarioExistente.filter(u -> !u.getId().equals(usuario.getId())).isPresent();
    }
}
