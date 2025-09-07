package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import java.util.List;

import org.jpatterns.gof.PrototypePattern;
import org.jpatterns.gof.TemplateMethodPattern;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.patterns.prototypes.Prototype;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un modificador. Contiene una descripción, valores primario y secundario,
 * una carta origen a la que pertenece, una lista de cartas a las que afecta, y es de un tipo.
 */
@Entity
@Getter
@Setter
@Table(name = "mods")
@Inheritance(strategy =  InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "mod_type", discriminatorType = DiscriminatorType.STRING)
@PrototypePattern.ConcretePrototype
@TemplateMethodPattern.AbstractClass(comment = "Define el esqueleto del algoritmo para aplicar un modificador. Las subclases implementan 'applyMod' y 'clone' con lógica específica.")
public abstract class Mod extends BaseEntity implements Prototype<Mod>{

    @NotNull
    private String description;

    private Integer primaryValue;

    private Integer secondaryValue;

    @ManyToOne
    private Card originCard;

    @ManyToMany
    private List<Card> target;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "mod_main_type")
    private ModType modType;

    public Mod() {}

    public Mod(String description, Integer primaryValue, Integer secondaryValue, Card originCard, List<Card> target, ModType modType) {
        this.description = description;
        this.primaryValue = primaryValue;
        this.secondaryValue = secondaryValue;
        this.originCard = originCard;
        this.target = target;
        this.modType = modType;
    }

    public Mod(Mod other) {
        this.description = other.getDescription();
        this.primaryValue = other.getPrimaryValue();
        this.secondaryValue = other.getSecondaryValue();
        this.modType = other.getModType();
    }

    /**
     * Aplica el efecto del modificador a la mano del jugador.
     * 
     * Este método debe ser implementado por las subclases de {@link Mod} para definir 
     * cómo se aplica el efecto cada modificador a las cartas.
     *
     * @param playerHand Lista de cartas en la mano del jugador sobre la cual se aplicará el modificador.
     */
    public abstract void applyMod(List<Card> playerHand);

    @Override
    @PrototypePattern.Operation
    public Mod getClone() {
        return this.clone();
    }

    /**
     * Crea una copia profunda del modificador.
     * 
     * Este método debe ser implementado por las subclases de {@link Mod} para 
     * garantizar que el modificador y todos sus datos asociados sean clonados 
     * correctamente, evitando referencias compartidas.
     * 
     * Es utilizado para la clonación del mazo universal, de forma que se pueda obtener
     * un mazo independiente para cada partida.
     * 
     * @return Una nueva instancia de {@link Mod} que es una copia exacta del objeto original.
     */
    public abstract Mod clone();

}
