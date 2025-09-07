package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import java.util.List;

import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscardDTO extends BaseEntity{

    private List<CardDTO> cards;

    public DiscardDTO() {}

    public DiscardDTO(Discard other) {
        this.id = other.getId();
        this.cards = other.getCards().stream().map(CardDTO::new).toList();
    }
    
}
