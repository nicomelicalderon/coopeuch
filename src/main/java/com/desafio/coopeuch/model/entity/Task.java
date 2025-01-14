package com.desafio.coopeuch.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tarea")
@Getter
@Setter
public class Task {
    @Id
    @Column(name = "identificador")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "fecha_creacion")
    private LocalDateTime creationDate;

    @Column(name = "vigente")
    private boolean active;

}
