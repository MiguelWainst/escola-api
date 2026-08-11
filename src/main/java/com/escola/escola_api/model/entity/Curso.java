package com.escola.escola_api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Table(name = "cursos")
@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"professores", "alunos"})
@ToString(exclude = {"professores", "alunos"})
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;

    private Integer cargaHoras;

    @CreatedDate
    private LocalDate dataCriacao;

    @LastModifiedDate
    private LocalDate dataAtualizacao;

    private UUID usuarioAtualizacao;

    @ManyToMany(mappedBy = "cursos", fetch = FetchType.LAZY)
    private List<Professor> professores;

    @OneToMany(mappedBy = "curso")
    private List<Aluno> alunos;
}
