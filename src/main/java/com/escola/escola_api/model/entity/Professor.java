package com.escola.escola_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "professores")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    private LocalDate dataNascimento;

    private String cpf;

    @CreatedDate
    private LocalDate dataContratacao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    private UUID usuarioAtualizacao;

    @ManyToMany
    @JoinTable(
            name = "professores_cursos",
            joinColumns = @JoinColumn(name = "matricula_professor"),
            inverseJoinColumns = @JoinColumn(name = "id_curso")
    )
    private List<Curso> cursos;
}
