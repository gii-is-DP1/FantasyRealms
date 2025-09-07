package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import java.util.Objects;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DecisionDTO {

    private Integer modId;

    private Integer cardId;

    private CardType cardType;

    @Override
    public String toString() {
        return "DecisionDTO{" +
                "modId=" + modId +
                ", cardId=" + cardId +
                ", cardType=" + cardType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DecisionDTO that = (DecisionDTO) o;
        return Objects.equals(modId, that.modId) &&
               Objects.equals(cardId, that.cardId) &&
               cardType == that.cardType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(modId, cardId, cardType);
    }
}