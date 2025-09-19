package com.udb.ciberseguridad.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @ManyToOne
    @JoinColumn(name = "docente_id")
    private User docente;

    @CreationTimestamp
    private LocalDateTime createdAt;
}