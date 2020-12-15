package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloFacade trelloFacade;
    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception{
        //Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        Mockito.when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);
        //When & Then
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                                                                    .andExpect(status().is(200))
                                                                    .andExpect(jsonPath("$", hasSize(0)));
    }
    @Test
    public void shouldFetchTrelloBoards() throws Exception{
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("Test List","1",false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("Test Task","1",trelloLists));

        Mockito.when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);
        //When & Then
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)))
                        .andExpect(jsonPath("$[0].name", Matchers.is("Test Task")))
                        .andExpect(jsonPath("$[0].id", Matchers.is("1")))
                        .andExpect(jsonPath("$[0].lists", hasSize(1)))
                        .andExpect(jsonPath("$[0].lists[0].name", Matchers.is("Test List")))
                        .andExpect(jsonPath("$[0].lists[0].id", Matchers.is("1")))
                        .andExpect(jsonPath("$[0].lists[0].closed", Matchers.is(false)));
    }
    @Test
    public void shouldCreateTrelloCard() throws Exception {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test", "Test description","Top","1");
        CreatedTrelloCardDto newCard = new CreatedTrelloCardDto("323","Test","Http:\\test.com");

        Mockito.when(trelloFacade.createCard(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(newCard);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        //When & Then
        mockMvc.perform(post("/v1/trello/createTrelloCard").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is("323")))
                .andExpect(jsonPath("$.name", Matchers.is("Test")))
                .andExpect(jsonPath("$.shortUrl",Matchers.is("Http:\\test.com")));
    }

}