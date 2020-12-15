package com.crud.tasks.service;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.AdminConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {
    @InjectMocks
    private  TrelloService trelloService;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService  emailService;
    @Mock
    private AdminConfig adminConfig;
    @Test
    public void testShouldReturnEmptyListTrelloBoardsDto(){
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);
        //When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();
        //Then
        Assert.assertTrue(result.isEmpty());
    }
    @Test
    public void testShouldReturnOneElementOnListTrelloBoardsDto(){
        //Given
        List<TrelloBoardDto> trelloBoardDtos = Arrays.asList(new TrelloBoardDto());
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);
        //When
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();
        //Then
        Assert.assertEquals(1,result.size());
        Assert.assertNull(result.get(0).getId());
        Assert.assertNull(result.get(0).getName());
    }
    @Test
    public void testShouldReturnNewCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test","test","t","100");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("test","test","test");
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto result = trelloService.createdTrelloCard(trelloCardDto);
        //Then
        Assert.assertEquals("test",result.getShortUrl());
    }
    @Test
    public void testShouldReturnEmptyCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test2","test2","t2","105");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto();
        when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        //When
        CreatedTrelloCardDto result = trelloService.createdTrelloCard(trelloCardDto);
        //Then
        Assert.assertEquals(null,result.getShortUrl());
        Assert.assertEquals(null,result.getId());
        Assert.assertEquals(null,result.getName());
    }
}
