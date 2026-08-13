package com.escola.escola_api.repository;

import com.escola.escola_api.model.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>, JpaSpecificationExecutor<Aluno> {

    Optional<Aluno> findByMatricula(Integer matricula);
    Optional<Aluno> findByEmail(String email);
    Optional<Aluno> findByCpf(String cpf);
}
