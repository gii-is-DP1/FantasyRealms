
package es.us.dp1.l2_05_24_25.fantasy_realms.player;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

class PlayerTests {

    private Player player;
    private User user;
    private Match match;
    private List<Card> hand;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("player1");
        player = new Player(user, PlayerType.CREADOR);

        // Simulamos una mano de cartas vacía o inicial
        hand = new ArrayList<>();
        player.setPlayerHand(hand);

        match = new Match();
        player.setMatchPlayed(match);
    }

    @Test
    void testPlayerInitialization() {
        assertNotNull(player);
        assertEquals(user, player.getUser());
        assertEquals(hand, player.getPlayerHand());
        assertEquals(match, player.getMatchPlayed());
        assertEquals(PlayerType.CREADOR, player.getRole()); // Al crearlo, se asigna como CREADOR
    }

    @Test
    void testAddCardToHand() {
        Card newCard = new Card();
        player.getPlayerHand().add(newCard);

        assertEquals(1, player.getPlayerHand().size());
        assertTrue(player.getPlayerHand().contains(newCard));
    }

    @Test
    void testRemoveCardFromHand() {
        Card newCard = new Card();
        player.getPlayerHand().add(newCard);
        player.getPlayerHand().remove(newCard);

        assertEquals(0, player.getPlayerHand().size());
        assertFalse(player.getPlayerHand().contains(newCard));
    }

    @Test
    void testPlayerInLobbyOrPlaying() {
        assertTrue(player.isInLobbyOrPlaying());
        match.setEndDate(LocalDateTime.now());
        assertFalse(player.isInLobbyOrPlaying());
    }

    @Test
    void testPlayerScore() {
        player.setScore(100);
        assertEquals(100, player.getScore());
    }

    @Test
    void testPlayerRole() {
        player.setRole(PlayerType.PARTICIPANTE);
        assertEquals(PlayerType.PARTICIPANTE, player.getRole());
    }

    @Test
    void testInitialScore() {
        assertNull(player.getScore());

        player.setScore(50);
        assertEquals(50, player.getScore());
    }

    @Test
    void testPlayerRoleChanges() {
        player.setRole(PlayerType.PARTICIPANTE);
        assertEquals(PlayerType.PARTICIPANTE, player.getRole());

        player.setRole(PlayerType.CREADOR);
        assertEquals(PlayerType.CREADOR, player.getRole());

        player.setRole(null);
        assertNull(player.getRole());
    }

}