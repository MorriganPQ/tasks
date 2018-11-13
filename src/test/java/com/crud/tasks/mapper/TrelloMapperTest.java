package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void shouldMapToBoards() {
        // Given
        List<TrelloBoardDto> trelloBoardDtoList = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "Mapper test", new ArrayList<>());
        trelloBoardDto.getLists().add(new TrelloListDto("2", "Test list", false));
        trelloBoardDto.getLists().add(new TrelloListDto("3", "Test list v2", true));
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("33", "Mapper test v2", new ArrayList<>());
        trelloBoardDto2.getLists().add(new TrelloListDto("34", "Test list", true));
        trelloBoardDtoList.add(trelloBoardDto);
        trelloBoardDtoList.add(trelloBoardDto2);

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtoList);

        // Then
        assertEquals(2, trelloBoards.size());
        assertEquals(2, trelloBoards.get(0).getLists().size());
        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals("Mapper test", trelloBoards.get(0).getName());
        assertEquals("34", trelloBoards.get(1).getLists().get(0).getId());
        assertEquals("Test list v2", trelloBoards.get(0).getLists().get(1).getName());
        assertFalse(trelloBoards.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void shouldMapToBoardsDto() {
        // Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1", "Mapper test", new ArrayList<>());
        trelloBoard.getLists().add(new TrelloList("2", "Test list", false));
        trelloBoard.getLists().add(new TrelloList("3", "Test list v2", true));
        TrelloBoard trelloBoard2 = new TrelloBoard("33", "Mapper test v2", new ArrayList<>());
        trelloBoard2.getLists().add(new TrelloList("34", "Test list", true));
        trelloBoards.add(trelloBoard);
        trelloBoards.add(trelloBoard2);

        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoards);

        // Then
        assertEquals(2, trelloBoardDtoList.size());
        assertEquals(2, trelloBoardDtoList.get(0).getLists().size());
        assertEquals("1", trelloBoardDtoList.get(0).getId());
        assertEquals("Mapper test", trelloBoardDtoList.get(0).getName());
        assertEquals("34", trelloBoardDtoList.get(1).getLists().get(0).getId());
        assertEquals("Test list v2", trelloBoardDtoList.get(0).getLists().get(1).getName());
        assertFalse(trelloBoardDtoList.get(0).getLists().get(0).isClosed());
    }

    @Test
    public void shouldMapToLists() {
        // Given
        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(new TrelloListDto("2", "Test list", false));
        trelloListDtoList.add(new TrelloListDto("3", "Test list v2", true));
        trelloListDtoList.add(new TrelloListDto("34", "Test list", true));

        //When
        List<TrelloList> trelloLists = trelloMapper.mapToLists(trelloListDtoList);

        // Then
        assertEquals(3, trelloLists.size());
        assertEquals("3", trelloLists.get(1).getId());
        assertEquals("Test list", trelloLists.get(0).getName());
        assertTrue(trelloLists.get(2).isClosed());
    }

    @Test
    public void shouldMapToListsDto() {
        // Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("2", "Test list", false));
        trelloLists.add(new TrelloList("3", "Test list v2", true));
        trelloLists.add(new TrelloList("34", "Test list", true));

        //When
        List<TrelloListDto> trelloListDtoList = trelloMapper.mapToListsDto(trelloLists);

        // Then
        assertEquals(3, trelloListDtoList.size());
        assertEquals("3", trelloListDtoList.get(1).getId());
        assertEquals("Test list", trelloListDtoList.get(0).getName());
        assertTrue(trelloListDtoList.get(2).isClosed());
    }

    @Test
    public void shouldMapToCard() {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test card", "Mapper test", "top", "8");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        // Then
        assertEquals("Test card", trelloCard.getName());
        assertEquals("Mapper test", trelloCard.getDescription());
        assertEquals("top", trelloCard.getPos());
        assertEquals("8", trelloCard.getListId());
    }

    @Test
    public void shouldMapToCardDto() {
        // Given
        TrelloCard trelloCard = new TrelloCard("Test card", "Mapper test", "top", "8");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        // Then
        assertEquals("Test card", trelloCardDto.getName());
        assertEquals("Mapper test", trelloCardDto.getDescription());
        assertEquals("top", trelloCardDto.getPos());
        assertEquals("8", trelloCardDto.getListId());
    }
}
