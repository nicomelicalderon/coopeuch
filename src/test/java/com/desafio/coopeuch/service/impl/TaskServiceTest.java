package com.desafio.coopeuch.service.impl;

import com.desafio.coopeuch.model.entity.Task;
import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.MessageResponse;
import com.desafio.coopeuch.model.response.TaskResponse;
import com.desafio.coopeuch.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    public void addTestOk() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setDescription("test");
        taskRequest.setCreationDate(LocalDateTime.now());
        taskRequest.setActive(true);
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(new Task());
        MessageResponse response = taskService.add(taskRequest);
        Assertions.assertThat(response.getMessage()).isEqualTo("task added");
    }

    @Test
    public void editTestOk() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setId(2L);
        taskRequest.setDescription("test edicion");
        taskRequest.setCreationDate(LocalDateTime.now());
        taskRequest.setActive(true);
        Mockito.when(taskRepository.save(any(Task.class))).thenReturn(new Task());
        Mockito.when(taskRepository.existsById(anyLong())).thenReturn(true);
        MessageResponse response = taskService.edit(taskRequest);
        Assertions.assertThat(response.getMessage()).isEqualTo("task edited");
    }

    @Test
    public void listAllTest() {
        Task task = new Task();
        task.setId(2L);
        task.setDescription("test");
        task.setCreationDate(LocalDateTime.now());
        task.setActive(true);
        List<Task> listRepo = new ArrayList<>();
        listRepo.add(task);
        Mockito.when(taskRepository.listAll()).thenReturn(listRepo);
        List<TaskResponse> listRet = taskService.list();
        Assertions.assertThat(listRet).isNotEmpty();
    }

    @Test
    public void getTaskTest() {
        Task task = new Task();
        task.setId(2L);
        task.setDescription("test");
        task.setCreationDate(LocalDateTime.now());
        task.setActive(true);
        Mockito.when(taskRepository.getOneTask(2L)).thenReturn(task);
        TaskResponse taskResponse = taskService.getTask(2L);
        Assertions.assertThat(taskResponse).isNotNull();
    }

    @Test
    public void removeTestOk() {
        Mockito.doNothing().when(taskRepository).deleteById(any());
        Mockito.when(taskRepository.existsById(anyLong())).thenReturn(true);
        MessageResponse response = taskService.remove(2L);
        Assertions.assertThat(response.getMessage()).isEqualTo("task deleted");
    }

    @After
    public void reset() {
        Mockito.reset(taskRepository);
    }
}
