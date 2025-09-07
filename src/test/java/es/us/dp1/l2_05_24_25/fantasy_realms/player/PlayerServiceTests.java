package es.us.dp1.l2_05_24_25.fantasy_realms.player;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.Card;
import es.us.dp1.l2_05_24_25.fantasy_realms.card.CardService;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.deck.DeckService;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.DiscardService;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.CardStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.MatchStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.PlayerStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.TurnStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.Turn;
import es.us.dp1.l2_05_24_25.fantasy_realms.turn.TurnService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTests {

    protected PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private CardService cardService;

    @Mock
    private UserService userService;

    @Mock
    private TurnService turnService;

    @Mock
    private DeckService deckService;

    @Mock
    private DiscardService discardService;

    @BeforeEach
    public void setup() {

        playerService = new PlayerService(playerRepository, cardService, userService, turnService, deckService, discardService);

    }


    @Test
    void testDrawCardFromDeck_Success() {

        User user = mock(User.class);
        when(user.getUsername()).thenReturn("testUser");
    
        Match match = mock(Match.class);
        Player player = mock(Player.class);
        Turn turn = mock(Turn.class);
        Card card = mock(Card.class);
        Deck deck = mock(Deck.class);
    
        when(match.isInProgress()).thenReturn(true);
    
        when(match.getPlayers()).thenReturn(Collections.singletonList(player));
        when(player.getUser()).thenReturn(user);
        when(match.getCurrentTurn()).thenReturn(turn);
        when(turn.getPlayer()).thenReturn(player);
    
        when(turn.canDrawCard()).thenReturn(true);
        when(deckService.drawCard(anyInt())).thenReturn(card);
    
        when(match.getDeck()).thenReturn(deck);
        when(deck.getId()).thenReturn(1);
    
        Player result = playerService.drawCardFromDeck(user, match);
    
        assertNotNull(result);
        verify(deckService).drawCard(anyInt());
        verify(turn).drawFromDeck(card);
    }

    @Test
    void testDrawCardFromDeck_PlayerNotFound() {
        User user = mock(User.class);
        Match match = mock(Match.class);
        when(match.getPlayers()).thenReturn(Collections.emptyList());

        assertThrows(PlayerStatesException.class, () -> playerService.drawCardFromDeck(user, match));
    }

    @Test
    public void testDrawCardFromDiscard_Success() {

        Integer cardId = 1;
        
        Match mockMatch = mock(Match.class);
        Player mockPlayer = mock(Player.class);
        User mockUser = mock(User.class);
        Card mockCard = mock(Card.class);
        Discard mockDiscard = mock(Discard.class);
        Turn mockTurn = mock(Turn.class);
        
        when(mockUser.getUsername()).thenReturn("testUser");
        
        List<Card> playerHand = new ArrayList<>();
        when(mockPlayer.getPlayerHand()).thenReturn(playerHand);
        when(mockPlayer.getUser()).thenReturn(mockUser);
        
        when(mockMatch.getCurrentTurn()).thenReturn(mockTurn);
        when(mockMatch.getDiscard()).thenReturn(mockDiscard);
        when(mockMatch.getPlayers()).thenReturn(Collections.singletonList(mockPlayer));
        when(mockMatch.isInProgress()).thenReturn(true);
        
        when(mockTurn.getPlayer()).thenReturn(mockPlayer);
        when(mockTurn.canDrawCard()).thenReturn(true);
        when(mockTurn.getTurnCount()).thenReturn(2);
        
        when(discardService.drawCard(anyInt(), eq(cardId))).thenReturn(mockCard);
        
        Player result = playerService.drawCardFromDiscard(mockUser, mockMatch, cardId);
        
        assertNotNull(result);
        assertEquals(1, playerHand.size());
        assertTrue(playerHand.contains(mockCard));
        
        verify(discardService).drawCard(eq(mockMatch.getDiscard().getId()), eq(cardId));
        
        verify(mockTurn).drawFromDiscard(mockCard);
    }

    @Test
    public void testDrawCardFromDiscard_PlayerNotFound() {
        User mockUser = mock(User.class);
        Match mockMatch = mock(Match.class);

        when(mockMatch.getPlayers()).thenReturn(Collections.emptyList());

        assertThrows(PlayerStatesException.class, () -> playerService.drawCardFromDiscard(mockUser, mockMatch, 1));
    }


    @Test
    void testDiscardCard_Success() {

        User mockUser = new User();
        mockUser.setUsername("testUser");
    
        Match mockMatch = mock(Match.class);
        when(mockMatch.isInProgress()).thenReturn(true);
        when(mockMatch.isInScoringPhase()).thenReturn(false);
    
        Discard mockDiscard = mock(Discard.class);
        when(mockMatch.getDiscard()).thenReturn(mockDiscard);
        when(mockDiscard.getCards()).thenReturn(new ArrayList<>());
    
        Player mockPlayer = new Player();
        mockPlayer.setUser(mockUser);
        Card mockCard = new Card();
        mockCard.setId(1);
        mockPlayer.setPlayerHand(new ArrayList<>(List.of(mockCard)));
    
        Turn mockTurn = mock(Turn.class);
        when(mockTurn.getPlayer()).thenReturn(mockPlayer);
        when(mockTurn.canDrawCard()).thenReturn(false);
        when(mockTurn.hasDiscarded()).thenReturn(false);
    
        when(mockMatch.getCurrentTurn()).thenReturn(mockTurn);
        when(mockMatch.getPlayers()).thenReturn(List.of(mockPlayer));
    
        when(cardService.findCardById(mockCard.getId())).thenReturn(mockCard);
    
        Player result = playerService.discardCard(mockUser, mockMatch, mockCard.getId());
    
        verify(discardService).discardCardFromHand(mockMatch, mockPlayer, mockCard);
        assertEquals(mockPlayer, result);
    }

    @Test
    void testDiscardCard_CardNotInHand() {
        User user = mock(User.class);
        Match match = mock(Match.class);
        Player player = mock(Player.class);
        Card card = mock(Card.class);

        when(match.getPlayers()).thenReturn(Collections.singletonList(player));
        when(player.getUser()).thenReturn(user);
        when(cardService.findCardById(anyInt())).thenReturn(card);

        assertThrows(MatchStatesException.class, () -> playerService.discardCard(user, match, 1));
    }

    @Test
    void testDrawCardFromDiscardAndUpdatePlayerAndMatch_Success() {

        Integer cardId = 1;
        
        Match match = mock(Match.class);
        Player player = mock(Player.class);
        Card card = mock(Card.class);
        Turn currentTurn = mock(Turn.class);

        List<Card> playerHand = new ArrayList<>();
        when(player.getPlayerHand()).thenReturn(playerHand);
        
        when(match.getCurrentTurn()).thenReturn(currentTurn);
        when(match.getDiscard()).thenReturn(mock(Discard.class));
        
        when(discardService.drawCard(anyInt(), eq(cardId))).thenReturn(card);

        playerService.drawCardFromDiscardAndUpdatePlayerAndMatch(match, player, cardId);

        assertEquals(1, playerHand.size());
        assertTrue(playerHand.contains(card));

        verify(discardService).drawCard(eq(match.getDiscard().getId()), eq(cardId));
        
        verify(currentTurn).drawFromDiscard(card);
    }

    @Test
    public void testFindPlayerById() {
        Player player = mock(Player.class);

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        Player result = playerService.findPlayerById(1);

        assertNotNull(result);
        verify(playerRepository, times(1)).findById(1);
    }

    @Test
    public void testFindPlayerByIdThrowsException() {
        when(playerRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> playerService.findPlayerById(1));
    }

    @Test
    public void testSavePlayer() {
        Player player = mock(Player.class);

        when(playerRepository.save(player)).thenReturn(player);

        Player result = playerService.savePlayer(player);

        assertNotNull(result);
        verify(playerRepository, times(1)).save(player);
    }

    @Test
    public void testCreatePlayerForUser() {
        User user = mock(User.class);
        when(userService.findUser(1)).thenReturn(user);

        Player player = playerService.createPlayerForUser(1, PlayerType.PARTICIPANTE);

        assertNotNull(player);
        assertEquals(user, player.getUser());
    }

    @Test
    public void testSumPlayerPoints() {
        Player player = mock(Player.class);
        Card card1 = mock(Card.class);
        Card card2 = mock(Card.class);

        when(player.getPlayerHand()).thenReturn(List.of(card1, card2));
        when(card1.getFinalValue()).thenReturn(5);
        when(card2.getFinalValue()).thenReturn(10);

        playerService.sumPlayerPoints(player);

        verify(player, times(1)).setScore(15);
    }

    @Test
    void testGetMatchesCreatedByUser() {
        Integer userId = 1;
    
        Player creatorPlayer = mock(Player.class);
    
        Match match1 = mock(Match.class);
        
        Match match2 = mock(Match.class);
    
        List<Match> expectedMatches = Arrays.asList(match1, match2);
    
        when(playerRepository.findMatchesCreatedByUser(userId)).thenReturn(expectedMatches);
    
        List<Match> result = playerService.getMatchesCreatedByUser(userId);
    
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(match1));
        assertTrue(result.contains(match2));
        
        verify(playerRepository, times(1)).findMatchesCreatedByUser(userId);
    }

    @Test
    void testEnsureMatchIsNotInScoringPhaseThrowsException() {
        Match match = mock(Match.class);
        when(match.isInScoringPhase()).thenReturn(true);

        assertThrows(MatchStatesException.class, () -> playerService.ensureMatchIsNotInScoringPhase(match));
    }

    @Test
    void testEnsureMatchIsNotInScoringPhaseSuccess() {
        Match match = mock(Match.class);
        when(match.isInScoringPhase()).thenReturn(false);

        assertDoesNotThrow(() -> playerService.ensureMatchIsNotInScoringPhase(match));
    }

    @Test
    void testEnsureUserHasCurrentTurnThrowsException() {
        Turn turn = mock(Turn.class);
        Player player = mock(Player.class);
        User user = mock(User.class);

        when(turn.getPlayer()).thenReturn(player);
        when(player.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("otherUser");

        User currentUser = mock(User.class);
        when(currentUser.getUsername()).thenReturn("testUser");

        assertThrows(TurnStatesException.class, () -> playerService.ensureUserHasCurrentTurn(turn, currentUser));
    }

    @Test
    void testEnsureUserHasCurrentTurnSuccess() {
        Turn turn = mock(Turn.class);
        Player player = mock(Player.class);
        User user = mock(User.class);

        when(turn.getPlayer()).thenReturn(player);
        when(player.getUser()).thenReturn(user);
        when(user.getUsername()).thenReturn("testUser");

        User currentUser = mock(User.class);
        when(currentUser.getUsername()).thenReturn("testUser");

        assertDoesNotThrow(() -> playerService.ensureUserHasCurrentTurn(turn, currentUser));
    }

    @Test
    void testEnsureNoActionsMadeThrowsException() {
        Turn turn = mock(Turn.class);
        when(turn.canDrawCard()).thenReturn(false);

        assertThrows(TurnStatesException.class, () -> playerService.ensureNoActionsMade(turn));
    }

    @Test
    void testEnsureNoActionsMadeSuccess() {
        Turn turn = mock(Turn.class);
        when(turn.canDrawCard()).thenReturn(true);

        assertDoesNotThrow(() -> playerService.ensureNoActionsMade(turn));
    }

    @Test
    void testEnsureActionsMadeThrowsException() {
        Turn turn = mock(Turn.class);
        when(turn.canDrawCard()).thenReturn(true);

        assertThrows(TurnStatesException.class, () -> playerService.ensureActionsMade(turn));
    }

    @Test
    void testEnsureActionsMadeSuccess() {
        Turn turn = mock(Turn.class);
        when(turn.canDrawCard()).thenReturn(false);

        assertDoesNotThrow(() -> playerService.ensureActionsMade(turn));
    }

    @Test
    void testEnsureCardToDiscardInHandThrowsException() {
        Player player = mock(Player.class);
        Card card = mock(Card.class);

        when(player.isCardInHand(card)).thenReturn(false);

        assertThrows(CardStatesException.class, () -> playerService.ensureCardToDiscardInHand(player, card));
    }

    @Test
    void testEnsureCardToDiscardInHandSuccess() {
        Player player = mock(Player.class);
        Card card = mock(Card.class);

        when(player.isCardInHand(card)).thenReturn(true);

        assertDoesNotThrow(() -> playerService.ensureCardToDiscardInHand(player, card));
    }

    @Test
    void testEnsureNotDiscardedThrowsException() {
        Match match = mock(Match.class);
        Turn turn = mock(Turn.class);

        when(match.getCurrentTurn()).thenReturn(turn);
        when(turn.hasDiscarded()).thenReturn(true);

        assertThrows(TurnStatesException.class, () -> playerService.ensureNotDiscarded(match));
    }

    @Test
    void testEnsureNotDiscardedSuccess() {
        Match match = mock(Match.class);
        Turn turn = mock(Turn.class);

        when(match.getCurrentTurn()).thenReturn(turn);
        when(turn.hasDiscarded()).thenReturn(false);

        assertDoesNotThrow(() -> playerService.ensureNotDiscarded(match));
    }

    @Test
    void testEnsureIsNotFirstTurnThrowsException() {
        Turn turn = mock(Turn.class);
        when(turn.getTurnCount()).thenReturn(1);

        assertThrows(TurnStatesException.class, () -> playerService.ensureIsNotFirstTurn(turn));
    }

    @Test
    void testEnsureIsNotFirstTurnSuccess() {
        Turn turn = mock(Turn.class);
        when(turn.getTurnCount()).thenReturn(2);

        assertDoesNotThrow(() -> playerService.ensureIsNotFirstTurn(turn));
    }

}
