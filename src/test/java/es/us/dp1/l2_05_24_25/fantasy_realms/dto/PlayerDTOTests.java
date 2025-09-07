package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.CardDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.PlayerDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

public class PlayerDTOTests {
    @Test
    public void testNoArgsConstructor() {
        PlayerDTO playerDTO = new PlayerDTO();
        assertNull(playerDTO.getId());
        assertNull(playerDTO.getUsername());
        assertNull(playerDTO.getScore());
        assertNull(playerDTO.getPlayerHand());
        assertNull(playerDTO.getRol());
    }

    @Test
    public void testConstructorWithParameters() {
        List<CardDTO> hand = new ArrayList<>();
        hand.add(new CardDTO(new Card("Card1", CardType.MAGO, 10)));
        hand.add(new CardDTO(new Card("Card2", CardType.BESTIA, 15)));

        PlayerDTO playerDTO = new PlayerDTO(1, "player1", 100, PlayerType.CREADOR, hand);

        // Verificar que se asignen correctamente los valores
        assertEquals(1, playerDTO.getId());
        assertEquals("player1", playerDTO.getUsername());
        assertEquals(100, playerDTO.getScore());
        assertEquals(2, playerDTO.getPlayerHand().size());
        assertEquals(PlayerType.CREADOR, playerDTO.getRol());
    }

    @Test
    public void testConstructorFromPlayer() {
        // Crear un objeto User para asociarlo al Player
        User user = new User();
        user.setId(1);  // Suponiendo que User tiene un método setId
        user.setUsername("player1");

        // Crear las cartas
        List<Card> hand = new ArrayList<>();
        hand.add(new Card("Card1", CardType.MAGO, 10));
        hand.add(new Card("Card2", CardType.BESTIA, 15));

        // Crear un Player sin usar el constructor con 5 parámetros
        Player player = new Player();
        player.setUser(user);
        player.setScore(100);
        player.setRole(PlayerType.CREADOR);
        player.setPlayerHand(hand);

        // Convertir el Player a PlayerDTO
        PlayerDTO playerDTO = new PlayerDTO(player);

        // Verificar que los datos se copien correctamente
        assertEquals(1, playerDTO.getId());
        assertEquals("player1", playerDTO.getUsername());
        assertEquals(100, playerDTO.getScore());
        assertEquals(2, playerDTO.getPlayerHand().size());
        assertEquals(PlayerType.CREADOR, playerDTO.getRol());

        // Verificar que las cartas en playerHand se han convertido correctamente a CardDTO
        assertEquals("Card1", playerDTO.getPlayerHand().get(0).getName());
        assertEquals("Card2", playerDTO.getPlayerHand().get(1).getName());
    }

    @Test
    public void testPlayerDTOWithEmptyHand() {
        List<CardDTO> emptyHand = new ArrayList<>();
        PlayerDTO playerDTO = new PlayerDTO(1, "player1", 100, PlayerType.CREADOR, emptyHand);

        // Verificar que la mano esté vacía
        assertEquals(0, playerDTO.getPlayerHand().size());
    }

    @Test
    public void testPlayerDTOWithNullHand() {
        PlayerDTO playerDTO = new PlayerDTO(1, "player1", 100, PlayerType.CREADOR, null);

        // Verificar que la mano sea nula
        assertNull(playerDTO.getPlayerHand());
    }

    @Test
    public void testSettersAndGetters() {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(1);
        playerDTO.setUsername("player1");
        playerDTO.setScore(100);
        playerDTO.setRol(PlayerType.CREADOR);

        List<CardDTO> hand = new ArrayList<>();
        hand.add(new CardDTO(new Card("Card1", CardType.MAGO, 10)));
        playerDTO.setPlayerHand(hand);

        assertEquals(1, playerDTO.getId());
        assertEquals("player1", playerDTO.getUsername());
        assertEquals(100, playerDTO.getScore());
        assertEquals(PlayerType.CREADOR, playerDTO.getRol());
        assertEquals(1, playerDTO.getPlayerHand().size());
        assertEquals("Card1", playerDTO.getPlayerHand().get(0).getName());
    }
}
