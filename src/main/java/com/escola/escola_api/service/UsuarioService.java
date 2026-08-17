package com.escola.escola_api.service;

import com.escola.escola_api.controller.dto.usuario.UsuarioCadastroDTO;
import com.escola.escola_api.controller.dto.usuario.UsuarioPesquisaDTO;
import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.repository.AlunoRepository;
import com.escola.escola_api.repository.ProfessorRepository;
import com.escola.escola_api.repository.UsuarioRepository;
import com.escola.escola_api.repository.mapper.UsuarioMapper;
import com.escola.escola_api.validator.UsuarioValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioValidator validator;
    private final UsuarioMapper mapper;
    private final AlunoService alunoService;
    private final ProfessorService professorService;


    @Transactional
    public Usuario cadastrar(UsuarioCadastroDTO dto) {
        Usuario entity = mapper.toEntity(dto);
        entity.setSenha(passwordEncoder.encode(dto.senha()));
        entity.setRoles(List.of(dto.tipoConta().name()));
        validator.validar(entity);
        usuarioRepository.save(entity);
        switch (dto.tipoConta()) {
            case ALUNO -> alunoService.salvar(dto.dadosAluno(), entity.getId());
            case PROFESSOR ->  professorService.salvar(dto.dadosProfessor(), entity.getId());
        }
        return entity;
    }

    public UsuarioPesquisaDTO obterPorId(UUID id) {
        return usuarioRepository.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public Optional<Usuario> obterPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    // Comming soon
//    @Transactional
//    public void vincularUsuarioProfessor(Integer matricula, UUID idUsuario) {
//        Professor professor = professorRepository.findByMatricula(matricula)
//                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));
//        if (professor.getIdUsuario() != null) throw new EntidadeJaVinculadaException("Este professor já está vinculado a um usuário");
//        professor.setIdUsuario(idUsuario);
//    }
//
//    @Transactional
//    public void vincularUsuarioAluno(Integer matricula, UUID idUsuario) {
//        Aluno aluno = alunoRepository.findByMatricula(matricula)
//                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado"));
//        if (aluno.getIdUsuario() != null) throw new EntidadeJaVinculadaException("Este aluno já está vinculado a um usuário");
//        aluno.setIdUsuario(idUsuario);
//    }
}
