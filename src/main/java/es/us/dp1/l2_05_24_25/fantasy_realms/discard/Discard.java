package es.us.dp1.l2_05_24_25.fantasy_realms.discard;

import java.util.ArrayList;
import java.util.List;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa una zona de descarte.
 */
@Entity
@Getter
@Setter
@Table(name = "discards")
public class Discard extends BaseEntity {

    @NotNull
    @OneToMany
    private List<Card> cards;

    public Discard() {
        this.cards = new ArrayList<>();
    }

    /**
     * Agrega una carta a la pila de descarte.
     * @param card Carta que se añadirá al descarte
     */
    public void addCardDiscard(Card card) {
        this.cards.add(card);
    }

    /**
     * Elimina la carta del descarte (si está presente).
     * @param card Carta a remover.
     * @return true si la carta se eliminó correctamente, false si no estaba en el descarte.
     */
    public boolean removeCard(Card card) {
        return this.cards.remove(card);
    }

}
