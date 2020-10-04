package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.*;

@Component
public class TrelloClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String trelloUsername;

        public List<TrelloBoardDto> getTrelloBoards(){

        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(getUrl(), TrelloBoardDto[].class);

        if(Optional.ofNullable(boardsResponse).isPresent()) return Arrays.asList(boardsResponse);
        else return new ArrayList<>();

    }
    private URI getUrl(){
        return  UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/"+ trelloUsername + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                //.queryParam("fields", "name,id")
                .build().encode().toUri();
        }
}


