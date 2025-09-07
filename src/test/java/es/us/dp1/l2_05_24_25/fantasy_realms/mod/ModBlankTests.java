package es.us.dp1.l2_05_24_25.fantasy_realms.mod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyForEachTypeMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.basic.BonusPenaltyNoType;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankExceptName;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankTypes;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankUnlessArmyOrWeatherPresent;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.blank.BlankUnlessType;

/*
 * Se pretende probar el funcionamiento del applyMod con las instancias de tipo Blank (carpeta blank)
 */
@ExtendWith(MockitoExtension.class)
public class ModBlankTests {

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

    /*
     * Modificador de tipo BlankTypes -> Anula todas las cartas de tipo x
    */

    // Caso 1: Hay cartas de ese tipo en la mano
    
    @Test
    void shouldBlankTypes() {
    
        // Le metemos modificadores a las cartas de tipo tierra
    
        Mod mod1 = new BlankTypes(
                "Mod 1",
                1,
                null,
                playerHand.get(0),
                null,
                ModType.BONUS);
    
        Mod mod2 = new BlankTypes(
                "Mod 2",
                1,
                null,
                playerHand.get(1),
                null,
                ModType.BONUS);
    
        // Añadimos modificadores acumulativamente a las cartas
        
        playerHand.get(0).setMods(new ArrayList<>(List.of(mod1)));
        playerHand.get(1).setMods(new ArrayList<>(List.of(mod2)));
    
        // Carta a añadir al target para probar que funciona correctamente
    
        Card card6 = new Card("Tierra 3", CardType.TIERRA, 20);
        card6.setMods(new ArrayList<>(List.of(
                new BlankTypes("Mod 3", 1, null, card6, null, ModType.BONUS)
        )));
    
        Mod modTest = new BlankTypes(
                "Anula todas las cartas de Tierra",
                null,
                null,
                playerHand.get(3),
                List.of(playerHand.get(0), playerHand.get(1), card6), // Aquí tendría todas las cartas de tipo tierra, en este caso suponemos que solo hay 3
                ModType.BLANK);
    
        // Comprobamos funcionamiento previo
    
        assertEquals(mod1, playerHand.get(0).getMods().get(0));
        assertEquals(mod2, playerHand.get(1).getMods().get(0));
        assertEquals(10, playerHand.get(0).getBaseValue());
        assertEquals(15, playerHand.get(1).getBaseValue());
        assertEquals(10, playerHand.get(0).getFinalValue());
        assertEquals(15, playerHand.get(1).getFinalValue());
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento
        assertTrue(playerHand.get(0).getMods().isEmpty()); // Deben eliminarse los modificadores de la carta 0
        assertTrue(playerHand.get(1).getMods().isEmpty()); // Deben eliminarse los modificadores de la carta 1
        assertEquals(0, playerHand.get(0).getBaseValue());
        assertEquals(0, playerHand.get(1).getBaseValue());
        assertEquals(0, playerHand.get(0).getFinalValue());
        assertEquals(0, playerHand.get(1).getFinalValue());
    }

    // Caso 2: No hay cartas de ese tipo en la mano

