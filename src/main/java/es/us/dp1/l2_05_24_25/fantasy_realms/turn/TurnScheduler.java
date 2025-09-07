package es.us.dp1.l2_05_24_25.fantasy_realms.turn;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import jakarta.transaction.Transactional;

@Service
public class TurnScheduler {

    private final MatchService matchService;
    private final TurnService turnService;

    public TurnScheduler(MatchService matchService, TurnService turnService) {
        this.matchService = matchService;
        this.turnService = turnService;
    }

    @Scheduled(fixedRate = 2000)
    @Transactional
    public void checkTurnTimeout() {

        List<Match> ongoingMatches = matchService.findMatchesInProgress(); 

        for (Match match : ongoingMatches) {
            Turn currentTurn = match.getCurrentTurn();
            if (currentTurn == null) {
                continue;
            }

            Instant turnStart = currentTurn.getTurnStartTime();
            if (turnStart == null) {
                continue;
            }

            long elapsedSeconds = Duration.between(turnStart, Instant.now()).toSeconds();

            if (elapsedSeconds > 60) {

                forceCancelTurn(match);
            }
        }
    }

    /**
     * Cancela el turno en nombre del jugador que lo tenía
     */
    private void forceCancelTurn(Match match) {
        Turn currentTurn = match.getCurrentTurn();
        if (currentTurn == null) {
            return;
        }

        if (match.isInScoringPhase()) {
            return;
        }

        Player turnPlayer = currentTurn.getPlayer();
        if (turnPlayer == null) {
            return;
        }

        User userInTurn = turnPlayer.getUser();

        try {
            turnService.cancelTurn(userInTurn, match);

        } catch (Exception e) {
        }
    }

}
