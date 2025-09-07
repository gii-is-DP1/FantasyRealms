package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship.FriendshipService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

@ExtendWith(SpringExtension.class)
public class GameInvitationServiceTests {

    protected GameInvitationService gameInvitationService;

    @Mock
    private GameInvitationRepository gameInvitationRepository;

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private MatchService matchService;

    private User sender;
    private User receiver;
    private Match match;
    private Player player;

    @BeforeEach
    void setUp() {

        gameInvitationService = new GameInvitationService(gameInvitationRepository, friendshipService, matchService);

        // Configurar usuarios
        sender = new User();
        sender.setId(1);
        sender.setUsername("Sender");

        receiver = new User();
        receiver.setId(2);
        receiver.setUsername("Receiver");

        // Configurar partida
        Deck deck = new Deck();
        Discard discard = new Discard();
        match = new Match("Test Match", deck, discard, new ArrayList<>());
        match.setId(1);
        match.setStartDate(null);

        // Configurar jugador
        player = new Player(sender, PlayerType.CREADOR);
        match.addPlayer(player);
    }

    @Test
    void testGetGameInvitations() {
        GameInvitation invitation = new GameInvitation();
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setMatch(match);
        invitation.setStatus(false);

        when(gameInvitationRepository.findByReceiver(receiver)).thenReturn(List.of(invitation));

        List<GameInvitationDTO> invitations = gameInvitationService.getGameInvitations(receiver);

        assertEquals(1, invitations.size());
        GameInvitationDTO dto = invitations.get(0);
        assertEquals("Sender", dto.getSenderUsername());
        assertEquals("Receiver", dto.getReceiverUsername());
        assertEquals(1, dto.getMatchId());
        assertFalse(dto.isStatus());

        verify(gameInvitationRepository, times(1)).findByReceiver(receiver);
    }

    @Test
    void testSendGameInvitation_Success() {
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(matchService.findMatchById(match.getId())).thenReturn(match);
        when(gameInvitationRepository.findByReceiverAndMatch(receiver, match)).thenReturn(null);

        gameInvitationService.sendGameInvitation(sender, receiver, match);

        verify(gameInvitationRepository, times(1)).save(any(GameInvitation.class));
    }

    @Test
    void testSendGameInvitation_NotFriends() {
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            gameInvitationService.sendGameInvitation(sender, receiver, match)
        );

