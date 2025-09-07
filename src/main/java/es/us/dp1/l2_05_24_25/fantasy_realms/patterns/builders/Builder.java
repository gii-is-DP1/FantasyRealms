package es.us.dp1.l2_05_24_25.fantasy_realms.patterns.builders;


import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;

/**
 * Interfaz genérica para construir objetos en nuestro proyecto siguiendo el patrón Builder
 * 
 * Si bien para nuestro proyecto no sería necesario por el tamaño, es incluido por si en el
 * futuro se quieren realizar adaptaciones, cambios y/o mejoras
 */
@BuilderPattern.Builder(participants = {Match.class, Player.class, Turn.class})
public interface Builder<T> {

    /**
     * Construye y retorna la instancia concreta de T.
     * @return La instancia construida de T
     */
    T build(); // Método genérico para construir objetos
}
