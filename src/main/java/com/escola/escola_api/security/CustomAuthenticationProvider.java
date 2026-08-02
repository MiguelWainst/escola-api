package com.escola.escola_api.security;

import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    final private UsuarioService usuarioService;
    final private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senhaDigitada = authentication.getCredentials().toString();

        Optional<Usuario> usuarioOptional = usuarioService.obterPorUsername(login);
        if (usuarioOptional.isEmpty()) {
            throw new BadCredentialsException("Usuário ou senha incorretos.");
        }
        Usuario usuario = usuarioOptional.get();
        boolean senhasBatem = passwordEncoder.matches(senhaDigitada, usuario.getSenha());
        if (senhasBatem) {
            return new CustomAuthentication(usuario);
        }
        throw new BadCredentialsException("Usuário ou senha incorretos.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
