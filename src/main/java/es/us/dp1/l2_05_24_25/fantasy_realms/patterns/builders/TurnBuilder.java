package es.us.dp1.l2_05_24_25.fantasy_realms.patterns.builders;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * "TurnBuilder" - construye objetos Turn.
 */
@Getter
@Setter
@Accessors(chain =  true) // Para que los setter retornen TurnBuilder y no void, y funcione
@BuilderPattern.ConcreteBuilder
public class TurnBuilder implements Builder<Turn> {

    private Player player;

    private Integer turnCount = 0;

    // Construcción de turno empleada, sin usar todos los atributos

    @Override
    public Turn build() {
        return new Turn(player, turnCount);
    }

}
