package es.us.dp1.l2_05_24_25.fantasy_realms.dto;


import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.TurnDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.DrawEnum;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

public class TurnDTOTests {
    private TurnDTO turnDTO;
    private Turn turn;
    private Player player;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");

        player = new Player(user, null);

        turn = new Turn();
        turn.setPlayer(player);
        turn.setTurnCount(5);
        turn.setDrawSource(DrawEnum.DECK);

        turnDTO = new TurnDTO(turn);
    }

    @Test
    void testTurnDTOInitialization() {
        assertNotNull(turnDTO);
        assertEquals("testUser", turnDTO.getUsername());
        assertEquals(5, turnDTO.getTurnCount());
        assertEquals(DrawEnum.DECK, turnDTO.getDrawSource());
    }

    @Test
    void testEmptyConstructor() {
        TurnDTO emptyTurnDTO = new TurnDTO();
        assertNull(emptyTurnDTO.getUsername());
        assertNull(emptyTurnDTO.getTurnCount());
        assertNull(emptyTurnDTO.getDrawSource());
    }

    @Test
    void testToString() {
        String expectedString = "TurnDTO{username=testUser, turnCount=5, drawSource=DECK}";
        assertEquals(expectedString, turnDTO.toString());
    }

    @Test
    void testNullTurnInitialization() {
        TurnDTO nullTurnDTO = new TurnDTO(null);
        assertNull(nullTurnDTO.getUsername());
        assertNull(nullTurnDTO.getTurnCount());
        assertNull(nullTurnDTO.getDrawSource());
    }
}
