package es.us.dp1.l2_05_24_25.fantasy_realms.turn;


import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

@ExtendWith(MockitoExtension.class)
public class TurnSchedulerTest {

    @Mock
    private MatchService matchService;

    @Mock
    private TurnService turnService;

    @InjectMocks
    private TurnScheduler turnScheduler;

    @Mock
    private Match match;

    @Mock
    private Turn currentTurn;

    @Mock
    private Player turnPlayer;

    @Mock
    private User userInTurn;

    @Test
    public void testCheckTurnTimeoutForExpiredTurn() {

        when(matchService.findMatchesInProgress()).thenReturn(List.of(match));
        when(match.getCurrentTurn()).thenReturn(currentTurn);

        when(currentTurn.getTurnStartTime()).thenReturn(Instant.now().minus(Duration.ofSeconds(61)));
        when(currentTurn.getPlayer()).thenReturn(turnPlayer);
        when(turnPlayer.getUser()).thenReturn(userInTurn);

        doNothing().when(turnService).cancelTurn(any(User.class), any(Match.class));

        turnScheduler.checkTurnTimeout();

        verify(turnService, times(1)).cancelTurn(eq(userInTurn), eq(match));
    }

    @Test
    public void testCheckTurnTimeoutDoesNothingWhenNoMatches() {

        when(matchService.findMatchesInProgress()).thenReturn(List.of());

        turnScheduler.checkTurnTimeout();

        verify(turnService, never()).cancelTurn(any(), any());
    }
}
