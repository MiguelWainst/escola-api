package com.escola.escola_api.service;

import com.escola.escola_api.controller.dto.usuario.UsuarioCadastroDTO;
import com.escola.escola_api.controller.dto.usuario.UsuarioPesquisaDTO;
import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.repository.UsuarioRepository;
import com.escola.escola_api.repository.mapper.UsuarioMapper;
import com.escola.escola_api.validator.UsuarioValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioValidator validator;
    private final UsuarioMapper mapper;

    public Usuario salvar(UsuarioCadastroDTO dto) {
        Usuario usuario = mapper.toEntity(dto);
        validator.validar(usuario);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuario.setRoles(List.of("GUEST"));
        return usuarioRepository.save(usuario);
    }

    public UsuarioPesquisaDTO obterPorId(UUID id) {
        return usuarioRepository.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public Optional<Usuario> obterPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
