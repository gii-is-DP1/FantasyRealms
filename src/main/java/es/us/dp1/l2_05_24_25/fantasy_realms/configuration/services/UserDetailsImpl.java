package es.us.dp1.l2_05_24_25.fantasy_realms.configuration.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.Achievement;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String username;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	private String avatar;

	private String email;
	
	private List<Achievement> achievements;

	public UserDetailsImpl(Integer id, String username, String password,
			Collection<? extends GrantedAuthority> authorities, String avatar,
			String email, List<Achievement> achievements) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.avatar = avatar;
		this.email = email;
		this.achievements = achievements;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getAuthority().getAuthority()));

		return new UserDetailsImpl(user.getId(), user.getUsername(),
				user.getPassword(),
				authorities,
				user.getAvatar(),
				user.getEmail(),
				user.getAchievements()
				);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}


	public Integer getId() {
		return id;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getEmail() {
		return email;
	}

	public List<Achievement> getAchievements() {
		return achievements;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetailsImpl other = (UserDetailsImpl) obj;
		return Objects.equals(id, other.id);
	}

}
