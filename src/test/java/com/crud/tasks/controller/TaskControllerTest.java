package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldGetTasks() throws Exception {
        // Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(16L, "Test", "Test description"));
        List<TaskDto> taskDtoList = new ArrayList<>();
        taskDtoList.add(new TaskDto(16L, "Test", "Test description"));
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtoList);

        // When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(16)))
                .andExpect(jsonPath("$[0].title", is("Test")))
                .andExpect(jsonPath("$[0].content", is("Test description")));
    }

    @Test
    public void shouldGetTask() throws Exception {
        // Given
        Task task = new Task(16L, "Test", "Test description");
        TaskDto taskDto = new TaskDto(16L, "Test", "Test description");
        when(dbService.getTask(16L)).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // When & Then
        mockMvc.perform(get("/v1/task/getTask").param("taskId", "16").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(16)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test description")));
    }

    @Test
    public void shouldDeleteTask() throws Exception {
        // When & Then
        mockMvc.perform(delete("/v1/task/deleteTask").param("taskId", "16"))
                .andExpect(status().isOk());
        verify(dbService, times(1)).deleteTask(16L);
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        // Given
        Task task = new Task(16L, "Test", "Test description");
        TaskDto taskDto = new TaskDto(16L, "Test", "Test description");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(16)))
                .andExpect(jsonPath("$.title", is("Test")))
                .andExpect(jsonPath("$.content", is("Test description")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        // Given
        Task task = new Task(16L, "Test", "Test description");
        TaskDto taskDto = new TaskDto(16L, "Test", "Test description");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(any(Task.class))).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        // When & Then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk());
        verify(dbService, times(1)).saveTask(any(Task.class));
    }
}
