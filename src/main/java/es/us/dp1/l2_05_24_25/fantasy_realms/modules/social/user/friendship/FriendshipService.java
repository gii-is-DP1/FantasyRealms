package es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.match.GameInvitationRepository;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserService userService;
    private final GameInvitationRepository gameInvitationRepository;

    @Autowired
    public FriendshipService(FriendshipRepository friendshipRepository, UserService userService, GameInvitationRepository gameInvitationRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userService = userService;
        this.gameInvitationRepository = gameInvitationRepository;
    }

    @Transactional(readOnly = true)
    public Boolean areFriends(Integer userId, Integer friendId) {

        if (userId.equals(friendId)) {
            throw new IllegalArgumentException("El usuario y su amigo no pueden ser la misma persona.");
        }

        return friendshipRepository.existsById(FriendshipId.create(userId, friendId));
    }

    @Transactional(readOnly = true)
    public List<Friendship> getFriends(Integer userId) {
        return friendshipRepository.findByUserAndStatus(userId, "ACCEPTED");
    }

    @Transactional(readOnly = true)
    public List<Friendship> getAllFriendshipsForUser(Integer userId) {
        return friendshipRepository.findAllByUserId(userId);
    }

    @Transactional
    public Friendship sendFriendRequest(Integer senderId, Integer receiverId) {
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("El remitente y el receptor no pueden ser la misma persona.");
        }

        FriendshipId fid = FriendshipId.create(senderId, receiverId);

        if (friendshipRepository.existsById(fid)) {
            throw new IllegalStateException("Ya existe una amistad o solicitud entre ambos usuarios.");
        }

        Friendship friendship = new Friendship();
        friendship.setId(fid);
        friendship.setUser(userService.findUser(fid.getUserId()));
        friendship.setFriend(userService.findUser(fid.getFriendId()));
        friendship.setStatus("PENDING");
        friendship.setSenderId(senderId);
        friendship.setReceiverId(receiverId);

        return friendshipRepository.save(friendship);
    }

    @Transactional
    public Friendship acceptFriendRequest(Integer senderId, Integer receiverId) {
        FriendshipId fid = FriendshipId.create(senderId, receiverId);

        Friendship friendship = friendshipRepository.findById(fid)
            .orElseThrow(() -> new IllegalStateException("No existe una solicitud de amistad con esos IDs."));

        if (!"PENDING".equals(friendship.getStatus())) {
            throw new IllegalStateException("La solicitud no está en estado PENDING.");
        }

        if (!friendship.getReceiverId().equals(receiverId)) {
            throw new IllegalStateException("Solo el receptor puede aceptar la solicitud.");
        }

        friendship.setStatus("ACCEPTED");
        return friendshipRepository.save(friendship);
    }

    @Transactional
    public void deleteFriendship(Integer userId, Integer friendId) {
        FriendshipId fid = FriendshipId.create(userId, friendId);

        if (!friendshipRepository.existsById(fid)) {
            throw new IllegalStateException("No existe amistad/solicitud con esos IDs.");
        }

        friendshipRepository.deleteById(fid);

        gameInvitationRepository.deleteBySenderOrReceiver(userId, friendId);
    }

    @Transactional
    public List<FriendshipRequestDTO> getFriendshipsForUserByStatus(Integer userId, String status) {
        List<Friendship> friendships = friendshipRepository.findByUserAndStatus(userId, status);

        return friendships.stream()
            .filter(f -> "PENDING".equals(status) && f.getReceiverId().equals(userId))
            .map(f -> new FriendshipRequestDTO(
                f.getSenderId(),
                userService.findUser(f.getSenderId()).getUsername(),
                f.getReceiverId(),
                userService.findUser(f.getReceiverId()).getUsername(),
                f.getStatus()
            ))
            .collect(Collectors.toList());
    }
}
