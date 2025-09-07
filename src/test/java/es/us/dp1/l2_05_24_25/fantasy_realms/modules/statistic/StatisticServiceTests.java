package es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardType;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.InvalidStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.Mod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.NecromancerMod;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.bonus.BonusBasicWizzardInHand;
import es.us.dp1.l2_05_24_25.fantasy_realms.mod.modType.changeState.ChangeType;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceTests {

    @Spy
    protected StatisticService statisticService;

    @Mock
    private PlayerService playerService;

    @Mock
    private MatchService matchService;

    private User currentUser1;
    private User currentUser2;
    private User currentUser3;
    private List<Player> mockPlayers;
    private List<Player> mockPlayers3;
    private List<Player> mockPlayers4;
    private List<Match> mockMatches;

    @BeforeEach
    public void setup() {
        
        statisticService = new StatisticService(playerService, matchService);

        currentUser1 = new User();
        currentUser1.setId(1);
        currentUser1.setUsername("TestUser");

        currentUser2 = new User();
        currentUser2.setId(2);
        currentUser2.setUsername("TestUser");

        currentUser3 = new User();
        currentUser3.setId(3);
        currentUser3.setUsername("TestUser");

        Player player1 = new Player();
        player1.setScore(50);

        Player player2 = new Player();
        player2.setScore(30);

        Player player3 = new Player();
        player3.setScore(70);

        mockPlayers = Arrays.asList(player1, player2, player3);

        Match match1 = new Match();
        match1.setStartDate(LocalDateTime.of(2025, 1, 10, 10, 0));
        match1.setEndDate(LocalDateTime.of(2025, 1, 10, 10, 20));
        match1.setPlayers(mockPlayers);

        Match match2 = new Match();
        match2.setStartDate(LocalDateTime.of(2025, 1, 10, 11, 0));
        match2.setEndDate(LocalDateTime.of(2025, 1, 10, 11, 15));
        match2.setPlayers(mockPlayers);

        Match match3 = new Match();
        match3.setStartDate(LocalDateTime.of(2025, 1, 10, 11, 0));
        match3.setEndDate(LocalDateTime.of(2025, 1, 10, 11, 15));
        Player player4 = new Player(currentUser1, PlayerType.GANADOR);
        Player player5 = new Player(currentUser2, PlayerType.PARTICIPANTE);
        Player player6 = new Player(currentUser3, PlayerType.PARTICIPANTE);
        player4.setMatchPlayed(match3);
        player5.setMatchPlayed(match3);
        player6.setMatchPlayed(match3);
        mockPlayers3 = Arrays.asList(player4, player5, player6);
        match3.setPlayers(mockPlayers3);
        mockMatches = Arrays.asList(match1, match2, match3);

        Match match4 = new Match();
        match4.setStartDate(LocalDateTime.of(2025, 1, 10, 12, 0));
        match4.setEndDate(LocalDateTime.of(2025, 1, 10, 12, 15));
        Player player7 = new Player(currentUser1, PlayerType.GANADOR);
        Player player8 = new Player(currentUser2, PlayerType.PARTICIPANTE);
        Player player9 = new Player(currentUser3, PlayerType.PARTICIPANTE);
        player7.setMatchPlayed(match4);
        player8.setMatchPlayed(match4);
        player9.setMatchPlayed(match4);
        mockPlayers4 = Arrays.asList(player7, player8, player9);
        match4.setPlayers(mockPlayers4);
        mockMatches = Arrays.asList(match1, match2, match3, match4);
    }

    @Test
    public void testGetTotalMatches() {
        when(playerService.countPlayersByUser(currentUser1)).thenReturn(5);

        Integer totalMatches = statisticService.getTotalMatches(currentUser1);

        assertNotNull(totalMatches);
        assertEquals(5, totalMatches);
    }

    @Test
    public void testGetAverageMatchDuration() {

        List<Match> matches = List.of(mockMatches.get(0), mockMatches.get(1));

        when(matchService.findMatchesByUser(currentUser1)).thenReturn(matches);

        Double averageDuration = statisticService.getAverageMatchDuration(currentUser1);

        assertNotNull(averageDuration);
        assertEquals(17.5, averageDuration);
    }

    @Test
    public void testGetAveragePlayersPerMatch() {
        when(matchService.findMatchesByUser(currentUser1)).thenReturn(mockMatches);

        Double averagePlayers = statisticService.getAveragePlayersPerMatch(currentUser1);

        assertNotNull(averagePlayers);
        assertEquals(3.0, averagePlayers);
    }

    @Test
    public void testGetAveragePoints() {
        when(playerService.getPlayersByUser(currentUser1)).thenReturn(mockPlayers);

        Double averagePoints = statisticService.getAveragePoints(currentUser1);

        assertNotNull(averagePoints);
        assertEquals(50.0, averagePoints);
    }

    @Test
    public void testGetMaxPoints() {
        when(playerService.getPlayersByUser(currentUser1)).thenReturn(mockPlayers);

        Integer maxPoints = statisticService.getMaxPoints(currentUser1);

        assertNotNull(maxPoints);
        assertEquals(70, maxPoints);
    }

    @Test
    public void testGetMinPoints() {
        when(playerService.getPlayersByUser(currentUser1)).thenReturn(mockPlayers);

        Integer minPoints = statisticService.getMinPoints(currentUser1);

        assertNotNull(minPoints);
        assertEquals(30, minPoints);
    }

    @Test
    public void testGetWinPercentage() {
        // Crear un mock de un usuario
        User currentUser = new User();
        currentUser.setId(1);
        currentUser.setUsername("test_user");

        // Crear jugadores de ejemplo para simular el porcentaje de victorias
        Player player1 = new Player();
        player1.setRole(PlayerType.GANADOR); // Aquí asignamos el tipo GANADOR
        Player player2 = new Player();
        player2.setRole(PlayerType.PARTICIPANTE); // O usa cualquier otro tipo adecuado
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Simula la respuesta de `playerService.getPlayersByUser(currentUser)`
        when(playerService.getPlayersByUser(currentUser)).thenReturn(players);

        // Llamar al método que estamos probando
        Double winPercentage = statisticService.getWinPercentage(currentUser);

        // Verificar el resultado esperado (50% de victorias)
        assertEquals(50.0, winPercentage, "El porcentaje de victorias debería ser 50%");
    }

    @Test
    public void testGetMostFrequentCardsInFinalHands() {
        Card card1 = new Card();
        card1.setName("Caballeria Ligera");

        Card card2 = new Card();
        card2.setName("Buque de guerra");

        Card card3 = new Card();
        card3.setName("Caballeria Ligera");

        mockPlayers.forEach(player -> player.setPlayerHand(Arrays.asList(card1, card2, card3)));

        when(playerService.getPlayersByUser(currentUser1)).thenReturn(mockPlayers);

        List<String> mostFrequentCards = statisticService.getMostFrequentCardsInFinalHands(currentUser1);

        assertNotNull(mostFrequentCards);
        assertEquals(Arrays.asList("Caballeria Ligera", "Buque de guerra"), mostFrequentCards);
    }

    // @Test
    // public void testGetMostFrequentCardTypeInFinalHands() {
    // Card card1 = new Card();
    // card1.setCardType(CardType.EJERCITO);

    // Card card2 = new Card();
    // card2.setCardType(CardType.ARMA);

    // Card card3 = new Card();
    // card3.setCardType(CardType.EJERCITO);

    // mockPlayers.forEach(player -> player.setPlayerHand(Arrays.asList(card1,
    // card2, card3)));

    // when(playerService.getPlayersByUser(currentUser)).thenReturn(mockPlayers);

    // CardType mostFrequentCardType =
    // statisticService.getMostFrequentCardTypeInFinalHands(currentUser);

    // assertNotNull(mostFrequentCardType);
    // assertEquals(CardType.EJERCITO, mostFrequentCardType);
    // }

    // No hay jugadores asociados al usuario actual.
    @Test
    public void testGetTotalMatches_NoPlayers() {
        when(playerService.countPlayersByUser(currentUser1)).thenReturn(0);

        Integer totalMatches = statisticService.getTotalMatches(currentUser1);

        assertNotNull(totalMatches);
        assertEquals(0, totalMatches, "El total de partidas debería ser 0 si no hay jugadores asociados.");
    }

    // No hay partidas asociadas al usuario actual.

    @Test
    public void testGetAverageMatchDuration_NoMatches() {
        when(matchService.findMatchesByUser(currentUser1)).thenReturn(new ArrayList<>());

        Double averageDuration = statisticService.getAverageMatchDuration(currentUser1);

        assertNotNull(averageDuration);
        assertEquals(0.0, averageDuration, "La duración promedio debería ser 0 si no hay partidas.");
    }

    // Una partida no tiene jugadores.
    @Test
    public void testGetAveragePlayersPerMatch_NoPlayersInMatch() {
        Match matchWithoutPlayers = new Match();
        matchWithoutPlayers.setPlayers(new ArrayList<>()); // Sin jugadores

        when(matchService.findMatchesByUser(currentUser1)).thenReturn(Arrays.asList(matchWithoutPlayers));

        Double averagePlayers = statisticService.getAveragePlayersPerMatch(currentUser1);

        assertNotNull(averagePlayers);
        assertEquals(0.0, averagePlayers, "El promedio de jugadores por partida debería ser 0 si no hay jugadores.");
    }

    // No hay jugadores en las manos finales.
    @Test
    public void testGetMostFrequentCardsInFinalHands_NoPlayers() {
        when(playerService.getPlayersByUser(currentUser1)).thenReturn(new ArrayList<>());

        List<String> mostFrequentCards = statisticService.getMostFrequentCardsInFinalHands(currentUser1);

        assertNotNull(mostFrequentCards);
        assertTrue(mostFrequentCards.isEmpty(),
                "La lista de cartas frecuentes debería estar vacía si no hay jugadores.");
    }

    // No hay cartas en las manos iniciales.

    @Test
    public void testGetMostFrequentCardsInFinalHands_NoCards() {
        mockPlayers.forEach(player -> player.setPlayerHand(new ArrayList<>()));

        when(playerService.getPlayersByUser(currentUser1)).thenReturn(mockPlayers);

        List<String> mostFrequentCards = statisticService.getMostFrequentCardsInFinalHands(currentUser1);

        assertNotNull(mostFrequentCards);
        assertTrue(mostFrequentCards.isEmpty(),
                "La lista de cartas frecuentes debería estar vacía si los jugadores no tienen cartas.");
    }

    // Obtener victorias totales del usuario -> si tiene 1 victoria y si no tiene
    // ninguna

    @Test
    public void testGetTotalWins() {

        // Tiene 1 victoria

        // Configurar los roles para los jugadores de la partida 1 (partida 3 viene
        // pre-configurada)
        mockPlayers.get(0).setRole(PlayerType.GANADOR); // currentUser2 gana esta partida
        mockPlayers.get(1).setRole(PlayerType.PARTICIPANTE);
        mockPlayers.get(2).setRole(PlayerType.PARTICIPANTE);
        mockPlayers.get(0).setUser(currentUser2);

        // Entonces currentUser2 gana partida 1 pero no gana ni la 2 ni la 3.

        // Mock del PlayerService
        when(playerService.getPlayersByUser(currentUser2)).thenReturn(List.of(mockPlayers.get(0), mockPlayers3.get(1)));

        // Llamar al método a probar
        long totalWins = statisticService.getTotalWins(currentUser2);

        // Verificar el resultado
        assertEquals(1, totalWins, "El usuario debería tener una victoria registrada.");

        // Verificar que el PlayerService fue llamado correctamente
        verify(playerService, times(1)).getPlayersByUser(currentUser2);

        // No tiene ninguna victoria

        // Cambiamos la partida ganada
        mockPlayers.get(0).setRole(PlayerType.PARTICIPANTE); // currentUser2 pierde
        mockPlayers.get(1).setRole(PlayerType.GANADOR);

        // Llamar al método a probar
        long totalWins2 = statisticService.getTotalWins(currentUser2);

        // Verificar el resultado
        assertEquals(0, totalWins2, "El usuario no debería tener una victoria registrada.");
    }

    // Calcular la racha de victorias -> Caso 1: El usuario no tiene racha y Caso 2:
    // El usuario tiene racha de 2 victorias

    @Test
    public void testGetCurrentWinStreak_NoWinStreak() {

        // Caso 1

        // Crear una nueva partida donde currentUser1 no gana
        Match newMatch = new Match();
        newMatch.setStartDate(LocalDateTime.of(2025, 1, 10, 13, 0));
        newMatch.setEndDate(LocalDateTime.of(2025, 1, 10, 13, 15));
        Player player10 = new Player(currentUser1, PlayerType.PARTICIPANTE); // currentUser1 no gana
        Player player11 = new Player(currentUser2, PlayerType.GANADOR);
        Player player12 = new Player(currentUser3, PlayerType.PARTICIPANTE);
        player10.setMatchPlayed(newMatch);
        player11.setMatchPlayed(newMatch);
        player12.setMatchPlayed(newMatch);
        newMatch.setPlayers(List.of(player10, player11, player12));

        // Combinar partidas relevantes para este test
        List<Match> matches = List.of(mockMatches.get(2), mockMatches.get(3), newMatch); // match3, match4 y la nueva
                                                                                         // partida

        List<Player> user1Players = List.of(matches.get(0).getPlayers().get(0), matches.get(0).getPlayers().get(1),
                player10);

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(user1Players);

        // Llamar al método a probar
        int currentWinStreak = statisticService.getCurrentWinStreak(currentUser1);

        // Validar que no tiene racha de victorias
        assertEquals(0, currentWinStreak, "La racha de victorias consecutivas debería ser 0.");

        // Verificar que el método del servicio fue llamado
        verify(playerService, times(1)).getPlayersByUser(currentUser1);

        // Caso 2

        // Cambiamos la fecha de la partida que pierde (el año)
        newMatch.setStartDate(LocalDateTime.of(2024, 1, 10, 13, 0));
        newMatch.setEndDate(LocalDateTime.of(2024, 1, 10, 13, 15));

        // Ahora debe tener racha de 2 victorias

        int currentWinStreak2 = statisticService.getCurrentWinStreak(currentUser1);

        // Validar que la racha de victorias consecutivas sea 2 (match3 y match4, pero
        // no la nueva partida)
        assertEquals(2, currentWinStreak2, "La racha de victorias consecutivas debería ser 2.");
    }

    @Test
    public void testLastGameWasFromLastPlace() {
        // Configurar match4 como la última partida y match3 como la penúltima
        Match lastMatch = mockMatches.get(3); // Última partida
        Match secondToLastMatch = mockMatches.get(2); // Penúltima partida

        // Configurar currentUser1 como ganador en match4
        lastMatch.getPlayers().get(0).setRole(PlayerType.GANADOR); // currentUser1 gana
        lastMatch.getPlayers().get(0).setScore(70); // Puntaje más alto
        lastMatch.getPlayers().get(1).setScore(50);
        lastMatch.getPlayers().get(2).setScore(30);

        // Configurar currentUser1 como último en match3
        secondToLastMatch.getPlayers().get(0).setRole(PlayerType.PARTICIPANTE);
        secondToLastMatch.getPlayers().get(0).setScore(30); // Puntaje más bajo para currentUser1
        secondToLastMatch.getPlayers().get(1).setScore(50);
        secondToLastMatch.getPlayers().get(2).setScore(70);

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(secondToLastMatch.getPlayers().get(0), lastMatch.getPlayers().get(0)));

        // Llamar al método a probar
        boolean result = statisticService.lastGameWasFromLastPlace(currentUser1);

        // Validar que cumple las condiciones
        assertTrue(result,
                "El usuario debería haber quedado en última posición en la penúltima partida y ganado la última.");

        // Cambiar las condiciones para que no cumpla (currentUser1 no gana la última
        // partida)
        lastMatch.getPlayers().get(0).setRole(PlayerType.PARTICIPANTE);

        // Llamar nuevamente al método
        boolean result2 = statisticService.lastGameWasFromLastPlace(currentUser1);

        // Validar que no cumple las condiciones
        assertFalse(result2, "El usuario no debería cumplir con las condiciones al no ganar la última partida.");

        // Verificar que el servicio de PlayerService fue llamado correctamente
        verify(playerService, times(2)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testGetLastWonMatch() {

        Match lastMatch = mockMatches.get(3); // Última partida (match4)

        // Configurar match3 como no ganada por currentUser1
        Match secondToLastMatch = mockMatches.get(2); // Penúltima partida (match3)
        secondToLastMatch.getPlayers().get(0).setRole(PlayerType.PARTICIPANTE); // currentUser1 no gana

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(secondToLastMatch.getPlayers().get(0), lastMatch.getPlayers().get(0)));

        // Llamar al método a probar
        Match result = statisticService.getLastWonMatch(currentUser1);

        // Validar que el resultado es la última partida ganada por el usuario
        assertNotNull(result, "El resultado no debería ser nulo cuando el usuario ganó la última partida.");
        assertEquals(lastMatch, result, "La última partida ganada debería coincidir con la esperada.");

        // Cambiar el rol del usuario en la última partida para que no sea GANADOR
        lastMatch.getPlayers().get(0).setRole(PlayerType.PARTICIPANTE);

        // Llamar nuevamente al método
        Match result2 = statisticService.getLastWonMatch(currentUser1);

        // Validar que ahora el resultado es nulo
        assertNull(result2, "El resultado debería ser nulo si el usuario no ganó la última partida.");

        // Verificar que el servicio de PlayerService fue llamado correctamente
        verify(playerService, times(2)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testGetPointsLastMatch() {

        Match lastMatch = mockMatches.get(3); // Última partida (match4)
        // Configuramos los puntos obtenidos por el usuario
        lastMatch.getPlayers().get(0).setScore(70); // Puntaje del usuario

        // Configurar match3 como no ganada por currentUser1
        Match secondToLastMatch = mockMatches.get(2); // Penúltima partida (match3)
        secondToLastMatch.getPlayers().get(0).setRole(PlayerType.PARTICIPANTE); // currentUser1 no gana

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(secondToLastMatch.getPlayers().get(0), lastMatch.getPlayers().get(0)));

        // Llamar al método a probar
        int points = statisticService.getPointsLastMatch(currentUser1);

        // Validar que el puntaje coincide con el esperado
        assertEquals(70, points, "El puntaje de la última partida ganada debería coincidir con el esperado.");

        // Cambiar el rol del usuario en la última partida para que no sea GANADOR
        lastMatch.getPlayers().get(0).setRole(PlayerType.PARTICIPANTE);

        // Llamar nuevamente al método
        int points2 = statisticService.getPointsLastMatch(currentUser1);

        // Validar que el puntaje ahora es 0
        assertEquals(0, points2, "El puntaje debería ser 0 si el usuario no ganó la última partida.");

        // Verificar que el servicio de PlayerService fue llamado correctamente
        verify(playerService, times(2)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testHadKingAndQueenInLastGame() {
        // Configurar la mano del jugador en match4
        Match lastMatch = mockMatches.get(3); // match4
        Player player = lastMatch.getPlayers().get(0); // currentUser1

        // Suponemos que la mano tiene solo 3 cartas
        List<Card> playerHand = List.of(
                new Card("Rey", CardType.LIDER, 8),
                new Card("Reina", CardType.LIDER, 6),
                new Card("Emperatriz", CardType.LIDER, 15));
        player.setPlayerHand(playerHand);

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(player));

        // Llamar al método a probar
        boolean result = statisticService.hadKingAndQueenInLastGame(currentUser1);

        // Validar que el resultado es true porque tiene "Rey" y "Reina" en la última
        // partida ganada
        assertTrue(result, "El usuario debería tener 'Rey' y 'Reina' en su última partida ganada.");

        // Cambiar la mano para que no tenga ambos
        player.setPlayerHand(List.of(
                new Card("Rey", CardType.LIDER, 8),
                new Card("Emperatriz", CardType.LIDER, 15)));

        // Llamar nuevamente al método
        boolean result2 = statisticService.hadKingAndQueenInLastGame(currentUser1);

        // Validar que el resultado ahora es false
        assertFalse(result2, "El usuario no debería tener 'Rey' y 'Reina' en su última partida ganada.");

        // Verificar que el servicio fue llamado correctamente
        verify(playerService, times(2)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testHadBeastmasterAnd3Beasts() {
        // Configurar la mano del jugador en match4
        Match lastMatch = mockMatches.get(3); // match4
        Player player = lastMatch.getPlayers().get(0); // currentUser1

        List<Card> playerHand = List.of(
                new Card("Maestro de bestias", CardType.MAGO, 9),
                new Card("Bestia 1", CardType.BESTIA, 2),
                new Card("Bestia 2", CardType.BESTIA, 2),
                new Card("Bestia 3", CardType.BESTIA, 2));
        player.setPlayerHand(playerHand);

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(player));

        // Llamar al método a probar
        boolean result = statisticService.hadBeastmasterAnd3Beasts(currentUser1);

        // Validar que el resultado es true porque tiene "Maestro de bestias" y 3 cartas
        // "Bestia"
        assertTrue(result,
                "El usuario debería tener 'Maestro de bestias' y al menos 3 cartas 'Bestia' en su última partida ganada.");

        // Cambiar la mano para que no tenga suficientes cartas "Bestia"
        player.setPlayerHand(List.of(
                new Card("Maestro de bestias", CardType.MAGO, 9),
                new Card("Bestia 1", CardType.BESTIA, 2),
                new Card("Bestia 2", CardType.BESTIA, 2)));

        // Llamar nuevamente al método
        boolean result2 = statisticService.hadBeastmasterAnd3Beasts(currentUser1);

        // Validar que el resultado ahora es false
        assertFalse(result2,
                "El usuario no debería tener 'Maestro de bestias' y al menos 3 cartas 'Bestia' en su última partida ganada.");

        // Verificar que el servicio fue llamado correctamente
        verify(playerService, times(2)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testHadSwordAndShieldOfKeth() {
        // Configurar la mano del jugador en match4
        Match lastMatch = mockMatches.get(3); // match4
        Player player = lastMatch.getPlayers().get(0); // currentUser1

        List<Card> playerHand = List.of(
                new Card("Escudo de Keth", CardType.ARTEFACTO, 4),
                new Card("Espada de Keth", CardType.ARMA, 7),
                new Card("Emperatriz", CardType.LIDER, 15));
        player.setPlayerHand(playerHand);

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(player));

        // Llamar al método a probar
        boolean result = statisticService.hadSwordAndShieldOfKeth(currentUser1);

        // Validar que el resultado es true porque tiene "Escudo de Keth" y "Espada de
        // Keth"
        assertTrue(result, "El usuario debería tener 'Escudo de Keth' y 'Espada de Keth' en su última partida ganada.");

        // Cambiar la mano para que falte una de las cartas
        player.setPlayerHand(List.of(
                new Card("Escudo de Keth", CardType.ARTEFACTO, 4),
                new Card("Emperatriz", CardType.LIDER, 15)));

        // Llamar nuevamente al método
        boolean result2 = statisticService.hadSwordAndShieldOfKeth(currentUser1);

        // Validar que el resultado ahora es false
        assertFalse(result2,
                "El usuario no debería tener 'Escudo de Keth' y 'Espada de Keth' en su última partida ganada.");

        // Verificar que el servicio fue llamado correctamente
        verify(playerService, times(2)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testHad4Elementals() {
        // Configurar la mano del jugador en match4
        Match lastMatch = mockMatches.get(3); // match4
        Player player = lastMatch.getPlayers().get(0); // currentUser1

        List<Card> playerHand = List.of(
                new Card("Elemental de Fuego", CardType.LLAMA, 4),
                new Card("Elemental de Agua", CardType.INUNDACION, 4),
                new Card("Elemental de Tierra", CardType.TIERRA, 4),
                new Card("Elemental de aire", CardType.TIEMPO, 4));
        player.setPlayerHand(playerHand);

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(player));

        // Llamar al método a probar
        boolean result = statisticService.had4Elementals(currentUser1);

        // Validar que el resultado es true porque tiene los 4 elementales
        assertTrue(result, "El usuario debería tener los 4 elementales en su última partida ganada.");

        // Cambiar la mano para que falte un elemental
        player.setPlayerHand(List.of(
                new Card("Elemental de Fuego", CardType.LLAMA, 4),
                new Card("Elemental de Agua", CardType.INUNDACION, 4),
                new Card("Elemental de Tierra", CardType.TIERRA, 4)));

        // Llamar nuevamente al método
        boolean result2 = statisticService.had4Elementals(currentUser1);

        // Validar que el resultado ahora es false
        assertFalse(result2, "El usuario no debería tener los 4 elementales en su última partida ganada.");

        // Verificar que el servicio fue llamado correctamente
        verify(playerService, times(2)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testHadNoRareCardsInLastGame() {
        // Configurar la mano del jugador en match4
        Match lastMatch = mockMatches.get(3); // match4
        Player player = lastMatch.getPlayers().get(0); // currentUser1

        List<Mod> allowedMods = List.of(new BonusBasicWizzardInHand());
        List<Mod> restrictedMods = List.of(new ChangeType(), new NecromancerMod());

        Card card1 = new Card("Carta 1", CardType.LIDER, 5);
        Card card2 = new Card("Carta 2", CardType.LIDER, 5);

        card1.setMods(allowedMods);
        card2.setMods(restrictedMods);

        List<Card> playerHand = List.of(card1, card2);

        player.setPlayerHand(playerHand);

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(player));

        // Llamar al método a probar
        boolean result = statisticService.hadNoRareCardsInLastGame(currentUser1);

        // Validar que el resultado es false porque tiene cartas con mods restringidos
        assertFalse(result, "El usuario no debería cumplir con la condición de no tener cartas raras.");

        // Cambiar la mano para que solo tenga mods permitidos
        player.getPlayerHand().get(1).setMods(allowedMods);

        // Llamar nuevamente al método
        boolean result2 = statisticService.hadNoRareCardsInLastGame(currentUser1);

        // Validar que el resultado ahora es true
        assertTrue(result2, "El usuario debería cumplir con la condición de no tener cartas raras.");

        // Verificar que el servicio fue llamado
        verify(playerService, times(2)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testGetTotalFireCardsPlayed() {
        // Configurar las cartas del usuario
        Player player = mockPlayers3.get(0); // currentUser1

        List<Card> playerHand = List.of(
                new Card("Carta 1", CardType.LLAMA, 3),
                new Card("Carta 2", CardType.LLAMA, 3),
                new Card("Carta 3", CardType.LIDER, 3));
        player.setPlayerHand(playerHand);

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(player));

        // Llamar al método a probar
        int totalFireCards = statisticService.getTotalCardsPlayedByType(currentUser1,"LLAMA");

        // Validar que el usuario tiene 2 cartas de tipo "Llama"
        assertEquals(2, totalFireCards, "El usuario debería tener 2 cartas de tipo 'Llama'.");

        // Verificar que el servicio fue llamado
        verify(playerService, times(1)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testGetTotalFloodCardsPlayed() {
        // Configurar las cartas del usuario
        Player player = mockPlayers3.get(0); // currentUser1

        List<Card> playerHand = List.of(
                new Card("Carta 1", CardType.INUNDACION, 3),
                new Card("Carta 2", CardType.LIDER, 3),
                new Card("Carta 3", CardType.INUNDACION, 3));
        player.setPlayerHand(playerHand);

        // Mockear el servicio para devolver los jugadores del usuario
        when(playerService.getPlayersByUser(currentUser1))
                .thenReturn(List.of(player));

        // Llamar al método a probar
        int totalFloodCards = statisticService.getTotalCardsPlayedByType(currentUser1,"INUNDACION");

        // Validar que el usuario tiene 2 cartas de tipo "Inundación"
        assertEquals(2, totalFloodCards, "El usuario debería tener 2 cartas de tipo 'Inundación'.");

        // Verificar que el servicio fue llamado
        verify(playerService, times(1)).getPlayersByUser(currentUser1);
    }

    @Test
    public void testGetTotalCardType_ShouldFail() {

        // Supongamos que se quiere contar las cartas de un tipo usadas pero no es válido

        // Validar que se lanza la excepción

        assertThrows(InvalidStatesException.class, () -> {statisticService.getTotalCardsPlayedByType(currentUser1,"PRUEBA");});
        
    }

    @Test
    public void testGetAverageRankingPosition() {
        // Mockeamos jugadores con diferentes puntuaciones
        Player player1 = new Player();
        player1.setScore(50);

        Player player2 = new Player();
        player2.setScore(30);

        Player player3 = new Player();
        player3.setScore(70);

        Match match = new Match();
        match.setPlayers(Arrays.asList(player1, player2, player3));

        // Asignamos la partida a los jugadores
        player1.setMatchPlayed(match);
        player2.setMatchPlayed(match);
        player3.setMatchPlayed(match);

        // Asignamos el usuario actual al jugador
        player1.setUser(currentUser1);

        // Mockeamos el servicio para devolver solo el jugador actual
        when(playerService.getPlayersByUser(currentUser1)).thenReturn(Arrays.asList(player1));

        // Llamamos al método a probar
        Double averageRankingPosition = statisticService.getAverageRankingPosition(currentUser1);

        // Verificamos el resultado esperado
        assertNotNull(averageRankingPosition);
        assertEquals(2.0, averageRankingPosition, 0.01); // El jugador 1 está en la posición 2
    }

    @Test
    public void testGetAverageTurnsPerMatch() {
        // Mockeamos los jugadores y partidas
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();

        Match match1 = new Match();
        match1.setPlayers(Arrays.asList(player1, player2, player3));

        // Mockeamos el turno actual de la partida
        Turn mockTurn = mock(Turn.class);
        when(mockTurn.getTurnCount()).thenReturn(10); // Supongamos que hay 10 turnos en esta partida
        match1.setCurrentTurn(mockTurn);

        // Asignamos la partida a los jugadores
        player1.setMatchPlayed(match1);
        player2.setMatchPlayed(match1);
        player3.setMatchPlayed(match1);

        // Asignamos el usuario actual a uno de los jugadores
        player1.setUser(currentUser1);

        // Mockeamos el servicio para devolver solo el jugador actual
        when(playerService.getPlayersByUser(currentUser1)).thenReturn(Arrays.asList(player1));

        // Llamamos al método a probar
        Double averageTurnsPerMatch = statisticService.getAverageTurnsPerMatch(currentUser1);

        // Verificamos el resultado esperado
        assertNotNull(averageTurnsPerMatch);
        assertEquals(3.33, averageTurnsPerMatch, 0.01); // 10 turnos / 3 jugadores
    }

    @Test
    public void testGetTotalMatchDuration() {
        // Aquí seleccionamos algunos partidos para un usuario específico
        Match match1 = mockMatches.get(0); // Duración 20 minutos
        Match match2 = mockMatches.get(1); // Duración 15 minutos

        // Simulamos que el servicio devuelve estos partidos para el usuario actual
        when(matchService.findMatchesByUser(currentUser1)).thenReturn(Arrays.asList(match1, match2));

        // Calculamos la duración total de las partidas
        Integer totalDuration = statisticService.getTotalMatchDuration(currentUser1);

        // Verificamos que la duración total es la esperada
        assertNotNull(totalDuration);
        assertEquals(35, totalDuration); // 20 + 15 = 35 minutos
    }

    @Test
    public void testGetMaxMatchDuration() {
        // Seleccionamos los partidos para el usuario
        Match match1 = mockMatches.get(0); // Duración 20 minutos
        Match match2 = mockMatches.get(1); // Duración 15 minutos
        Match match3 = mockMatches.get(3); // Duración 15 minutos

        // Simulamos que el servicio devuelve estos partidos para el usuario
        when(matchService.findMatchesByUser(currentUser1)).thenReturn(Arrays.asList(match1, match2, match3));

        // Obtenemos la duración máxima de las partidas
        Integer maxDuration = statisticService.getMaxMatchDuration(currentUser1);

        // Verificamos que la duración máxima es la correcta
        assertNotNull(maxDuration);
        assertEquals(20, maxDuration); // El partido más largo tiene 20 minutos
    }

    @Test
    public void testGetMinMatchDuration() {
        // Seleccionamos los partidos para el usuario
        Match match1 = mockMatches.get(0); // Duración 20 minutos
        Match match2 = mockMatches.get(1); // Duración 15 minutos
        Match match3 = mockMatches.get(3); // Duración 15 minutos

        // Simulamos que el servicio devuelve estos partidos para el usuario
        when(matchService.findMatchesByUser(currentUser1)).thenReturn(Arrays.asList(match1, match2, match3));

        // Obtenemos la duración mínima de las partidas
        Integer minDuration = statisticService.getMinMatchDuration(currentUser1);

        // Verificamos que la duración mínima es la correcta
        assertNotNull(minDuration);
        assertEquals(15, minDuration); // El partido más corto tiene 15 minutos
    }

    @Test
    public void testGetGlobalAverageMatchDuration() {
        // Simulamos que el servicio devuelve todos los partidos
        when(matchService.findAllMatches()).thenReturn(mockMatches);

        // Calculamos la duración media global de las partidas
        Double avgDuration = statisticService.getGlobalAverageMatchDuration();

        // Verificamos que la duración media global es la correcta
        assertNotNull(avgDuration);
        assertEquals(16.25, avgDuration); // (20 + 15 + 15 + 15) / 4 = 16.25 minutos
    }

    @Test
    public void testGetGlobalMaxMatchDuration() {
        // Simulamos que el servicio devuelve todos los partidos
        when(matchService.findAllMatches()).thenReturn(mockMatches);

        // Obtenemos la duración máxima global de las partidas
        Integer maxDuration = statisticService.getGlobalMaxMatchDuration();

        // Verificamos que la duración máxima global es la correcta
        assertNotNull(maxDuration);
        assertEquals(20, maxDuration); // El partido más largo tiene 20 minutos
    }

    @Test
    public void testGetGlobalMinMatchDuration() {
        // Simulamos que el servicio devuelve todos los partidos
        when(matchService.findAllMatches()).thenReturn(mockMatches);

        // Obtenemos la duración mínima global de las partidas
        Integer minDuration = statisticService.getGlobalMinMatchDuration();

        // Verificamos que la duración mínima global es la correcta
        assertNotNull(minDuration);
        assertEquals(15, minDuration); // El partido más corto tiene 15 minutos
    }

    @Test
    public void testGetMaxPlayersPerMatch() {
        // Seleccionamos los partidos del usuario
        Match match1 = mockMatches.get(0); // 3 jugadores
        Match match2 = mockMatches.get(1); // 3 jugadores
        Match match3 = mockMatches.get(3); // 3 jugadores

        // Simulamos que el servicio devuelve estos partidos para el usuario
        when(matchService.findMatchesByUser(currentUser1)).thenReturn(Arrays.asList(match1, match2, match3));

        // Obtenemos el máximo número de jugadores por partida
        Integer maxPlayers = statisticService.getMaxPlayersPerMatch(currentUser1);

        // Verificamos que el número máximo de jugadores por partida es el esperado
        assertNotNull(maxPlayers);
        assertEquals(3, maxPlayers); // Todos los partidos tienen 3 jugadores
    }

    @Test
    public void testGetMinPlayersPerMatch() {
        // Seleccionamos los partidos del usuario
        Match match1 = mockMatches.get(0); // 3 jugadores
        Match match2 = mockMatches.get(1); // 3 jugadores
        Match match3 = mockMatches.get(3); // 3 jugadores

        // Simulamos que el servicio devuelve estos partidos para el usuario
        when(matchService.findMatchesByUser(currentUser1)).thenReturn(Arrays.asList(match1, match2, match3));

        // Obtenemos el mínimo número de jugadores por partida
        Integer minPlayers = statisticService.getMinPlayersPerMatch(currentUser1);

        // Verificamos que el número mínimo de jugadores por partida es el esperado
        assertNotNull(minPlayers);
        assertEquals(3, minPlayers); // Todos los partidos tienen 3 jugadores
    }

    @Test
    public void testGetMaxGlobalPlayersPerMatch() {
        // Simulamos que el servicio devuelve todos los partidos
        when(matchService.findAllMatches()).thenReturn(mockMatches);

        // Obtenemos el máximo número de jugadores por partida global
        Integer maxPlayers = statisticService.getMaxGlobalPlayersPerMatch();

        // Verificamos que el número máximo de jugadores por partida es el esperado
        assertNotNull(maxPlayers);
        assertEquals(3, maxPlayers); // Todos los partidos tienen 3 jugadores
    }

    @Test
    public void testGetMinGlobalPlayersPerMatch() {
        // Simulamos que el servicio devuelve todos los partidos
        when(matchService.findAllMatches()).thenReturn(mockMatches);

        // Obtenemos el mínimo número de jugadores por partida global
        Integer minPlayers = statisticService.getMinGlobalPlayersPerMatch();

        // Verificamos que el número mínimo de jugadores por partida es el esperado
        assertNotNull(minPlayers);
        assertEquals(3, minPlayers); // Todos los partidos tienen 3 jugadores
    }

    @Test
    public void testGetGlobalAveragePlayersPerMatch() {
        // Simulamos que el servicio devuelve todos los partidos
        when(matchService.findAllMatches()).thenReturn(mockMatches);

        // Calculamos el número medio de jugadores por partida global
        Double avgPlayers = statisticService.getGlobalAveragePlayersPerMatch();

        // Verificamos que el número medio de jugadores por partida es el esperado
        assertNotNull(avgPlayers);
        assertEquals(3.0, avgPlayers); // Todos los partidos tienen 3 jugadores
    }

    // Comprueba que el ranking se genera correctamente y se ordena por el número de
    // victorias

    @Test
    public void estRankingSortedByWins() {
        currentUser1 = new User();
        currentUser1.setId(1);
        currentUser1.setUsername("User1");

        currentUser2 = new User();
        currentUser2.setId(2);
        currentUser2.setUsername("User2");

        currentUser3 = new User();
        currentUser3.setId(3);
        currentUser3.setUsername("User3");

        // Jugadores y puntuaciones
        Player player1 = new Player(currentUser1, PlayerType.GANADOR);
        player1.setScore(100);

        Player player2 = new Player(currentUser2, PlayerType.PARTICIPANTE);
        player2.setScore(50);

        Player player3 = new Player(currentUser3, PlayerType.GANADOR);
        player3.setScore(80);

        // Partidas simuladas
        Match match1 = new Match();
        match1.setPlayers(Arrays.asList(player1, player2));

        Match match2 = new Match();
        match2.setPlayers(Arrays.asList(player2, player3));

        Match match3 = new Match();
        match3.setPlayers(Arrays.asList(player1, player3));

        // Mock de las partidas
        when(matchService.findAllMatches()).thenReturn(Arrays.asList(match1, match2, match3));

        Map<String, List<Integer>> ranking = statisticService.getRanking("wins");

        assertNotNull(ranking);
        assertEquals(3, ranking.size()); // Debe haber 3 usuarios en el ranking
        assertTrue(ranking.containsKey("User1"));
        assertTrue(ranking.containsKey("User2"));
        assertTrue(ranking.containsKey("User3"));

        assertEquals(2, ranking.get("User1").get(0)); // User1 tiene 2 victorias
        assertEquals(200, ranking.get("User1").get(1)); // User1 tiene 200 puntos

        assertEquals(0, ranking.get("User2").get(0)); // User2 tiene 0 victorias
        assertEquals(100, ranking.get("User2").get(1)); // User2 tiene 100 puntos

        assertEquals(2, ranking.get("User3").get(0)); // User3 tiene 2 victorias
        assertEquals(160, ranking.get("User3").get(1)); // User3 tiene 160 puntos

        List<String> sortedKeys = ranking.keySet().stream().toList();

        assertEquals("User1", sortedKeys.get(0)); // User1 debe estar primero (más victorias)
        assertEquals("User3", sortedKeys.get(1)); // User3 debe estar segundo (empate en victorias, orden original)
        assertEquals("User2", sortedKeys.get(2)); // User2 debe estar al final (sin victorias)
    }

    @Test
    public void testRankingSortedByPoints() {
        currentUser1 = new User();
        currentUser1.setId(1);
        currentUser1.setUsername("User1");

        currentUser2 = new User();
        currentUser2.setId(2);
        currentUser2.setUsername("User2");

        currentUser3 = new User();
        currentUser3.setId(3);
        currentUser3.setUsername("User3");

        // Jugadores y puntuaciones
        Player player1 = new Player(currentUser1, PlayerType.GANADOR);
        player1.setScore(100);

        Player player2 = new Player(currentUser2, PlayerType.PARTICIPANTE);
        player2.setScore(50);

        Player player3 = new Player(currentUser3, PlayerType.GANADOR);
        player3.setScore(80);

        // Partidas simuladas
        Match match1 = new Match();
        match1.setPlayers(Arrays.asList(player1, player2));

        Match match2 = new Match();
        match2.setPlayers(Arrays.asList(player2, player3));

        Match match3 = new Match();
        match3.setPlayers(Arrays.asList(player1, player3));

        // Mock de las partidas
        when(matchService.findAllMatches()).thenReturn(Arrays.asList(match1, match2, match3));
        Map<String, List<Integer>> ranking = statisticService.getRanking("points");

        List<String> sortedKeys = ranking.keySet().stream().toList();

        assertEquals("User1", sortedKeys.get(0)); // User1 debe estar primero (más puntos)
        assertEquals("User3", sortedKeys.get(1)); // User3 debe estar segundo
        assertEquals("User2", sortedKeys.get(2)); // User2 debe estar al final
    }

}
