package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FriendshipRepository extends CrudRepository<Friendship, FriendshipId> {

    @Query("SELECT f FROM Friendship f "
         + "WHERE (f.senderId = :userId OR f.receiverId = :userId) "
         + "AND f.status = :status")
    List<Friendship> findByUserAndStatus(@Param("userId") Integer userId, @Param("status") String status);

    @Query("SELECT f FROM Friendship f WHERE f.user.id = :userId OR f.friend.id = :userId")
    List<Friendship> findAllByUserId(@Param("userId") Integer userId);

}