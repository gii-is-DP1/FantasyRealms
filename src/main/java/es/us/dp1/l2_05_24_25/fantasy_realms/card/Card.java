package es.us.dp1.l2_05_24_25.fantasy_realms.card;

import java.util.ArrayList;
import java.util.List;

import org.jpatterns.gof.PrototypePattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.NamedEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.patterns.prototypes.Prototype;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa una carta. Cada carta tiene un tipo, imagen,
 * valores base y final, y una lista de modificadores.
 */
@Entity
@Getter
@Setter
@Table(name = "cards")
@PrototypePattern.ConcretePrototype
public class Card extends NamedEntity implements Prototype<Card> {

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @NotNull
    private String image;

    @NotNull
    @Min(value = 0)
    private Integer baseValue;

    @NotNull
    private Integer finalValue;

    @OneToMany(mappedBy = "originCard", cascade = { CascadeType.ALL })
    private List<Mod> mods;

    public Card() {
    }

    // Constructor donde definimos que el finalValue de la carta inicialmente es igual al baseValue

    public Card(String name, CardType cardType, Integer baseValue) {
        this.name = name;
        this.cardType = cardType;
        this.baseValue = baseValue;
        this.finalValue = baseValue;
    }

    public Card(Card other) {
        this.name = other.getName();
        this.cardType = other.getCardType();
        this.baseValue = other.getBaseValue();
        this.finalValue = other.getFinalValue();
        this.image = other.getImage();
        this.mods = new ArrayList<>();
    }

    /**
     * Devuelve un clon (superficial) de este objeto Card.
     * Los mods de la carta se clonan profundamente en otro paso, en deckService.
     */
    @Override
    @PrototypePattern.Operation
    public Card getClone() {
        return new Card(this);
    }

}