package es.us.dp1.l2_05_24_25.fantasy_realms.patterns.builders;


import java.util.List;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * "MatchBuilder" - construye objetos Match.
 */
@Getter
@Setter
@Accessors(chain = true) // Para que los setter retornen MatchBuilder y no void, y funcione
@BuilderPattern.ConcreteBuilder
public class MatchBuilder implements Builder<Match> {
    
    private String name;
    private Deck deck;
    private Discard discard;
    private List<Player> players;

    @Override
    public Match build() {
        return new Match(name, deck, discard, players);
    }

}
