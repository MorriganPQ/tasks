package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloMapper {
    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardDtoList) {
        return trelloBoardDtoList.stream()
                .map(trelloBoardDto -> new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(), mapToLists(trelloBoardDto.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> trelloBoards) {
        return trelloBoards.stream()
                .map(trelloBoard -> new TrelloBoardDto(trelloBoard.getId(), trelloBoard.getName(), mapToListsDto(trelloBoard.getLists())))
                .collect(Collectors.toList());
    }

    public List<TrelloList> mapToLists(final List<TrelloListDto> trelloListDtoList) {
        return trelloListDtoList.stream()
                .map(trelloListDto -> new TrelloList(trelloListDto.getId(), trelloListDto.getName(), trelloListDto.isClosed()))
                .collect(Collectors.toList());
    }

    public List<TrelloListDto> mapToListsDto(final List<TrelloList> trelloLists) {
        return trelloLists.stream()
                .map(trelloList -> new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(Collectors.toList());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(), trelloCardDto.getPos(), trelloCardDto.getListId());
    }

    public TrelloCardDto mapToCardDto(final TrelloCard trelloCard) {
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(), trelloCard.getPos(), trelloCard.getListId());
    }
}
