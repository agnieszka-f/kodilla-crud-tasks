package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TrelloCardF {
    private String name;
    private String description;
    private String pos;
    private String listId;
}
