package com.desafio.coopeuch.service.impl;

import com.desafio.coopeuch.model.entity.Task;
import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.TaskResponse;
import com.desafio.coopeuch.repository.TaskRepository;
import com.desafio.coopeuch.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private static final String ERROR="error";

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public String add(TaskRequest request) {
        Task task = requestToTask(request, true);

        try {
            taskRepository.save(task);
        } catch (Exception e) {
            log.error("", e);
            return ERROR;
        }
        return "task added";
    }

    @Override
    public String edit(TaskRequest request) {
        Task task = requestToTask(request, false);

        try {
            if(taskRepository.existsById(task.getId())){
                taskRepository.save(task);
            }
        } catch (Exception e) {
            log.error("", e);
            return ERROR;
        }
        return "task edited";
    }

    @Override
    public List<TaskResponse> list() {
        List<TaskResponse> response = new ArrayList<>();
        try {
            List<Task> tasks = taskRepository.listAll();
            for (Task t: tasks
                 ) {
                TaskResponse tr = new TaskResponse();
                tr.setId(t.getId());
                tr.setDescription(t.getDescription());
                tr.setCreationDate(t.getCreationDate());
                tr.setActive(t.isActive());
                response.add(tr);
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return response;
    }

    @Override
    public String remove(Long id) {
        try {
            if (taskRepository.existsById(id)) {
                taskRepository.deleteById(id);
            }
        }catch (Exception e) {
            log.error("", e);
            return ERROR;
        }
        return "task deleted";
    }

    private Task requestToTask(TaskRequest r, boolean add) {
        Task task = new Task();
        if(!add) {
            task.setId(r.getId());
        }
        task.setDescription(r.getDescription());
        task.setCreationDate(r.getCreationDate());
        task.setActive(r.isActive());

        return task;
    }
}
