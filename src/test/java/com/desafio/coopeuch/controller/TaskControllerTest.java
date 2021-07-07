package com.desafio.coopeuch.controller;

import com.desafio.coopeuch.model.entity.Task;
import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.TaskResponse;
import com.desafio.coopeuch.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    @Test
    public void addTestOk() throws Exception {
        String json = "{\n" +
                "    \"id\":1,\n" +
                "    \"description\":\"test3\",\n" +
                "    \"creationDate\":\"2021-07-06T17:00:00\",\n" +
                "    \"active\":true\n" +
                "}";
        Mockito.when(service.add(Mockito.any(TaskRequest.class))).thenReturn("task added");
        this.mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("task added")));
    }

    @Test
    public void addTestNull() throws Exception {
        String json = "{\n" +
                "    \"id\":1,\n" +
                "    \"description\":null,\n" +
                "    \"creationDate\":\"2021-07-06T17:00:00\",\n" +
                "    \"active\":true\n" +
                "}";
        Mockito.when(service.add(Mockito.any(TaskRequest.class))).thenReturn("Something is missing in the request");
        this.mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Something is missing in the request")));
    }

    @Test
    public void editTestNull() throws Exception {
        String json = "{\n" +
                "    \"id\":2,\n" +
                "    \"description\":null,\n" +
                "    \"creationDate\":\"2021-07-06T17:00:00\",\n" +
                "    \"active\":true\n" +
                "}";
        Mockito.when(service.edit(Mockito.any(TaskRequest.class))).thenReturn("Something is missing in the request");
        this.mockMvc.perform(put("/task").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Something is missing in the request")));
    }

    @Test
    public void editTestOk() throws Exception {
        String json = "{\n" +
                "    \"id\":2,\n" +
                "    \"description\":\"test3 editado\",\n" +
                "    \"creationDate\":\"2021-07-06T17:00:00\",\n" +
                "    \"active\":true\n" +
                "}";
        Mockito.when(service.edit(Mockito.any(TaskRequest.class))).thenReturn("task edited");
        this.mockMvc.perform(put("/task").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("task edited")));
    }

    @Test
    public void listAllTest() throws Exception {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(2L);
        taskResponse.setDescription("test");
        taskResponse.setCreationDate(LocalDateTime.now());
        taskResponse.setActive(true);
        List<TaskResponse> list = new ArrayList<>();
        list.add(taskResponse);
        Mockito.when(service.list()).thenReturn(list);
        this.mockMvc.perform(get("/task")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void removeTestOk() throws Exception {
        Mockito.when(service.remove(Mockito.any())).thenReturn("task deleted");
        this.mockMvc.perform(delete("/task").content("2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("task deleted")));
    }
}
