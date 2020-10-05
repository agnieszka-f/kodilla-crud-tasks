package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/trello")
public class TrelloController {
    @Autowired
    TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        trelloBoards.forEach(
                trelloBoardDto -> {
                    System.out.println(trelloBoardDto.getId()+" "+trelloBoardDto.getName());
                    System.out.println("This board contains lists: ");
                    trelloBoardDto.getLists().forEach(
                            trelloList -> System.out.println(trelloList.getId()+" "+trelloList.getName()+" "+ trelloList.isClosed())
                    );
                }
        );


        /*****List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

               trelloBoards.stream().filter(x -> {
                   try {
                       return x.getClass().getDeclaredField("name").toString().contains("name")
                               && x.getClass().getDeclaredField("id").toString().contains("id")
                               && x.getName().contains("Kodilla");
                   } catch (NoSuchFieldException e) {
                       e.printStackTrace();
                       return false;
                   }
               }).forEach(x->System.out.println(x.getId() + " " + x.getName()));****/
    }
    @RequestMapping(method = RequestMethod.POST, value="createTrelloCard")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto){
        return trelloClient.createNewCard(trelloCardDto);
    }
}
