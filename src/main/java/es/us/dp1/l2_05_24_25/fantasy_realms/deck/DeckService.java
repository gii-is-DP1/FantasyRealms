package es.us.dp1.l2_05_24_25.fantasy_realms.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.jpatterns.gof.PrototypePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;

/**
 * Servicio para la gestión de Decks (mazos).
 * Provee métodos para obtener, clonar, barajar y manipular el mazo.
 */
@Service
@PrototypePattern // Este servicio es el cliente del patrón prototipo
public class DeckService {

    private DeckRepository deckRepository;

    @Autowired
    public DeckService(DeckRepository deckRepository) {
        this.deckRepository = deckRepository;
    }

    /**
     * Obtiene el mazo universal (ID=1) y crea una copia profunda barajada
     * para usarla en una partida nueva.
     * 
     * @return Deck completamente clonado y barajado.
     */
    @Transactional(readOnly = true)
    public Deck getShuffledDeckForMatch() {
        // Asumimos que el universalDeck está en la BD con ID=1
        Deck universalDeck = deckRepository.findById(1).orElseThrow(() -> new ResourceNotFoundException("Deck", "id", 1));
        
        // Clonar y barajar
        return deepCloneAndShuffle(universalDeck);
    }

    /**
     * Busca un Deck por su ID en la base de datos.
     * 
     * @param deckId ID del Deck
     * @return Deck encontrado
     * @throws ResourceNotFoundException si no existe
     */
    @Transactional(readOnly = true)
    public Deck findDeckById(Integer deckId) {
        return deckRepository.findById(deckId).orElseThrow(() -> new ResourceNotFoundException("Deck", "id", deckId));
    }

    /**
     * Extrae la carta superior del mazo (posición 0) y la quita de la lista.
     * 
     * @param deckId ID del mazo
     * @return Carta extraída
     */
    @Transactional
    public Card drawCard(Integer deckId) {
        Deck deck = findDeckById(deckId);
        return deck.getCards().remove(0);

    }

    /**
     * Comprueba si un mazo está vacío.
     * 
     * @param deckId ID del mazo
     * @return true si no hay cartas, false en caso contrario.
     */
    public boolean isDeckEmpty(Integer deckId) {
        Deck deck = findDeckById(deckId);
        return deck.isEmpty();
    }

    /**
     * Crea una copia profunda de un deck, baraja sus cartas y devuelve
     * un nuevo Deck completamente independiente.
     *
     * @param originalDeck Deck original (el universal)
     * @return Deck clonado y barajado
     */
    @Transactional
    public Deck deepCloneAndShuffle(Deck originalDeck) {
        // Clonar profundamente las cartas
        List<Card> clonedCards = deepCloneCards(originalDeck.getCards());
        // Mezclar las cartas clonadas
        Collections.shuffle(clonedCards);
        // Crear un nuevo Deck con esas cartas (también rellena 'initialCards')
        return new Deck(clonedCards);
    }

    /**
     * Realiza un clonado profundo de las cartas (y sus modificadores), utilizando el patrón prototipo.
     * 
     * @param originalCards Lista de cartas originales
     * @return Lista nueva con cartas y mods completamente independientes
     */
    public List<Card> deepCloneCards(List<Card> originalCards) {
        Map<Card, Card> cardCloneMap = new HashMap<>();
        
        // 1. Clon superficial de cada carta usando getClone()
        for (Card original : originalCards) {
            cardCloneMap.put(original, original.getClone());
        }

        // 2. Clonar y reubicar Mods
        for (Card original : originalCards) {
            Card cloned = cardCloneMap.get(original);

            List<Mod> clonedMods = new ArrayList<>();
            for (Mod originalMod : original.getMods()) {
                Mod clonedMod = originalMod.getClone();
                clonedMod.setOriginCard(cloned);

                // Ajustar el target para las cartas clonadas
                if (originalMod.getTarget() != null && !originalMod.getTarget().isEmpty()) {
                    List<Card> clonedTargets = originalMod.getTarget().stream()
                            .map(cardCloneMap::get)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    clonedMod.setTarget(clonedTargets);
                } else {
                    clonedMod.setTarget(new ArrayList<>());
                }

                clonedMods.add(clonedMod);
            }

            cloned.setMods(clonedMods);
        }

        return new ArrayList<>(cardCloneMap.values());
    }

    /**
     * Extrae las 7 primeras cartas del mazo y las elimina de la lista original.
     * @param deck El mazo del cual se tomarán las cartas
     * @return Lista con 7 cartas
     */
    @Transactional
    public List<Card> drawMultiple(Deck deck) {
        final int cardsPerPlayer = 7;
        // Tomar una sublista con las 7 primeras
        List<Card> drawn = new ArrayList<>(deck.getCards().subList(0, cardsPerPlayer));
        // Removerlas del mazo
        deck.getCards().subList(0, cardsPerPlayer).clear();
        return drawn;
    }

}
