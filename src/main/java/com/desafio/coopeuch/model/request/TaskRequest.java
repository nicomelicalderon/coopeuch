package com.desafio.coopeuch.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {
    private Long id;
    private String description;
    private LocalDateTime creationDate;
    private boolean active;
}
