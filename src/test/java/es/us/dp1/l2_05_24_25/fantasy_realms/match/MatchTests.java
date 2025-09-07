package es.us.dp1.l2_05_24_25.fantasy_realms.match;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchTests {

    private Match match;
    private Deck deck;
    private Discard discard;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        deck = mock(Deck.class);
        discard = mock(Discard.class);
        players = new ArrayList<>();

        Player player1 = mock(Player.class);
        when(player1.getRole()).thenReturn(PlayerType.CREADOR);
        Player player2 = mock(Player.class);
        when(player2.getRole()).thenReturn(PlayerType.PARTICIPANTE);
        Player player3 = mock(Player.class);
        when(player3.getRole()).thenReturn(PlayerType.PARTICIPANTE);

        players.addAll(Arrays.asList(player1, player2, player3));

        match = new Match("Test Match", deck, discard, players);
    }

    @Test
    void testIsInProgress() {
        match.setStartDate(LocalDateTime.now());
        assertTrue(match.isInProgress());
        
        match.setEndDate(LocalDateTime.now());
        assertFalse(match.isInProgress());
    }

    @Test
    void testIsFinished() {
        assertFalse(match.isFinished());

        match.setEndDate(LocalDateTime.now());
        assertTrue(match.isFinished());
    }

    @Test
    void testAddPlayer() {
        Player newPlayer = mock(Player.class);
        match.addPlayer(newPlayer);

        assertEquals(4, match.getPlayers().size());
        verify(newPlayer).setMatchPlayed(match);
    }

    @Test
    void testReachedMaxPlayers() {

        for (int i = 0; i < 4; i++) {
            Player participant = mock(Player.class);
            when(participant.getRole()).thenReturn(PlayerType.PARTICIPANTE); 
            match.addPlayer(participant);
        }
    
        assertTrue(match.reachedMaxPlayers(), "El número máximo de jugadores no fue alcanzado correctamente.");
    }

    @Test
    void testNotReachedMinPlayers() {
        match.getPlayers().clear(); 
        assertTrue(match.notReachedMinPlayers());
    
        for (int i = 0; i < 2; i++) {
            Player player = mock(Player.class);
            when(player.getRole()).thenReturn(PlayerType.PARTICIPANTE);
            match.addPlayer(player);
        }
    
        assertTrue(match.notReachedMinPlayers());
    
        Player player3 = mock(Player.class);
        when(player3.getRole()).thenReturn(PlayerType.PARTICIPANTE);
        match.addPlayer(player3);
    
        assertFalse(match.notReachedMinPlayers());
    }

    @Test
    void testGetCreador() {
        Player creador = match.getCreador();
        assertNotNull(creador);
        assertEquals(PlayerType.CREADOR, creador.getRole());
    }

    @Test
    void testActivateScoringPhase() {
        Turn turn = mock(Turn.class);
        when(turn.getTurnCount()).thenReturn(5);

        match.setCurrentTurn(turn);
        match.activateScoringPhase();

        assertTrue(match.isInScoringPhase());
        assertEquals(5, match.getScoringStartTurn());
    }

    @Test
    void testGetMaxSpecialTurn() {
        Turn turn = mock(Turn.class);
        when(turn.getTurnCount()).thenReturn(5);

        match.setCurrentTurn(turn);
        match.activateScoringPhase();

        assertEquals(8, match.getMaxSpecialTurn()); // 5 + 3 players
    }

    @Test
    void testFirstPlayer() {
        Player firstPlayer = match.firstPlayer();
        assertNotNull(firstPlayer);
        assertTrue(players.contains(firstPlayer));
    }
}