    @Test
    void shouldNotBlankTypes() {

        // Le metemos modificadores a las cartas de tipo tierra

        Mod mod1 = new BlankTypes(
                "Mod 1",
                1,
                null,
                playerHand.get(0),
                null,
                ModType.BONUS);

        Mod mod2 = new BlankTypes(
                "Mod 2",
                1,
                null,
                playerHand.get(1),
                null,
                ModType.BONUS);

        // Añadimos modificadores acumulativamente a las cartas

        playerHand.get(0).setMods(new ArrayList<>(List.of(mod1)));
        playerHand.get(1).setMods(new ArrayList<>(List.of(mod2)));

        // Carta a añadir al target para probar que funciona correctamente

        Card card6 = new Card("Llama 1", CardType.LLAMA, 20);

        card6.setMods(new ArrayList<>(List.of(
                new BlankTypes("Mod 3", 1, null, card6, null, ModType.BONUS))));

        Mod modTest = new BlankTypes(
                "Anula todas las cartas de Tierra",
                null,
                null,
                playerHand.get(3),
                List.of(card6), // Suponemos que solo hay una carta de tipo llama
                ModType.BLANK);

        // Comprobamos funcionamiento previo

        assertEquals(mod1, playerHand.get(0).getMods().get(0));
        assertEquals(mod2, playerHand.get(1).getMods().get(0));
        assertEquals(10, playerHand.get(0).getBaseValue());
        assertEquals(15, playerHand.get(1).getBaseValue());
        assertEquals(10, playerHand.get(0).getFinalValue());
        assertEquals(15, playerHand.get(1).getFinalValue());

        // Llamamos al método a probar
        modTest.applyMod(playerHand);

        // Validamos el comportamiento
        assertFalse(playerHand.get(0).getMods().isEmpty()); // No deben eliminarse los modificadores de la carta 0
        assertFalse(playerHand.get(1).getMods().isEmpty()); // No deben eliminarse los modificadores de la carta 1
        assertEquals(10, playerHand.get(0).getBaseValue());
        assertEquals(15, playerHand.get(1).getBaseValue());
        assertEquals(10, playerHand.get(0).getFinalValue());
        assertEquals(15, playerHand.get(1).getFinalValue());
    }

    /*
     * Modificador tipo BlankUnlessType -> Anula la carta al menos que se cumpla una condición (por ejemplo que esté acompañada de un tipo específico en la mano)
    */

    // Caso 1: Existe dicho tipo en la mano

