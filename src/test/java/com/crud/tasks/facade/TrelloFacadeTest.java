package com.crud.tasks.facade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.service.TrelloService;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.crud.tasks.trello.validator.TrelloValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloFacadeTest {
    @InjectMocks
    TrelloFacade trelloFacade;
    @Mock
    TrelloMapper trelloMapper;
    @Mock
    TrelloService trelloService;
    @Mock
    TrelloValidator trelloValidator;
    @Mock
    TrelloClient trelloClient;
    @Test
    public void shouldFetchEmptyList(){
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("the_list","1",false));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("the_list","1",false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("test", "1", trelloLists));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("test", "1", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(new ArrayList<>());
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(new ArrayList());
        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //Then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(0,trelloBoardDtos.size());
    }
    @Test
    public void shouldFetchTrelloBoards(){
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("my_list","1",false));

        List<TrelloList> mappedTrelloLists = new ArrayList<>();
        mappedTrelloLists.add(new TrelloList("my_list","1",false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("my_task", "1", trelloLists));

        List<TrelloBoard> mappedTrelloBoards = new ArrayList<>();
        mappedTrelloBoards.add(new TrelloBoard("my_task", "1", mappedTrelloLists));

        when(trelloService.fetchTrelloBoards()).thenReturn(trelloBoards);
        when(trelloMapper.mapToBoards(trelloBoards)).thenReturn(mappedTrelloBoards);
        when(trelloMapper.mapToBoardsDto(anyList())).thenReturn(trelloBoards);
        when(trelloValidator.validateTrelloBoards(mappedTrelloBoards)).thenReturn(mappedTrelloBoards);

        //When
        List<TrelloBoardDto> trelloBoardDtos = trelloFacade.fetchTrelloBoards();
        //Then
        Assert.assertNotNull(trelloBoardDtos);
        Assert.assertEquals(1,trelloBoardDtos.size());
        trelloBoardDtos.forEach(
                x -> {
                    Assert.assertEquals("my_task",x.getName());
                     Assert.assertEquals("1",x.getId());

                     x.getLists().forEach( y-> {
                         Assert.assertEquals("my_list", y.getName());
                         Assert.assertEquals("1", y.getId());
                         Assert.assertEquals(false,y.isClosed());
                     });
        });
    }
    @Test
    public void test2(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test name","test description","1081","2");
        TrelloCardF trelloCard =  new TrelloCardF("test name","test description","1081","2");
        CreatedTrelloCardDto newCard = new CreatedTrelloCardDto("2","test name","http:\\test.pl");
        when(trelloMapper.mapToCard(trelloCardDto)).thenReturn(trelloCard);
        when(trelloMapper.mapToCartDto(trelloCard)).thenReturn(trelloCardDto);
        when(trelloService.createdTrelloCard(trelloCardDto)).thenReturn(newCard);
        //When
        CreatedTrelloCardDto result = trelloFacade.createCard(trelloCardDto);
        String id = result.getId();
        String name = result.getName();
        String shortUrl = result.getShortUrl();
        //Then
        Assert.assertEquals("2",id);
        Assert.assertEquals("test name",name);
        Assert.assertEquals("http:\\test.pl",shortUrl);
    }
}
