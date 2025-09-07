package es.us.dp1.l2_05_24_25.fantasy_realms.card;

import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio que gestiona las operaciones CRUD de las cartas del juego.
 * 
 * Este servicio está diseñado principalmente para ser usado por el administrador del sistema,
 * permitiendo la creación, lectura, actualización y eliminación de cartas.
 */
@Service
public class CardService {

     private CardRepository cardRepository;

     @Autowired
     public CardService(CardRepository cardRepository) {
         this.cardRepository = cardRepository;
     }

     
     /**
     * Recupera todas las cartas disponibles en el juego.
     * 
     * @return Iterable<Card> Lista de todas las cartas almacenadas en la base de datos.
     */
     @Transactional
     public Iterable<Card> findAll() {
         return cardRepository.findAll();
     }

     /**
     * Recupera todos los tipos de carta definidos en el juego.
     * 
     * @return CardType[] Array de los valores posibles del enumerado {@link CardType}.
     */
     @Transactional
     public CardType[] findCardTypes() {
        return CardType.values();
     }

     /**
     * Recupera todas las cartas que pertenecen a un tipo específico.
     * 
     * @param ct Tipo de carta especificado mediante {@link CardType}.
     * @return List<Card> Lista de cartas que coinciden con el tipo proporcionado.
     */
     @Transactional
     public List<Card> findCardsByType(CardType ct) {
        return cardRepository.findByCardType(ct);
     }

     /**
     * Guarda una nueva carta o actualiza una existente en la base de datos.
     * 
     * @param card Instancia de {@link Card} a guardar o actualizar.
     * @return Card Carta guardada o actualizada.
     */
     @Transactional
     public Card saveCard(Card card) {
        return cardRepository.save(card);
     }

     /**
     * Elimina una carta específica de la base de datos.
     * 
     * @param id ID de la carta que se desea eliminar.
     * @throws ResourceNotFoundException Si no se encuentra una carta con el ID especificado.
     */
     @Transactional
     public void deleteCardById(Integer id) {
         Card toDelete = findCardById(id);
         cardRepository.delete(toDelete);
     }

     /**
     * Recupera una carta específica por su ID.
     * 
     * @param id ID de la carta que se desea buscar.
     * @return Card Carta encontrada.
     * @throws ResourceNotFoundException Si no se encuentra una carta con el ID especificado.
     */
     @Transactional
     public Card findCardById(Integer id) {
        return cardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Card", "id", id));
     }
    
}
