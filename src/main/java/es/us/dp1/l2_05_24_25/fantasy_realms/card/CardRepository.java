package es.us.dp1.l2_05_24_25.fantasy_realms.card;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends CrudRepository<Card,Integer> {
    
    // Búsqueda de cartas por tipo

    @Query("Select c FROM Card c WHERE c.cardType = :cardType")
    List<Card> findByCardType(@Param("cardType") CardType ct);


}
