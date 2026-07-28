package com.escola.escola_api.repository;

import com.escola.escola_api.model.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoRepository extends JpaRepository<Curso, UUID> {
}
