package com.desafio.coopeuch.controller;

import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.TaskResponse;
import com.desafio.coopeuch.service.TaskService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ApiOperation(value="Para agregar una tarea")
    public String addNewTask(@RequestBody TaskRequest request) {

        if(taskRequestCheck(request, true)){
            return taskService.add(request);
        } else {
            return "Something is missing in the request";
        }
    }

    @PutMapping
    @ApiOperation(value="Para editar una tarea")
    public String editTask(@RequestBody TaskRequest request) {
        if(taskRequestCheck(request, false)){
            return taskService.edit(request);
        } else {
            return "Something is missing in the request";
        }
    }

    @GetMapping
    @ApiOperation(value="Para listar todas las tareas")
    public List<TaskResponse> listAll() {
        return taskService.list();
    }

    @DeleteMapping
    @ApiOperation(value="Para eliminar una tarea")
    public String deleteTask(Long id) {
        return taskService.remove(id);
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
