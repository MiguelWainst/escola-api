package com.escola.escola_api.repository.specification;

import com.escola.escola_api.model.entity.Aluno;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AlunoSpec {

    public static Specification<Aluno> comFiltros(Integer matricula, String nome, String cpf, UUID usuarioAtualizacao) {
        return  (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (matricula != null) predicates.add(cb.equal(root.get("matricula"), matricula));
            if (nome != null && !nome.isBlank()) predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            if (cpf != null && !cpf.isBlank()) predicates.add(cb.equal(root.get("cpf"), cpf));
            if (usuarioAtualizacao != null) predicates.add(cb.equal(root.get("usuarioAtualizacao"), usuarioAtualizacao));
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
