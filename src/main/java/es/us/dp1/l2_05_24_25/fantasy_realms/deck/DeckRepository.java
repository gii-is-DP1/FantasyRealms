package es.us.dp1.l2_05_24_25.fantasy_realms.deck;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface DeckRepository extends CrudRepository<Deck, Integer>{
    
}
