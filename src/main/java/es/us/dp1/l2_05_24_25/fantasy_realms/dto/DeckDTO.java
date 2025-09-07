package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import java.util.List;
import java.util.stream.Collectors;

import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeckDTO {

        private List<CardDTO> cards;
        private List<CardDTO> initialCards;

        public DeckDTO() {
        }

        public DeckDTO(Deck other) {

                this.cards = other.getCards().stream()
                                .map(CardDTO::new)
                                .collect(Collectors.toList());

                this.initialCards = other.getInitialCards().stream()
                                .map(CardDTO::new)
                                .collect(Collectors.toList());

        }

}