    @Test
    void shouldNotBeBlanked() {
    
        // Añadimos un modificador de prueba a la carta origen (playerHand.get(3))

        Mod mod1 = new BlankTypes(
                "Mod 1",
                1,
                null,
                playerHand.get(3), // Carta de origen
                null,
                ModType.BONUS);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(mod1)));
    
        // Carta adicional de tipo "Tierra" para probar que el target funciona correctamente

        Card card6 = new Card("Tierra 3", CardType.TIERRA, 20);
    
        // Modificador de tipo BlankUnlessType, que anula la carta a menos que esté con una carta de "Tierra"

        Mod modTest = new BlankUnlessType(
                "Anulada a menos que esté con al menos una carta de Tierra.",
                null,
                null,
                playerHand.get(3),
                List.of(playerHand.get(0), playerHand.get(1), card6), // Target: cartas de tipo tierra, suponemos que hay 3 en todo el juego
                ModType.BLANK);
    
        // Añadimos el modificador a la carta origen
        playerHand.get(3).getMods().add(modTest);
    
        // Validamos que la carta inicialmente tenga modificadores y valores base y final
        assertFalse(playerHand.get(3).getMods().isEmpty()); // La carta debe tener modificadores
        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
        assertFalse(playerHand.get(3).getMods().isEmpty()); // Como hay ese tipo en la mano, no se anulan los modificadores
        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    }

    // Caso 2: No existe dicho tipo en la mano

    @Test
    void shouldBeBlanked() {
    
        // Añadimos un modificador de prueba a la carta origen (playerHand.get(3))

        Mod mod1 = new BlankTypes(
                "Mod 1",
                1,
                null,
                playerHand.get(3), // Carta de origen
                null,
                ModType.BONUS);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(mod1)));
    
        // Carta adicional de tipo "Tierra" para probar que el target funciona correctamente

        Card card6 = new Card("Llama 1", CardType.LLAMA, 20);
    
        // Modificador de tipo BlankUnlessType, que anula la carta a menos que esté con una carta de "Tierra"

        Mod modTest = new BlankUnlessType(
                "Anulada a menos que esté con al menos una carta de tipo Llama.",
                null,
                null,
                playerHand.get(3),
                List.of(card6), // Target: suponemos que solo hay una carta de tipo llama en todo el juego.
                ModType.BLANK);
    
        // Añadimos el modificador a la carta origen
        playerHand.get(3).getMods().add(modTest);
    
        // Validamos que la carta inicialmente tenga modificadores y valores base y final
        assertFalse(playerHand.get(3).getMods().isEmpty()); // La carta debe tener modificadores
        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
        assertTrue(playerHand.get(3).getMods().isEmpty()); // Como no hay ese modificador, la carta se anula
        assertEquals(0, playerHand.get(3).getBaseValue());
        assertEquals(0, playerHand.get(3).getFinalValue());
    }

    /*
     * Modificador de tipo BlankExceptName -> Anula todas las cartas excepto x cartas por nombre/tipos
    */

    // Caso 1: Anula todas las cartas de Llama excepto Rayo.

    @Test
    void shouldBlankFlameExceptLightning() {

        // Suponemos que hay 3 cartas de tipo Llama, 1 es la de rayo

        Card card5 = new Card("Llama 1", CardType.LLAMA, 20);
        Card card6 = new Card("Llama 2", CardType.LLAMA, 20);
        Card card7 = new Card("Rayo", CardType.LLAMA, 20);

        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);

        // Definimos un mod de prueba para cada una
        
        Mod mod1 = new BlankTypes(
                "Mod 1",
                1,
                null,
                playerHand.get(4), // Carta de origen
                null,
                ModType.BONUS);

        Mod mod2 = new BlankTypes(
                "Mod 2",
                1,
                null,
                playerHand.get(5), // Carta de origen
                null,
                ModType.BONUS);

        Mod mod3 = new BlankTypes(
                "Mod 3",
                1,
                null,
                playerHand.get(6), // Carta de origen
                null,
                ModType.BONUS);

        playerHand.get(4).setMods(new ArrayList<>(List.of(mod1)));
        playerHand.get(5).setMods(new ArrayList<>(List.of(mod2)));
        playerHand.get(6).setMods(new ArrayList<>(List.of(mod3)));

        // Definimos el mod a testear
        
        Mod modTest = new BlankExceptName(
                "Anula todas las cartas de Llama excepto Rayo.",
                null,
                null,
                playerHand.get(3),
                List.of(card5,card6), // Target: suponemos que solo hay una carta de tipo llama en todo el juego.
                ModType.BLANK);

        // Validamos comportamiento previo

        // Validamos que la carta inicialmente tenga modificadores y valores base y final
        assertFalse(playerHand.get(4).getMods().isEmpty());
        assertFalse(playerHand.get(5).getMods().isEmpty());
        assertFalse(playerHand.get(6).getMods().isEmpty());
        
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
        assertTrue(playerHand.get(4).getMods().isEmpty());
        assertTrue(playerHand.get(5).getMods().isEmpty());
        assertFalse(playerHand.get(6).getMods().isEmpty());
        assertEquals(0, playerHand.get(4).getBaseValue());
        assertEquals(0, playerHand.get(4).getFinalValue());
        assertEquals(0, playerHand.get(5).getBaseValue());
        assertEquals(0, playerHand.get(5).getFinalValue());
        assertEquals(20, playerHand.get(6).getBaseValue());
        assertEquals(20, playerHand.get(6).getFinalValue());
    }

    // Caso 2: Anula todas las cartas excepto Llamas, Clima, Magos, Armas, Artefactos, Gran Inundación, Isla, Montaña, Unicornio y Dragón.
    
    @Test
    void shouldBlankTypesAndNamesFlood() {
    
        // Suponemos que hay varias cartas, incluidas algunas que serán anuladas y otras que se exceptúan
    
        Card card5 = new Card("Dragón", CardType.BESTIA, 30);  // Esta carta no debería ser anulada
        Card card6 = new Card("Llama 1", CardType.LLAMA, 15);   // Esta carta no debería ser anulada
        Card card7 = new Card("Isla", CardType.INUNDACION, 20); // Esta carta no debería ser anulada
    
        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);
    
        // Definimos un mod de prueba para cada carta, para comprobar que se eliminan correctamente -> Vamos a probarlo solo con la carta de la mano de tipo tierra y la de tipo bestia
    
        Mod mod1 = new BlankTypes(
                "Mod 1",
                1,
                null,
                playerHand.get(0), // Carta de origen
                null,
                ModType.BONUS);
    
        Mod mod2 = new BlankTypes(
                "Mod 2",
                1,
                null,
                playerHand.get(2), // Carta de origen
                null,
                ModType.BONUS);

        Mod mod3 = new BlankTypes(
                "Mod 3",
                1,
                null,
                playerHand.get(4), // Carta de origen
                null,
                ModType.BONUS);

        Mod mod4 = new BlankTypes(
                "Mod 4",
                1,
                null,
                playerHand.get(5), // Carta de origen
                null,
                ModType.BONUS);

        Mod mod5 = new BlankTypes(
                "Mod 5",
                1,
                null,
                playerHand.get(6), // Carta de origen
                null,
                ModType.BONUS);

        Mod mod6 = new BlankTypes(
                "Mod 6",
                1,
                null,
                playerHand.get(6), // Carta de origen
                null,
                ModType.BONUS);
    
        playerHand.get(0).setMods(new ArrayList<>(List.of(mod1)));
        playerHand.get(1).setMods(new ArrayList<>(List.of(mod2)));
        playerHand.get(2).setMods(new ArrayList<>(List.of(mod3)));
        playerHand.get(4).setMods(new ArrayList<>(List.of(mod4)));
        playerHand.get(5).setMods(new ArrayList<>(List.of(mod5)));
        playerHand.get(6).setMods(new ArrayList<>(List.of(mod6)));
    
        // Definimos el mod a testear
    
        Mod modTest = new BlankExceptName(
                "Anula todas las cartas excepto Llamas, Clima, Magos, Armas, Artefactos, Gran Inundación, Isla, Montaña, Unicornio y Dragón.",
                null,
                null,
                playerHand.get(3),
                List.of(playerHand.get(0), playerHand.get(1), playerHand.get(2), playerHand.get(3)),
                ModType.BLANK);

        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Validamos comportamiento previo
    
        // Validamos que las cartas inicialmente tengan modificadores y valores base y final

        assertFalse(playerHand.get(0).getMods().isEmpty());
        assertFalse(playerHand.get(1).getMods().isEmpty());
        assertFalse(playerHand.get(2).getMods().isEmpty());
        assertFalse(playerHand.get(4).getMods().isEmpty());
        assertFalse(playerHand.get(5).getMods().isEmpty());
        assertFalse(playerHand.get(6).getMods().isEmpty());
    
        // Llamamos al método a probar
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
        assertEquals(0, playerHand.get(0).getBaseValue()); // Carta 1 debe ser anulada
        assertEquals(0, playerHand.get(0).getFinalValue());
        assertTrue(playerHand.get(0).getMods().isEmpty());

        assertEquals(0, playerHand.get(1).getBaseValue()); // Carta 2 debe ser anulada
        assertEquals(0, playerHand.get(1).getFinalValue());
        assertTrue(playerHand.get(1).getMods().isEmpty());
    
        assertEquals(0, playerHand.get(2).getBaseValue()); // Carta 3 debe ser anulada
        assertEquals(0, playerHand.get(2).getFinalValue());
        assertTrue(playerHand.get(2).getMods().isEmpty());

        assertEquals(0, playerHand.get(3).getBaseValue()); // Carta 4 debe ser anulada
        assertEquals(0, playerHand.get(3).getFinalValue());
        assertTrue(playerHand.get(3).getMods().isEmpty());
    
        assertEquals(30, playerHand.get(4).getBaseValue()); // Carta 5 no debe ser anulada
        assertEquals(30, playerHand.get(4).getFinalValue());
        assertFalse(playerHand.get(4).getMods().isEmpty());
    
        assertEquals(15, playerHand.get(5).getBaseValue()); // Carta 6 no debe ser anulada
        assertEquals(15, playerHand.get(5).getFinalValue());
        assertFalse(playerHand.get(5).getMods().isEmpty());
    
        assertEquals(20, playerHand.get(6).getBaseValue()); // Carta 7 no debe ser anulada
        assertEquals(20, playerHand.get(6).getFinalValue());
        assertFalse(playerHand.get(6).getMods().isEmpty());
    }

    /*
     *  Modificador de tipo BlankUnlessArmyOrWeatherPresent -> Anulada a menos que esté con al menos un Ejército y Anulada si la mano contiene cualquier carta de Tiempo.
    */
    
    // Caso 1: Existe Ejército y Tiempo -> Se debe anular ya que hay "Tiempo"

    @Test
    void shouldBlankWhenArmyAndWeatherPresent() {
    
        // Ejércitos presentes
        Card card5 = new Card("Ejército 1", CardType.EJERCITO, 10);
        Card card6 = new Card("Ejército 2", CardType.EJERCITO, 15);
        
        // Carta de tipo "Tiempo"
        Card card7 = new Card("Niebla", CardType.TIEMPO, 20);
    
        // Agregamos las cartas a la mano del jugador
        playerHand.add(card5);
        playerHand.add(card6);
        playerHand.add(card7);
    
        // Definimos un modificador para la carta de prueba
        Mod modTest = new BlankUnlessArmyOrWeatherPresent(
                "Anulada a menos que esté con al menos un Ejército o si la mano contiene cualquier carta de Tiempo",
                null,
                null,
                playerHand.get(3),
                List.of(card5, card6, card7),
                ModType.BLANK);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Validamos el comportamiento previo
        assertFalse(playerHand.get(3).getMods().isEmpty()); // La carta tiene modificadores inicialmente
        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    
        // Aplicamos el modificador
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
        assertTrue(playerHand.get(3).getMods().isEmpty());
        assertEquals(0, playerHand.get(3).getBaseValue());
        assertEquals(0, playerHand.get(3).getFinalValue());
    }
    
    // Caso 2: No hay Ejército, pero sí carta de tipo "Tiempo" -> Se debe anular

    @Test
    void shouldBlankWhenNoArmyAndWeatherPresent() {
    
        // Carta de tipo "Tiempo"
        Card card7 = new Card("Niebla", CardType.TIEMPO, 20);
        
        playerHand.add(card7);
    
        // Definimos un modificador para la carta de prueba
        Mod modTest = new BlankUnlessArmyOrWeatherPresent(
                "Anulada a menos que esté con al menos un Ejército o si la mano contiene cualquier carta de Tiempo",
                null,
                null,
                playerHand.get(3),
                List.of(card7),
                ModType.BLANK);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Validamos el comportamiento previo
        assertFalse(playerHand.get(3).getMods().isEmpty());
        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    
        // Aplicamos el modificador
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
        assertTrue(playerHand.get(3).getMods().isEmpty());
        assertEquals(0, playerHand.get(3).getBaseValue());
        assertEquals(0, playerHand.get(3).getFinalValue());
    }
    
    
    // Caso 3: No hay carta de tipo "Tiempo" en la mano y tampoco hay Ejército -> Se debe anular
    
    @Test
    void shouldBlankWhenNoArmyAndNoWeatherPresent() {
    
        // Definimos un modificador para la carta de prueba
        Mod modTest = new BlankUnlessArmyOrWeatherPresent(
                "Anulada a menos que esté con al menos un Ejército o si la mano contiene cualquier carta de Tiempo",
                null,
                null,
                playerHand.get(3),
                List.of(),
                ModType.BLANK);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Validamos el comportamiento previo
        assertFalse(playerHand.get(3).getMods().isEmpty());
        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    
        // Aplicamos el modificador
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
        assertTrue(playerHand.get(3).getMods().isEmpty());
        assertEquals(0, playerHand.get(3).getBaseValue());
        assertEquals(0, playerHand.get(3).getFinalValue());
    }
    
    // Caso 4: Hay Ejército y no hay "Tiempo" -> No se debe anular

    @Test
    void shouldNotBlankWhenArmyPresentAndNoWeather() {
    
        // Ejércitos presentes
        Card card5 = new Card("Ejército 1", CardType.EJERCITO, 10);
        playerHand.add(card5);
    
        // Definimos un modificador para la carta de prueba
        Mod modTest = new BlankUnlessArmyOrWeatherPresent(
                "Anulada a menos que esté con al menos un Ejército o si la mano contiene cualquier carta de Tiempo",
                null,
                null,
                playerHand.get(3),
                List.of(card5),
                ModType.BLANK);
    
        playerHand.get(3).setMods(new ArrayList<>(List.of(modTest)));
    
        // Validamos el comportamiento previo
        assertFalse(playerHand.get(3).getMods().isEmpty());
        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    
        // Aplicamos el modificador
        modTest.applyMod(playerHand);
    
        // Validamos el comportamiento esperado
        assertFalse(playerHand.get(3).getMods().isEmpty());
        assertEquals(5, playerHand.get(3).getBaseValue());
        assertEquals(5, playerHand.get(3).getFinalValue());
    }
    
}
