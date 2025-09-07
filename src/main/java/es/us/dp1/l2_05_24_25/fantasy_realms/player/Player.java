package es.us.dp1.l2_05_24_25.fantasy_realms.player;

import java.util.ArrayList;
import java.util.List;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un jugador en el juego. Un jugador tiene una puntuación, un rol,
 * una mano de cartas, una partida jugada y un usuario asociado.
 * 
 * Un jugador representa un usuario que ha jugado una partida. Así, esta entidad está enfocada
 * en el juego de las partidas.
 */
@Getter
@Setter
@Table(name = "players")
@Entity
@BuilderPattern.Product
public class Player extends BaseEntity {

    private Integer score;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PlayerType role;
    
    @OneToMany
    @NotNull
    private List<Card> playerHand;

    @NotNull
    @ManyToOne
    private Match matchPlayed;

    @NotNull
    @ManyToOne
    private User user;

    public Player() {
    }

    public Player(User user, PlayerType role) {
        this.user = user;
        this.role = role;
        this.playerHand = new ArrayList<>();
    }


    public boolean isInLobbyOrPlaying() {
        return this.matchPlayed != null && !this.matchPlayed.isFinished();
    }

    public boolean isCardInHand(Card card) {
        return this.getPlayerHand().contains(card);
    }

}
