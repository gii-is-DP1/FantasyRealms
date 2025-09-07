package es.us.dp1.l2_05_24_25.fantasy_realms.turn;

import java.time.Instant;

import org.jpatterns.gof.BuilderPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa el turno actual asociado a una partida. Contiene
 * un jugador asociado, estado del turno, carta robada en el turno, número
 * de turno y un booleano que indica si se ha descartado en el turno.
 */
@Entity
@Getter
@Setter
@BuilderPattern.Product
public class Turn extends BaseEntity{

    @ManyToOne
    private Player player;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private DrawEnum drawSource = DrawEnum.NONE;

    @ManyToOne(cascade = CascadeType.ALL) // Puede haber más de un turno en el que se robe la misma carta (de la zona de descarte)
    private Card cardDrawn;

    private Integer turnCount = 0;
    
    private boolean discarded = false;

    @Column(name = "turn_start_time")
    private Instant turnStartTime;

    public Turn() {}

    public Turn(Player player, int turnCount) {
        this.player = player;
        this.turnCount = turnCount;
    }

    public boolean canDrawCard() {
        return drawSource == DrawEnum.NONE;
    }
    
    public boolean hasDrawnCard() {
        return drawSource != DrawEnum.NONE;
    }

    public boolean hasDiscarded() {
        return discarded;
    }

    public boolean hasActionsToRevert() {
        return drawSource != DrawEnum.NONE && !discarded;
    }
    
    public void drawFromDeck(Card card) {
        this.cardDrawn = card;
        this.drawSource = DrawEnum.DECK;
    }
    
    public void drawFromDiscard(Card card) {
        this.cardDrawn = card;
        this.drawSource = DrawEnum.DISCARD;
    }
    
}
