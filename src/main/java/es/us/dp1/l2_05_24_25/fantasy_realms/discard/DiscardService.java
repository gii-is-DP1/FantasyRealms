package es.us.dp1.l2_05_24_25.fantasy_realms.discard;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardService;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;

/**
 * Servicio para la gestión de la zona de descarte.
 */
@Service
public class DiscardService {

    private CardService cardService;
    private DiscardRepository discardRepository;

    @Autowired
    public DiscardService(DiscardRepository discardRepository,
            CardService cardService) {
        this.discardRepository = discardRepository;
        this.cardService = cardService;

    }

    /**
     * Busca una zona de descarte por su ID en la base de datos.
     * 
     * @param discardId ID de la zona de descarte
     * @return Discard encontrado
     * @throws ResourceNotFoundException si no se encuentra
     */
    @Transactional(readOnly = true)
    public Discard findDiscardById(Integer discardId) {
        return discardRepository.findById(discardId).orElseThrow(() -> new ResourceNotFoundException("Discard", "id", discardId));
    }

    /**
     * Método interno que agrega una carta a la zona de descarte de la partida dada.
     * 
     * @param match La partida activa en la que se desea descartar (ya buscada en playerService)
     * @param player El jugador que quiere realizar el descarte (ya buscado en playerService)    
     * @param card La carta que se quiere descartar  
     */

    @Transactional
    public void discardCardFromHand(Match match, Player player, Card card) {

        // Obtener la zona de descarte

        Discard discard = match.getDiscard();

        // Eliminamos de la mano del jugador

        player.getPlayerHand().remove(card);

        // Agregar la carta a la zona de descartes

        discard.addCardDiscard(card);
    }

    /**
     * Roba (extrae) una carta específica de la zona de descarte,
     * identificada por discardId y cardId.
     * 
     * @param discardId ID de la zona de descarte
     * @param cardId    ID de la carta a extraer
     * @return la {@link Card} extraída
     * @throws ResourceNotFoundException si el discard o la carta no existen
     */
    @Transactional
    public Card drawCard(Integer discardId, Integer cardId) {

        Discard discard = findDiscardById(discardId);
        Card card = cardService.findCardById(cardId);

        discard.getCards().remove(card);

        return card;

    }
}