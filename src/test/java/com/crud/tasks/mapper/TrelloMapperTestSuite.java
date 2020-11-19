package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {
    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToList(){
        //Given
        List<TrelloListDto> trelloListDtos = Arrays.asList(new TrelloListDto("firstList", "1",false),
                                                            new TrelloListDto("secondList","2",false));
        List<TrelloList> result = trelloMapper.mapToList(trelloListDtos);
        //When
        int size = result.size();
        String nameOfFirstElement = result.get(0).getName();
        String nameOfSecondElement = result.get(1).getName();
        //Then
        Assert.assertEquals(2,size);
        Assert.assertEquals("firstList",nameOfFirstElement);
        Assert.assertEquals("secondList",nameOfSecondElement);
    }
    @Test
    public void testMapToListDto(){
        //Given
        List<TrelloList> trelloLists = Arrays.asList(new TrelloList("firstList", "1",false));

        List<TrelloListDto> result = trelloMapper.mapToListDto(trelloLists);
        //When
        int size = result.size();
        String id = result.get(0).getId();
        String name = result.get(0).getName();
        boolean isClosed = result.get(0).isClosed();
        //Then
        Assert.assertEquals(1,size);
        Assert.assertEquals("1",id);
        Assert.assertEquals("firstList", name);
        Assert.assertEquals(false, isClosed);
    }
    @Test
    public void testMapToCardDto(){
        //Given
        TrelloCardF trelloCard = new TrelloCardF("card_1", "first card", "10", "1");
        TrelloCardDto result = trelloMapper.mapToCartDto(trelloCard);
        //When
        String name = result.getName();
        String description = result.getDescription();
        String pos = result.getPos();
        String listId = result.getListId();
        //Then
        Assert.assertEquals("card_1",name);
        Assert.assertEquals("first card",description);
        Assert.assertEquals("10",pos);
        Assert.assertEquals("1",listId);
    }
    @Test
    public void testMapToCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card_2","second card", "11","2");
        TrelloCardF result = trelloMapper.mapToCard(trelloCardDto);
        //When
        String name = result.getName();
        String description = result.getDescription();
        String pos = result.getPos();
        String listId = result.getListId();
        //Then
        Assert.assertEquals("card_2",name);
        Assert.assertEquals("second card",description);
        Assert.assertEquals("11",pos);
        Assert.assertEquals("2",listId);
    }
    @Test
    public void testMapToBoards(){
        //Given
        List<TrelloListDto> trelloListDtos = Arrays.asList(new TrelloListDto("firstList", "1",false));
        List<TrelloBoardDto> trelloBoardDtos = Arrays.asList(new TrelloBoardDto("board_1","1",trelloListDtos));
        List<TrelloBoard> result = trelloMapper.mapToBoards(trelloBoardDtos);
        //When
        String name = result.get(0).getName();
        String id = result.get(0).getId();
        String listName = result.get(0).getLists().get(0).getName();
        String listId = result.get(0).getLists().get(0).getId();
        //Then
        Assert.assertEquals("board_1",name);
        Assert.assertEquals("1",id);
        Assert.assertEquals("firstList",listName);
        Assert.assertEquals("1",listId);
    }
    @Test
    public void testMapToBoardsDto(){
        //Given
        List<TrelloList> trelloLists = Arrays.asList(new TrelloList("secondList", "2",false));
        List<TrelloBoard> trelloBoards = Arrays.asList(new TrelloBoard("board_2","2",trelloLists));
        List<TrelloBoardDto> result = trelloMapper.mapToBoardsDto(trelloBoards);
        //When
        String name = result.get(0).getName();
        String id = result.get(0).getId();
        String listName = result.get(0).getLists().get(0).getName();
        String listId = result.get(0).getLists().get(0).getId();
        //Then
        Assert.assertEquals("board_2",name);
        Assert.assertEquals("2",id);
        Assert.assertEquals("secondList",listName);
        Assert.assertEquals("2",listId);
    }
}
