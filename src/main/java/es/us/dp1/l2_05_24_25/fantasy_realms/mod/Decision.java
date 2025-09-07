package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import lombok.Getter;
import lombok.Setter;

// No es necesario persistir Decision en BD, por lo que no hay que definirlo como Entity
@Getter
@Setter
public class Decision {

    private Card targetCard; // Carta objetivo para los cambios, si aplica

    private CardType targetCardType; // Tipo de carta, si aplica

    public Decision() {}

    public Decision(Card targetCard, CardType targetCardType) {
        this.targetCard = targetCard;
        this.targetCardType = targetCardType;
    }
    
}
