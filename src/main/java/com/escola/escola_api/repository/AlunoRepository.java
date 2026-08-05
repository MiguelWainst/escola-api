package com.escola.escola_api.repository;

import com.escola.escola_api.model.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    Optional<Aluno> findByMatricula(Integer matricula);
}
