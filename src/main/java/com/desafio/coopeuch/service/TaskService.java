package com.desafio.coopeuch.service;

import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.TaskResponse;

import java.util.List;

public interface TaskService {
    String add(TaskRequest request);
    String edit(TaskRequest request);
    List<TaskResponse> list();
    String remove(Long id);
}
