package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship.FriendshipService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

@Service
public class GameInvitationService {

    private final GameInvitationRepository gameInvitationRepository;
    private final FriendshipService friendshipService;
    private final MatchService matchService;

    @Autowired
    public GameInvitationService(GameInvitationRepository GameInvitationRepository, FriendshipService friendshipService, MatchService matchService) {
        this.gameInvitationRepository = GameInvitationRepository;
        this.friendshipService = friendshipService;	
        this.matchService = matchService;
    }

    @Transactional(readOnly=true)
    public List<GameInvitationDTO> getGameInvitations(User receiver) {
        List<GameInvitation> inv = gameInvitationRepository.findByReceiver(receiver);

        return inv.stream().map(i -> new GameInvitationDTO(i.getSender().getUsername(), i.getReceiver().getUsername(), i.isStatus(), i.getMatch().getId())).toList();
    }

    @Transactional
    public void sendGameInvitation(User sender, User receiver, Match match) {

        if (!friendshipService.areFriends(sender.getId(), receiver.getId())) {
            throw new IllegalArgumentException("Los usuarios no son amigos.");
        }

        Match existingMatch = matchService.findMatchById(match.getId());

        if (existingMatch == null) {
            throw new IllegalArgumentException("La partida no existe.");
        }

        if (existingMatch.isInProgress()) {
            throw new IllegalArgumentException("La partida ya ha empezado.");
        }
        if(existingMatch.getPlayers().stream().filter(player -> player.getUser().equals(receiver)).count() > 0) {
            throw new IllegalArgumentException("El usuario ya está en la partida.");
        }

        GameInvitation existingInvitation = gameInvitationRepository.findByReceiverAndMatch(receiver, match);

        if (existingInvitation != null) {
            throw new IllegalArgumentException("Ya existe una invitación para esta partida.");
        }

        GameInvitation gi = new GameInvitation();
        gi.setSender(sender);
        gi.setReceiver(receiver);
        gi.setMatch(match);
        gi.setStatus(false);

        gameInvitationRepository.save(gi);
    }

    @Transactional
    public void acceptGameInvitation(User receiver, User sender, Match match) {

        if (!friendshipService.areFriends(sender.getId(), receiver.getId())) {
            throw new IllegalArgumentException("Los usuarios no son amigos.");
        }

        GameInvitation gi = gameInvitationRepository.findByReceiverAndMatch(receiver, match);

        if (gi == null) {
            throw new IllegalArgumentException("No existe una invitación para esta partida.");
        }

        Match existingMatch = matchService.findMatchById(match.getId());

        if (existingMatch == null) {
            throw new IllegalArgumentException("La partida no existe.");
        }

        if (existingMatch.isInProgress()) {
            throw new IllegalArgumentException("La partida ya ha empezado.");
        }

        matchService.joinMatch(match.getId(), receiver);

        gi.setStatus(true);

        gameInvitationRepository.save(gi);
    }
    
    @Transactional
    public void rejectGameInvitation(User receiver, Match match) {

        GameInvitation gi = gameInvitationRepository.findByReceiverAndMatch(receiver, match);

        if (gi == null) {
            throw new IllegalArgumentException("No existe una invitación para esta partida.");
        }

        gameInvitationRepository.delete(gi);
    
    }

}
