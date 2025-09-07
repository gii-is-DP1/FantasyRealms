package es.us.dp1.l2_05_24_25.fantasy_realms.user;


import java.util.List;
import java.util.Set;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.UserUpdateProfileDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.model.BaseEntity;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.Achievement;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.Authorities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad que representa un usuario en la aplicación. Un usuario contiene
 * un nombre de usuario, contraseña, avatar, correo, lista de logros, unos
 * permisos y una lista de jugadores asociados a partidas que ha jugado
 */
@Getter
@Setter
@Entity
@Table(name = "appusers")
public class User extends BaseEntity {

	@Column(unique = true)
	String username;

	String password;

	String avatar;

	@Email
	@Column(unique = true)
	private String email;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Achievement> achievements;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "authority")
	Authorities authority;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Player> players;

	public Boolean hasAuthority(String auth) {
		return authority.getAuthority().equals(auth);
	}

	public Boolean hasAnyAuthority(String... authorities) {
		Boolean cond = false;
		for (String auth : authorities) {
			if (auth.equals(authority.getAuthority()))
				cond = true;
		}
		return cond;
	}

	/**
	 * Método que permite saber si un usario tien asociado un jugador que está en partida
	 */
	public Boolean isPlaying() {
		return this.getPlayers().stream().anyMatch(p -> p.isInLobbyOrPlaying());
	}

	public User() {}

	public User(String username, String password, String avatar, String email, List<Achievement> achievements, Authorities authority, Set<Player> players) {
		this.username = username;
		this.password = password;
		this.avatar = avatar;
		this.email = email;
		this.achievements = achievements;
		this.authority = authority;
		this.players = players;
	}

	public User(UserUpdateProfileDTO dto) {
		this.username = dto.getUsername();
		this.password = dto.getPassword();
		this.avatar = dto.getAvatar();
		this.email = dto.getEmail();
	}

}
