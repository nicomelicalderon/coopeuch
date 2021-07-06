package com.desafio.coopeuch.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Long id;
    private String description;
    private LocalDateTime creationDate;
    private boolean active;
}
