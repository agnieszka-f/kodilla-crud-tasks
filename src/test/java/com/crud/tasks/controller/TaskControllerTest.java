package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskMapper taskMapper;
    @MockBean
    private DbService dbService;
    @Test
    public void testGetTasks() throws Exception{
        //Given
        List<TaskDto> taskDtoList = Arrays.asList(
                new TaskDto(1L,"test title dto1","test content dto1"),
                new TaskDto(2L, "test title dto2", "test content dto2")
        );
        List<Task> taskList = Arrays.asList(
                new Task(1L,"test title 1","test content1"),
                new Task(2L, "test title 2", "test content2")
        );
        Mockito.when(dbService.getAllTasks()).thenReturn(taskList);
        Mockito.when(taskMapper.mapToTaskDtoList(ArgumentMatchers.<Task>anyList())).thenReturn(taskDtoList);

        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].title", Matchers.is("test title dto1")))
                .andExpect(jsonPath("$[0].content",Matchers.is("test content dto1")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].title", Matchers.is("test title dto2")))
                .andExpect(jsonPath("$[1].content",Matchers.is("test content dto2")));
    }
    @Test
    public void testGetTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(2L,"test task dto", "test content dto");
        Task task = new Task(2L,"test task", "test content");
        Optional<Task> optionalTask = Optional.of(task);

        Mockito.when(dbService.getTask(2L)).thenReturn(optionalTask);
        Mockito.when(taskMapper.mapToTaskDto(optionalTask.get())).thenReturn(taskDto);

        String id = Long.toString(2L);
        //When & Then
        mockMvc.perform(get("/v1/tasks/{taskId}", 2L).contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.title", Matchers.is("test task dto")))
                .andExpect(jsonPath("$.content", Matchers.is("test content dto")));
    }
    @Test
    public void testCreateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(2L,"test task dto", "test content dto");
        Task task = new Task(2L,"test task", "test content");

        Mockito.when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        Mockito.when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String content = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(content))
                .andExpect(status().isOk());
    }
    @Test
    public void testUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(2L,"test task dto", "test content dto");
        Task task = new Task(2L,"test task", "test content");

        Mockito.when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        Mockito.when(dbService.saveTask(ArgumentMatchers.any(Task.class))).thenReturn(task);
        Mockito.when(taskMapper.mapToTaskDto(ArgumentMatchers.any(Task.class))).thenReturn(taskDto);

        Gson gson = new Gson();
        String content = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(put("/v1/tasks").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(content)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.title", Matchers.is("test task dto")))
                .andExpect(jsonPath("$.content", Matchers.is("test content dto")));
    }
    @Test
    public void testDeleteTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(4L,"test task dto", "test content dto");
        Task task = new Task(4L,"test task", "test content");
        Optional<Task> optionalTask = Optional.of(task);

        Mockito.when(dbService.getTask(4L)).thenReturn(optionalTask);

        String id = Long.toString(4L);
        //When & Then
        mockMvc.perform(delete("/v1/tasks/{taskId}", id)
                .contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

}
