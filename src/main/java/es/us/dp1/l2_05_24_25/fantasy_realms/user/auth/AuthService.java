package es.us.dp1.l2_05_24_25.fantasy_realms.user.auth;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.InvalidStatesException;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.auth.payload.request.SignupRequest;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.Authorities;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.AuthoritiesService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class AuthService {

	private final PasswordEncoder encoder;
	private final AuthoritiesService authoritiesService;
	private final UserService userService;
	//private final PlayerService playerService;
	

	@Autowired
	public AuthService(PasswordEncoder encoder, AuthoritiesService authoritiesService, UserService userService
			// PlayerService playerService
			) {
		this.encoder = encoder;
		this.authoritiesService = authoritiesService;
		this.userService = userService;
		//this.playerService = ownerService;		
	}

	@Transactional
	public void createUser(@Valid SignupRequest request) {
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setAchievements(new ArrayList<>()); // Inicializamos con lista vacía

		if(!request.getAuthority().toUpperCase().equals("ADMIN") && !request.getAuthority().toUpperCase().equals("PLAYER")) {
			throw new InvalidStatesException("You cant use that role!");
		}
		
		Authorities role = new Authorities();
		role.setAuthority(request.getAuthority().toUpperCase());
		role.setUser(user);

		user.setAuthority(role);

		// Guardar el usuario con la información básica
		authoritiesService.saveAuthorities(role);
		userService.saveUserBasic(user);
	}

	

}
