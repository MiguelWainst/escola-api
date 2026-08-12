package com.escola.escola_api.repository;

import com.escola.escola_api.model.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Integer>, JpaSpecificationExecutor<Professor> {

    Optional<Professor> findByMatricula(Integer matricula);
    Optional<Professor> findByEmail(String email);
    Optional<Professor> findByCpf(String cpf);
}
