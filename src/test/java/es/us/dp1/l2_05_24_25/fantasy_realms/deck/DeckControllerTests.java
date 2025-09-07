package es.us.dp1.l2_05_24_25.fantasy_realms.deck;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;

@WebMvcTest(controllers = DeckController.class)
public class DeckControllerTests {

    @MockBean
    private DeckService deckService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testFindDeckById_Success() throws Exception {

        Deck mockDeck = new Deck(Arrays.asList(
                new Card("Card 1", null, 10),
                new Card("Card 2", null, 20)));
        when(deckService.findDeckById(anyInt())).thenReturn(mockDeck);

        mockMvc.perform(get("/api/v1/deck/1")
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cards.size()").value(2))
                .andExpect(jsonPath("$.cards[0].name").value("Card 1"))
                .andExpect(jsonPath("$.cards[1].name").value("Card 2"));

        verify(deckService, times(1)).findDeckById(1);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    void testFindDeckById_NotFound() throws Exception {

        when(deckService.findDeckById(anyInt())).thenThrow(new ResourceNotFoundException("Deck", "id", 1));

        mockMvc.perform(get("/api/v1/deck/1")
                .with(csrf())        
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(deckService, times(1)).findDeckById(1);
    }
}
