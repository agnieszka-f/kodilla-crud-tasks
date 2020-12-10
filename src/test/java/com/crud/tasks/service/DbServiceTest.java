package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import java.util.Optional;
import org.assertj.core.internal.bytebuddy.dynamic.DynamicType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {
    @Mock
    private TaskRepository repository;
    @InjectMocks
    private DbService dbService;
    @Test
    public void testGetAllTasks(){
        //Given
        List<Task> tasks = Arrays.asList(new Task(1L,"test task","test content"),
                                            new Task());
        when(repository.findAll()).thenReturn(tasks);
        //When
        List<Task> result = dbService.getAllTasks();
        //Then
        Assert.assertEquals(2,result.size());
        Assert.assertEquals("test task", result.get(0).getTitle());
        Assert.assertEquals(null, result.get(1).getTitle());
    }
    @Test
    public void testGetTaskById(){
        //Given
        Task task = new Task(2L, "task2","content2");
        Optional<Task> optionalTask = Optional.of(task);
        when(repository.findById(2L)).thenReturn(optionalTask);
        //When
        Task result = dbService.getTaskById(2L);
        //Then
        Assert.assertEquals("task2", result.getTitle());
        Assert.assertEquals("content2", result.getContent());
        Assert.assertEquals(Optional.of(2L).get(), result.getId());
    }
    @Test
    public void testSaveTask(){
        //Given
        Task task = new Task(3L, "task3","content3");
        when(repository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);
        //When
        Task result = dbService.saveTask(new Task());
        //Then
        Assert.assertEquals("task3", result.getTitle());
        Assert.assertEquals("content3", result.getContent());
        Assert.assertEquals(Optional.of(3L).get(), result.getId());
    }
    @Test
    public void testGetTask(){
        //Given
        Task task = new Task(4L, "task4","content4");
        Optional<Task> optionalTask = Optional.of(task);
        when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(optionalTask);
        //When
        Optional<Task> result = dbService.getTask(4L);
        //Then
        Assert.assertEquals("task4", result.get().getTitle());
        Assert.assertEquals("content4", result.get().getContent());
        Assert.assertEquals(Optional.of(4L).get(), result.get().getId());
    }
    @Test
    public void testDeleteById(){
        //Given
        Task task = new Task(5L, "task5","content5");
        //When
        dbService.deleteById(task.getId());
        //Then
        verify(repository, times(1)).deleteById(task.getId());
    }
}
