package es.us.dp1.l2_05_24_25.fantasy_realms.mod;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.NecromancerMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyForEachTypeMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyNoType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankTypes;

/*
 * Se pretende probar el funcionamiento del applyMod con cada instancia específica
 */
@ExtendWith(MockitoExtension.class)
public class ModNecromancerTests {

    private List<Card> playerHand;

    @BeforeEach
    void setUp() {

        this.playerHand = new ArrayList<>();

        Card card1 = new Card("Tierra 1", CardType.TIERRA, 10);
        Card card2 = new Card("Tierra 2", CardType.TIERRA, 15);
        Card card3 = new Card("Bestia 1", CardType.BESTIA, 20);
        Card card4 = new Card("Bestia 2", CardType.BESTIA, 5);

        // Añadimos modificadores a las cartas para probar la eliminación
        Mod penaltyMod1 = new BonusPenaltyForEachTypeMod("Penalty 1", -5, null, card1, null, ModType.PENALTY);
        Mod blankNoPenalty1 = new BlankTypes("Blank 1", null, null, card1, null, ModType.BLANK);
        Mod blankPenalty2 = new BlankTypes("Blank 2", -10, null, card2, null, ModType.PENALTY);
        Mod bonusMod = new BonusPenaltyNoType("Bonus", 5, null, card3, null, ModType.BONUS);

        card1.setMods(new ArrayList<>(List.of(penaltyMod1,blankNoPenalty1)));
        card2.setMods(new ArrayList<>(List.of(blankPenalty2)));
        card3.setMods(new ArrayList<>(List.of(bonusMod)));

        playerHand.add(card1);
        playerHand.add(card2);
        playerHand.add(card3);
        playerHand.add(card4);

    }

    // Modificador de tipo NecromancerMod: Al final del juego, puedes tomar un Ejército, Líder, Mago o Bestia del montón de descarte y añadirlo a tu mano como una octava carta.

    @Test
    void shouldAddCardFromDiscardPile() {
    
        // Definimos el mazo de descarte con varias cartas
        List<Card> discardPile = new ArrayList<>();
    
        Card card1 = new Card("Ejército 1", CardType.EJERCITO, 20);
        Card card2 = new Card("Líder 1", CardType.LIDER, 25);
        Card card3 = new Card("Bestia 1", CardType.BESTIA, 15);
        Card card4 = new Card("Mago 1", CardType.MAGO, 30);
        Card card5 = new Card("Artefacto 1", CardType.ARTEFACTO, 10); // Esta carta no debería poder añadirse
    
        // Añadimos las cartas al mazo de descarte
        discardPile.add(card1);
        discardPile.add(card2);
        discardPile.add(card3);
        discardPile.add(card4);
        discardPile.add(card5);
    
        // Definimos la mano del jugador con 7 cartas
        playerHand.clear();
        playerHand.add(new Card("Tierra 1", CardType.TIERRA, 10));
        playerHand.add(new Card("Inundación 1", CardType.INUNDACION, 15));
        playerHand.add(new Card("Arma 1", CardType.ARMA, 20));
        playerHand.add(new Card("Llama 1", CardType.LLAMA, 25));
        playerHand.add(new Card("Tiempo 1", CardType.TIEMPO, 30));
        playerHand.add(new Card("Bestia 2", CardType.BESTIA, 18));
        playerHand.add(new Card("Líder 2", CardType.LIDER, 22));
    
        // Definimos el modificador NecromancerMod para aplicar
        Mod modTest = new NecromancerMod(
                "Al final del juego, puedes tomar un Ejército, Líder, Mago o Bestia del montón de descarte y añadirlo a tu mano como una octava carta.",
                null,
                null,
                null, // No hay carta de origen, ya que afecta al mazo de descarte
                null,
                ModType.STATE);
    
        // Validamos el estado previo
        assertEquals(7, playerHand.size()); // La mano del jugador tiene inicialmente 7 cartas
    
        // Aplicamos el modificador para añadir una carta válida del mazo de descarte
        ((NecromancerMod) modTest).applyMod(playerHand, discardPile, card1); // Añadimos una carta de tipo "Ejército"
    
        // Validamos el comportamiento esperado
        assertEquals(8, playerHand.size()); // La mano del jugador ahora tiene 8 cartas
        assertTrue(playerHand.contains(card1)); // La carta añadida debe estar en la mano del jugador
    
        // Caso: Intentar añadir una carta no permitida del mazo de descarte
        ((NecromancerMod) modTest).applyMod(playerHand, discardPile, card5); // Intentamos añadir una carta de tipo "Artefacto"
    
        // Validamos que la carta no fue añadida
        assertEquals(8, playerHand.size()); // La mano del jugador debe seguir teniendo 8 cartas
        assertFalse(playerHand.contains(card5)); // La carta de tipo "Artefacto" no debe estar en la mano del jugador
    }

}
