package com.escola.escola_api.security;

import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.repository.AlunoRepository;
import com.escola.escola_api.repository.ProfessorRepository;
import com.escola.escola_api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioRepository usuarioRepository;
    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;

    public Usuario obterUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomPrincipal principal) {
            return usuarioRepository.findById(principal.id())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        }
        return null;
    }

    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return false;
        return authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    public boolean isDonoDoAluno(Integer matricula) {
        UUID idUsuarioLogado = obterIdUsuarioLogado();
        if (idUsuarioLogado == null) return false;
        return alunoRepository.findByMatricula(matricula)
                .map(aluno -> idUsuarioLogado.equals(aluno.getIdUsuario()))
                .orElse(false);
    }

    public boolean isDonoDoProfessor(Integer matricula) {
        UUID idUsuarioLogado = obterIdUsuarioLogado();
        if (idUsuarioLogado == null) return false;
        return professorRepository.findByMatricula(matricula)
                .map(professor -> idUsuarioLogado.equals(professor.getIdUsuario()))
                .orElse(false);
    }

    public UUID obterIdUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) return null;
        if (authentication.getPrincipal() instanceof CustomPrincipal principal) {
            return principal.id();
        }
        return null;
    }
}
