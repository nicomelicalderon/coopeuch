package com.desafio.coopeuch.controller;

import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.TaskResponse;
import com.desafio.coopeuch.service.serviceImpl.TaskServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskServiceImpl taskService;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public @ResponseBody String addNewUser(@RequestBody TaskRequest request) {

        if(taskRequestCheck(request, true)){
            return taskService.add(request);
        } else {
            return "Something is missing in the request";
        }
    }

    @PutMapping
    public @ResponseBody String editUser(@RequestBody TaskRequest request) {
        if(taskRequestCheck(request, false)){
            return taskService.edit(request);
        } else {
            return "Something is missing in the request";
        }
    }

    @GetMapping
    public @ResponseBody List<TaskResponse> listAll() {
        return taskService.list();
    }

    @DeleteMapping
    public @ResponseBody String deleteTask(Long id) {
        if(id != null) {
            return taskService.remove(id);
        } else {
            return "id missing";
        }
    }

    private boolean taskRequestCheck(TaskRequest request, boolean add) {
        if(add) {
            return request.getDescription() != null && !request.getDescription().isEmpty()
                    && request.getCreationDate() != null;
        } else {
            return request.getId() != null &&
                    request.getDescription() != null && !request.getDescription().isEmpty()
                    && request.getCreationDate() != null;
        }
    }
}
