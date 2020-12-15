package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTestSuite {
    @InjectMocks
    private TaskMapper taskMapper;
    @Test
    public void testMapToTask(){
        //Given
        TaskDto taskDto = new TaskDto(1L,"test task","test description");
        Task result = taskMapper.mapToTask(taskDto);
        //When
        Long id = result.getId();
        String title = result.getTitle();
        String content = result.getContent();
        //Then
        Assert.assertEquals(java.util.Optional.of(1L).get(), id);
        Assert.assertEquals("test task", title);
        Assert.assertEquals("test description", content);
    }
    @Test
    public void testMapToTaskDto(){
        //Given
        Task task = new Task(1L,"test my task","test my description");
        TaskDto result = taskMapper.mapToTaskDto(task);
        //When
        Long id = result.getId();
        String title = result.getTitle();
        String content = result.getContent();
        //Then
        Assert.assertEquals(java.util.Optional.of(1L).get(), id);
        Assert.assertEquals("test my task", title);
        Assert.assertEquals("test my description", content);
    }
    @Test
    public void testMapToTaskDtoList(){
        //Given
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(new TaskDto(1L,"first test task", "first description task"));
        taskDtos.add(new TaskDto(2L,"second test task", "second description task"));

        List<Task> result = taskMapper.mapToTaskList(taskDtos);
        //When
        int size = result.size();
        String firstTitle = result.get(0).getTitle();
        String secondTitle = result.get(1).getTitle();
        //Then
        Assert.assertEquals(2,size);
        Assert.assertEquals("first test task",firstTitle);
        Assert.assertEquals("second test task",secondTitle);
    }
    @Test
    public void testMapToTaskList(){
        //Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(1L,"first task", "first description"));
        tasks.add(new Task(2L,"second task", "second description"));

        List<TaskDto> result = taskMapper.mapToTaskDtoList(tasks);
        //When
        int size = result.size();
        String firstTitle = result.get(0).getTitle();
        String secondTitle = result.get(1).getTitle();
        //Then
        Assert.assertEquals(2,size);
        Assert.assertEquals("first task",firstTitle);
        Assert.assertEquals("second task",secondTitle);
    }
}
