package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match.GameInvitationDTO;

public class GameInvitationDTOTests {

    @Test
    void testDefaultConstructor() {
        
        GameInvitationDTO dto = new GameInvitationDTO();

        // Verificaciones explícitas para los valores iniciales
        assertNull(dto.getSenderUsername());
        assertNull(dto.getReceiverUsername());
        assertFalse(dto.isStatus());
        assertNull(dto.getMatchId());
    }
    
}
