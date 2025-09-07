package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import java.util.Collections;
import java.util.List;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.NamedEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDTO extends NamedEntity{

    private String name;

    private String image;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    private Integer baseValue;

    private Integer finalValue;

    private List<ModDTO> modsDTO;

    public CardDTO(Card other) {
        this.id = other.getId();
        this.name = other.getName();
        this.image = other.getImage();
        this.cardType = other.getCardType();
        this.baseValue = other.getBaseValue();
        this.finalValue = other.getFinalValue();
        this.modsDTO = (other.getMods() != null) ? other.getMods().stream().map(ModDTO::new).toList() : Collections.emptyList();
    }
    
}
