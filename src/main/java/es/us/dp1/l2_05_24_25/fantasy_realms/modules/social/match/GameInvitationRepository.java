package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import jakarta.transaction.Transactional;

public interface GameInvitationRepository extends CrudRepository<GameInvitation, Integer> {

    @Query("SELECT gi FROM GameInvitation gi WHERE gi.receiver = :receiver")
    List<GameInvitation> findByReceiver(User receiver);

    @Query("SELECT gi FROM GameInvitation gi WHERE gi.receiver = :receiver AND gi.status = false AND gi.match = :match")
    GameInvitation findByReceiverAndMatch(User receiver, Match match);  
    
    @Modifying
    @Transactional
    @Query("DELETE FROM GameInvitation g WHERE " +
           "(g.sender.id = :userId AND g.receiver.id = :friendId) OR " +
           "(g.sender.id = :friendId AND g.receiver.id = :userId)")
    void deleteBySenderOrReceiver(@Param("userId") Integer userId, @Param("friendId") Integer friendId);

}
