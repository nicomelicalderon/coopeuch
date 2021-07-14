package com.desafio.coopeuch.service;

import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.MessageResponse;
import com.desafio.coopeuch.model.response.TaskResponse;

import java.util.List;

public interface TaskService {
    MessageResponse add(TaskRequest request);
    MessageResponse edit(TaskRequest request);
    List<TaskResponse> list();
    TaskResponse getTask(Long id);
    MessageResponse remove(Long id);
}
