package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import java.util.List;

import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerDTO {
    private Integer id;

    private String username;

    private Integer score;

    public List<CardDTO> playerHand;

    @Enumerated(value = EnumType.STRING)
    private PlayerType rol;

    public PlayerDTO() {
    }

    public PlayerDTO(Integer id, String username, Integer score, PlayerType rol, List<CardDTO> playerHand) {
        this.id = id;
        this.username = username;
        this.score = score;
        this.rol = rol;
        this.playerHand = playerHand;
    }

    public PlayerDTO(Player p) {
        this.id = p.getUser().getId();
        this.username = p.getUser().getUsername();
        this.playerHand = p.getPlayerHand().stream().map(CardDTO::new).toList();
        this.score = p.getScore();
        this.rol = p.getRole();
    }



}
