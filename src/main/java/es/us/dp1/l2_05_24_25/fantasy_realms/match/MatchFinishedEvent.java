package es.us.dp1.l2_05_24_25.fantasy_realms.match;

import org.springframework.context.ApplicationEvent;

public class MatchFinishedEvent extends ApplicationEvent {

    private final Match match;

    public MatchFinishedEvent(Object source, Match match) {
        super(source);
        this.match = match;
    }

    public Match getMatch() {
        return match;
    }
}

