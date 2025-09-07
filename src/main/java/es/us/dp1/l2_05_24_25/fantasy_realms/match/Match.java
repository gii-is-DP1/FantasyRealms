package es.us.dp1.l2_05_24_25.fantasy_realms.match;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.NamedEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa una partida. Cada partida contiene un mazo,
 * una zona de descarte, jugadores, un turno actrual y su estado
 */
@Entity
@Getter
@Setter
@Table(name = "matches")
@BuilderPattern.Product
public class Match extends NamedEntity {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Deck deck;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL) 
    private Discard discard;

    @NotNull
    @OneToMany(mappedBy = "matchPlayed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    @OneToOne(cascade = CascadeType.ALL)
    private Turn currentTurn;

    private boolean inScoringPhase = false;

    private Integer scoringStartTurn;

    public Match() {}

    public Match(String name, Deck deck, Discard discard, List<Player> players) {
        this.name = name;
        this.deck = deck;
        this.discard = discard;
        this.players = players;
        this.players.forEach(player -> player.setMatchPlayed(this));
    }

	public Boolean isInProgress() {
		return this.getStartDate() != null && this.getEndDate() == null;
	}

    public Boolean isFinished() {
        return this.getEndDate() != null;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        player.setMatchPlayed(this); // Establecer la relación inversa automáticamente
    }

    public Boolean reachedMaxPlayers() {
        long nonSpectatorPlayers = this.players.stream()
            .filter(player -> player.getRole() != null && !player.getRole().equals(PlayerType.ESPECTADOR))
            .count();
        return nonSpectatorPlayers >= 6;
    }
    
    public Boolean notReachedMinPlayers() {
        long nonSpectatorPlayers = this.players.stream()
            .filter(player -> !player.getRole().equals(PlayerType.ESPECTADOR))
            .count();
        return nonSpectatorPlayers < 3;
    }    

    /**
     * Devuelve el jugador creador de la partida. Un jugador tiene una partida asociada, que siempre tiene creador.
     * Si el creador es nulo, significa que sí o sí la partida ha finalizado, y el ganador es el propio creador.
     * Es decir, el rol de GANADOR sobreescribe al de CREADOR.
     * 
     * @return Jugador creador de la partida
     */
    public Player getCreador() {
        // Buscar primero rol CREADOR
        Player creator = this.players.stream()
            .filter(p -> p.getRole() == PlayerType.CREADOR)
            .findFirst()
            .orElse(null);
    
        if (creator != null) {
            return creator;
        } else {
            // Si no hay CREADOR, significa que la partida ha finalizado y el creador es GANADOR. Lo retornamos
            return this.players.stream()
                .filter(p -> p.getRole() == PlayerType.GANADOR)
                .findFirst()
                .orElse(null); // no es pero se deja por seguridad
        }
    }

    /**
     * Activa la fase de turnos especiales (donde los jugadores aplican
     * sus decisiones dinámicas)
     */
    public void activateScoringPhase() {
        this.inScoringPhase = true;
        this.scoringStartTurn = this.currentTurn.getTurnCount();
    }

    /**
     * Devuelve el turno máximo que se puede jugar en la partida.
     * 
     * @return Número del turno máximo.
     */
    public int getMaxSpecialTurn() {
        return this.scoringStartTurn + this.players.size();
    }

    /**
     * Devuelve un jugador aleatorio para iniciar la partida.
     * 
     * @return Jugador inicial.
     */
    public Player firstPlayer() {
        List<Player> nonSpectatorPlayers = this.players.stream()
            .filter(player -> !player.getRole().equals(PlayerType.ESPECTADOR))
            .toList();
    
        Random random = new Random();
        int num = random.nextInt(nonSpectatorPlayers.size());
        return nonSpectatorPlayers.get(num);
    }    

}