        assertEquals("Los usuarios no son amigos.", exception.getMessage());
        verifyNoInteractions(gameInvitationRepository);
    }

    @Test
    void testSendGameInvitation_MatchInProgress() {
        match.setStartDate(LocalDateTime.now());
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(matchService.findMatchById(match.getId())).thenReturn(match);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            gameInvitationService.sendGameInvitation(sender, receiver, match)
        );

        assertEquals("La partida ya ha empezado.", exception.getMessage());
        verifyNoInteractions(gameInvitationRepository);
    }

    @Test
    void testAcceptGameInvitation_Success() {
        GameInvitation invitation = new GameInvitation();
        invitation.setSender(sender);
        invitation.setReceiver(receiver);
        invitation.setMatch(match);
        invitation.setStatus(false);

        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(gameInvitationRepository.findByReceiverAndMatch(receiver, match)).thenReturn(invitation);
        when(matchService.findMatchById(match.getId())).thenReturn(match);

        gameInvitationService.acceptGameInvitation(receiver, sender, match);

        verify(matchService, times(1)).joinMatch(match.getId(), receiver);
        verify(gameInvitationRepository, times(1)).save(invitation);
        assertTrue(invitation.isStatus());
    }

    @Test
    void testAcceptGameInvitation_NoInvitation() {
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(gameInvitationRepository.findByReceiverAndMatch(receiver, match)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            gameInvitationService.acceptGameInvitation(receiver, sender, match)
        );

        assertEquals("No existe una invitación para esta partida.", exception.getMessage());
        verifyNoInteractions(matchService);
    }

    @Test
    void testRejectGameInvitation_Success() {
        GameInvitation invitation = new GameInvitation();
        invitation.setReceiver(receiver);
        invitation.setMatch(match);

        when(gameInvitationRepository.findByReceiverAndMatch(receiver, match)).thenReturn(invitation);

        gameInvitationService.rejectGameInvitation(receiver, match);

        verify(gameInvitationRepository, times(1)).delete(invitation);
    }

    @Test
    void testRejectGameInvitation_NoInvitation() {
        when(gameInvitationRepository.findByReceiverAndMatch(receiver, match)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            gameInvitationService.rejectGameInvitation(receiver, match)
        );

        assertEquals("No existe una invitación para esta partida.", exception.getMessage());
        verify(gameInvitationRepository, times(0)).delete(any(GameInvitation.class));
    }
    
    @Test
    void testSendGameInvitation_UsersNotFriends() {
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gameInvitationService.sendGameInvitation(sender, receiver, match));

        assertEquals("Los usuarios no son amigos.", exception.getMessage());
    }

    @Test
    void testSendGameInvitation_MatchDoesNotExist() {
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(matchService.findMatchById(match.getId())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gameInvitationService.sendGameInvitation(sender, receiver, match));

        assertEquals("La partida no existe.", exception.getMessage());
    }

    @Test
    void testSendGameInvitation_MatchAlreadyStarted() {
        match.setStartDate(LocalDateTime.now());
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(matchService.findMatchById(match.getId())).thenReturn(match);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gameInvitationService.sendGameInvitation(sender, receiver, match));

        assertEquals("La partida ya ha empezado.", exception.getMessage());
    }

    @Test
    void testSendGameInvitation_UserAlreadyInMatch() {
        Player player = new Player(receiver, PlayerType.PARTICIPANTE);
        match.getPlayers().add(player);

        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(matchService.findMatchById(match.getId())).thenReturn(match);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gameInvitationService.sendGameInvitation(sender, receiver, match));

        assertEquals("El usuario ya está en la partida.", exception.getMessage());
    }

    @Test
    void testSendGameInvitation_InvitationAlreadyExists() {
        GameInvitation existingInvitation = new GameInvitation();
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(matchService.findMatchById(match.getId())).thenReturn(match);
        when(gameInvitationRepository.findByReceiverAndMatch(receiver, match)).thenReturn(existingInvitation);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gameInvitationService.sendGameInvitation(sender, receiver, match));

        assertEquals("Ya existe una invitación para esta partida.", exception.getMessage());
    }

    @Test
    void testAcceptGameInvitation_InvitationDoesNotExist() {
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(gameInvitationRepository.findByReceiverAndMatch(receiver, match)).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gameInvitationService.acceptGameInvitation(receiver, sender, match));

        assertEquals("No existe una invitación para esta partida.", exception.getMessage());
    }

    @Test
    void testAcceptGameInvitation_MatchDoesNotExist() {
        GameInvitation invitation = new GameInvitation();
        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(gameInvitationRepository.findByReceiverAndMatch(receiver, match)).thenReturn(invitation);
        when(matchService.findMatchById(match.getId())).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gameInvitationService.acceptGameInvitation(receiver, sender, match));

        assertEquals("La partida no existe.", exception.getMessage());
    }

    @Test
    void testAcceptGameInvitation_MatchAlreadyStarted() {
        GameInvitation invitation = new GameInvitation();
        match.setStartDate(LocalDateTime.now());

        when(friendshipService.areFriends(sender.getId(), receiver.getId())).thenReturn(true);
        when(gameInvitationRepository.findByReceiverAndMatch(receiver, match)).thenReturn(invitation);
        when(matchService.findMatchById(match.getId())).thenReturn(match);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> gameInvitationService.acceptGameInvitation(receiver, sender, match));

        assertEquals("La partida ya ha empezado.", exception.getMessage());
    }
    
}
