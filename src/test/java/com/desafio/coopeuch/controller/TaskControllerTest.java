package com.desafio.coopeuch.controller;

import com.desafio.coopeuch.model.request.TaskRequest;
import com.desafio.coopeuch.model.response.MessageResponse;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    private static final String RESPONSE_NULL = "Something is missing in the request";
    private static final String JSON_RESPONSE_NULL = "{" +
            "\"message\":\"Something is missing in the request\"" +
            "}";


    @Test
    public void addTestOk() throws Exception {
        String json = "{\n" +
                "    \"id\":1,\n" +
                "    \"description\":\"test3\",\n" +
                "    \"creationDate\":\"2021-07-06T17:00:00\",\n" +
                "    \"active\":true\n" +
                "}";
        MessageResponse response = new MessageResponse();
        response.setMessage("task added");
        String jsonResponse = "{" +
                "\"message\":\"task added\"" +
                "}";
        Mockito.when(service.add(Mockito.any(TaskRequest.class))).thenReturn(response);
        this.mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
                //.andExpect(content().string(containsString("task added")));
                .andExpect(content().json(jsonResponse));
    }

    @Test
    public void addTestNull() throws Exception {
        String json = "{\n" +
                "    \"id\":1,\n" +
                "    \"description\":null,\n" +
                "    \"creationDate\":\"2021-07-06T17:00:00\",\n" +
                "    \"active\":true\n" +
                "}";
        MessageResponse responseNull = new MessageResponse();
        responseNull.setMessage(RESPONSE_NULL);
        Mockito.when(service.add(Mockito.any(TaskRequest.class))).thenReturn(responseNull);
        this.mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
                //.andExpect(content().string(containsString("Something is missing in the request")));
                .andExpect(content().json(JSON_RESPONSE_NULL));
    }

    @Test
    public void editTestNull() throws Exception {
        String json = "{\n" +
                "    \"id\":2,\n" +
                "    \"description\":null,\n" +
                "    \"creationDate\":\"2021-07-06T17:00:00\",\n" +
                "    \"active\":true\n" +
                "}";
        MessageResponse responseNull = new MessageResponse();
        responseNull.setMessage(RESPONSE_NULL);
        Mockito.when(service.edit(Mockito.any(TaskRequest.class))).thenReturn(responseNull);
        this.mockMvc.perform(put("/task").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
                //.andExpect(content().string(containsString("Something is missing in the request")));
                .andExpect(content().json(JSON_RESPONSE_NULL));
    }

    @Test
    public void editTestOk() throws Exception {
        String json = "{\n" +
                "    \"id\":2,\n" +
                "    \"description\":\"test3 editado\",\n" +
                "    \"creationDate\":\"2021-07-06T17:00:00\",\n" +
                "    \"active\":true\n" +
                "}";
        MessageResponse response = new MessageResponse();
        response.setMessage("task edited");
        String jsonResponse = "{" +
                "\"message\":\"task edited\"" +
                "}";
        Mockito.when(service.edit(Mockito.any(TaskRequest.class))).thenReturn(response);
        this.mockMvc.perform(put("/task").contentType(MediaType.APPLICATION_JSON).content(json)).andDo(print()).andExpect(status().isOk())
                //.andExpect(content().string(containsString("task edited")));
                .andExpect(content().json(jsonResponse));
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
        MessageResponse response = new MessageResponse();
        response.setMessage("task deleted");
        String jsonResponse = "{" +
                "\"message\":\"task deleted\"" +
                "}";
        Mockito.when(service.remove(Mockito.any())).thenReturn(response);
        this.mockMvc.perform(delete("/task").content("2")).andDo(print()).andExpect(status().isOk())
                //.andExpect(content().string(containsString("task deleted")));
                .andExpect(content().json(jsonResponse));
    }

    @Test
    public void getTaskTestOk() throws Exception {
        TaskResponse response = new TaskResponse();
        response.setId(1L);
        response.setDescription("test unitario");
        String str = "2021-07-06 17:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        response.setCreationDate(LocalDateTime.parse(str, formatter));
        response.setActive(true);
        String jsonResponse = "{\n" +
                "    \"id\":1,\n" +
                "    \"description\":\"test unitario\",\n" +
                "    \"creationDate\":\"2021-07-06T17:00:00\",\n" +
                "    \"active\":true\n" +
                "}";
        Mockito.when(service.getTask(Mockito.any())).thenReturn(response);
        this.mockMvc.perform(get("/task/id").content("1")).andDo(print()).andExpect(status().isOk())
                //.andExpect(content().string(containsString("task deleted")));
                .andExpect(content().json(jsonResponse));
    }
}
