package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloMapper {
    public TrelloBoard mapToBoard(final TrelloBoardDto trelloBoardDto){
        return new TrelloBoard(trelloBoardDto.getId(),
                                trelloBoardDto.getName(),
                                mapToList(trelloBoardDto.getLists()));
    }
    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardDto){
        return trelloBoardDto.stream()
                .map(x -> new TrelloBoard(x.getName(),x.getId(),mapToList(x.getLists())))
                .collect(Collectors.toList());
    }
    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> trelloBoards){
        return trelloBoards.stream()
                .map(x-> new TrelloBoardDto(x.getName(),x.getId(),mapToListDto(x.getLists())))
                .collect(Collectors.toList());
    }
    public List<TrelloList> mapToList(final List<TrelloListDto> trelloListDto ){
        return trelloListDto.stream()
                .map(x -> new TrelloList(x.getName(),x.getId(),x.isClosed()))
                .collect(Collectors.toList());
    }
    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloLists){
        return trelloLists.stream()
                .map(x-> new TrelloListDto(x.getName(),x.getId(),x.isClosed()))
                .collect(Collectors.toList());
    }

    public TrelloCardDto mapToCartDto(final TrelloCardF trelloCardF){
        return new TrelloCardDto(trelloCardF.getName(),trelloCardF.getDescription(),trelloCardF.getPos(),trelloCardF.getListId());
    }
    public TrelloCardF mapToCard(final TrelloCardDto trelloCardDto){
        return new TrelloCardF(trelloCardDto.getName(),trelloCardDto.getDescription(),trelloCardDto.getPos(),trelloCardDto.getListId());
    }
}
