package es.us.dp1.l2_05_24_25.fantasy_realms.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un mazo (Deck). 
 * Puede contener cartas (cards) y sus versiones iniciales (initialCards).
 */
@Entity
@Getter
@Setter
@Table(name = "decks")
public class Deck extends BaseEntity {

    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> cards;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> initialCards;

    public Deck() {
        
    }

    /**
     * Crea un Deck con las cartas dadas. También inicializa 'initialCards'
     * con una copia independiente de las mismas.
     * @param cards Cartas que se asignarán al mazo.
     */
    public Deck(List<Card> cards) {
        // Asignamos una nueva lista (copia superficial)
        this.cards = new ArrayList<>(cards);
        // Copia independiente para 'initialCards'
        this.initialCards = cards.stream()
                .map(Card::new) // constructor de copia de Card
                .collect(Collectors.toList());
    }

    /**
     * Indica si el mazo está vacío.
     * @return true si no hay cartas, false en caso contrario.
     */
    public boolean isEmpty() {
        return cards == null || cards.isEmpty();
    }

    /**
     * Agrega una carta al mazo (usado al revertir acciones de turnos)
     * @param card Carta que se añadirá al mazo
     */
    public void addCardDeck(Card card) {
        this.cards.add(0,card);
    }

}
