package com.escola.escola_api.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "alunos")
@Getter
@Setter
@ToString(exclude = "curso")
@EqualsAndHashCode(exclude = "curso")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aluno_seq")
    @SequenceGenerator(name = "aluno_seq", sequenceName = "aluno_seq", allocationSize = 1)
    private Integer matricula;

    private String nome;

    private String email;

    private LocalDate dataNascimento;

    private String cpf;

    @CreatedDate
    private LocalDate dataMatricula;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    private UUID usuarioAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso")
    private Curso curso;
}
