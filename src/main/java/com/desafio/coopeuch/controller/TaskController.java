package com.desafio.coopeuch.controller;

import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.MessageResponse;
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

    private static final String MISSING = "Something is missing in the request";

    @PostMapping
    @ApiOperation(value="Para agregar una tarea")
    public MessageResponse addNewTask(@RequestBody TaskRequest request) {
        MessageResponse response = new MessageResponse();

        if(taskRequestCheck(request, true)){
            response = taskService.add(request);
        } else {
            response.setMessage(MISSING);
        }
        return response;
    }

    @PutMapping
    @ApiOperation(value="Para editar una tarea")
    public MessageResponse editTask(@RequestBody TaskRequest request) {
        MessageResponse response = new MessageResponse();
        if(taskRequestCheck(request, false)){
            response = taskService.edit(request);
        } else {
            response.setMessage(MISSING);
        }
        return response;
    }

    @GetMapping
    @ApiOperation(value="Para listar todas las tareas")
    public List<TaskResponse> listAll() {
        return taskService.list();
    }

    @GetMapping("/id")
    @ApiOperation(value="Para obtener una tarea")
    public TaskResponse getTask(Long id) {
        return taskService.getTask(id);
    }

    @DeleteMapping
    @ApiOperation(value="Para eliminar una tarea")
    public MessageResponse deleteTask(Long id) {
        return taskService.remove(id);
    }

    private boolean taskRequestCheck(TaskRequest request, boolean add) {
        if(add) {
            return request.getDescription() != null && !request.getDescription().isEmpty();
        } else {
            return request.getId() != null &&
                    request.getDescription() != null && !request.getDescription().isEmpty()
                    && request.getCreationDate() != null;
        }
    }
}
