package es.us.dp1.l2_05_24_25.fantasy_realms.dto;


import java.util.List;

import es.us.dp1.l2_05_24_25.fantasy_realms.match.Match;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.Achievement;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends BaseEntity {

    @Column(unique = true)
    String username;

    String avatar;

    @NotNull
    @Email
    @Column(unique = true)
    private String email;

    private List<Achievement> achievements;

    private List<MatchDTO> matches; // Lista de partidas jugadas por el usuario (si es un jugador)

    private String authority;

    private String password;

    public UserDTO() {}

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.avatar = user.getAvatar();
        this.email = user.getEmail();
        this.achievements = user.getAchievements();
        List<Match> mList = user.getPlayers().stream().map(p -> p.getMatchPlayed()).toList();
        this.matches = mList.stream().map(MatchDTO::new).toList();
        this.authority = user.getAuthority().getAuthority();
    }

        public UserDTO(Integer id, String username, String email, String authority, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.authority = authority;
        this.password = password;
    }
    

}
