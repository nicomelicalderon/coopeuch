package com.desafio.coopeuch.service.impl;

import com.desafio.coopeuch.model.entity.Task;
import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.MessageResponse;
import com.desafio.coopeuch.model.response.TaskResponse;
import com.desafio.coopeuch.repository.TaskRepository;
import com.desafio.coopeuch.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public MessageResponse add(TaskRequest request) {
        MessageResponse response = new MessageResponse();
        Task task = requestToTask(request, true);
        task.setCreationDate(LocalDateTime.now());
        try {
            response.setMessage("task added");
            taskRepository.save(task);
        } catch (Exception e) {
            log.error("", e);
            response.setMessage(ERROR);
        }
        return response;
    }

    @Override
    public MessageResponse edit(TaskRequest request) {
        MessageResponse response = new MessageResponse();
        Task task = requestToTask(request, false);

        try {
            if(taskRepository.existsById(task.getId())){
                response.setMessage("task edited");
                taskRepository.save(task);
            } else {
                response.setMessage("task not found");
            }
        } catch (Exception e) {
            log.error("", e);
            response.setMessage(ERROR);
        }
        return response;
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
    public TaskResponse getTask(Long id) {
        TaskResponse response = new TaskResponse();
        try {
            Task task = taskRepository.getOneTask(id);
            response.setId(task.getId());
            response.setDescription(task.getDescription());
            response.setCreationDate(task.getCreationDate());
            response.setActive(task.isActive());
        } catch (Exception e) {
            log.error("", e);
        }
        return response;
    }

    @Override
    public MessageResponse remove(Long id) {
        MessageResponse response = new MessageResponse();
        try {
            if (taskRepository.existsById(id)) {
                response.setMessage("task deleted");
                taskRepository.deleteById(id);
            } else {
                response.setMessage("task not found");
            }
        }catch (Exception e) {
            log.error("", e);
            response.setMessage(ERROR);
        }
        return response;
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
