package es.us.dp1.l2_05_24_25.fantasy_realms.user.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.l2_05_24_25.fantasy_realms.configuration.jwt.JwtUtils;
import es.us.dp1.l2_05_24_25.fantasy_realms.configuration.services.UserDetailsImpl;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.statistic.achievements.Achievement;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.auth.payload.request.LoginRequest;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.auth.payload.request.SignupRequest;

/**
 * Test class for {@link OwnerRestController}
 *
 */

@WebMvcTest(value = AuthController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = {
		SecurityAutoConfiguration.class })
class AuthControllerTests {

	private static final String BASE_URL = "/api/v1/auth";

	@SuppressWarnings("unused")
	@Autowired
	private AuthController authController;

	@MockBean
	private AuthenticationManager authenticationManager;

	@MockBean
	private JwtUtils jwtUtils;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthService authService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	private LoginRequest loginRequest;
	private SignupRequest signupRequest;
	private UserDetailsImpl userDetails;
	private String token;

	@BeforeEach
	void setup() {
		loginRequest = new LoginRequest();
		loginRequest.setUsername("owner");
		loginRequest.setPassword("password");

		signupRequest = new SignupRequest();
		signupRequest.setUsername("username");
		signupRequest.setPassword("password");
		signupRequest.setAuthority("OWNER");

		// Crear lista de logros (vacía para el propósito del test)
		List<Achievement> achievements = new ArrayList<>();

		// Crear UserDetailsImpl con todos los atributos requeridos
		userDetails = new UserDetailsImpl(
				1, // id
				loginRequest.getUsername(), // username
				loginRequest.getPassword(), // password
				List.of(new SimpleGrantedAuthority("OWNER")), // authorities
				"avatar.png", // avatar (valor de prueba)
				"owner@example.com", // email (valor de prueba)
				achievements // achievements (lista vacía)
		);

		token = "JWT TOKEN";
	}

	@Test
	void shouldAuthenticateUser() throws Exception {
		Authentication auth = Mockito.mock(Authentication.class);

		when(this.jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn(token);
		when(this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
		Mockito.doReturn(userDetails).when(auth).getPrincipal();

		mockMvc.perform(post(BASE_URL + "/signin").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value(loginRequest.getUsername()))
				.andExpect(jsonPath("$.id").value(userDetails.getId())).andExpect(jsonPath("$.token").value(token));
	}

	@Test
	void shouldValidateToken() throws Exception {
		when(this.jwtUtils.validateJwtToken(token)).thenReturn(true);

		mockMvc.perform(get(BASE_URL + "/validate").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.param("token", token)).andExpect(status().isOk())
				.andExpect(jsonPath("$").value(true));
	}

	@Test
	void shouldNotValidateToken() throws Exception {
		when(this.jwtUtils.validateJwtToken(token)).thenReturn(false);

		mockMvc.perform(get(BASE_URL + "/validate").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.param("token", token)).andExpect(status().isOk())
				.andExpect(jsonPath("$").value(false));
	}

	@Test
	void shouldRegisterUser() throws Exception {
		when(this.userService.existsUser(signupRequest.getUsername())).thenReturn(false);
		doNothing().when(this.authService).createUser(signupRequest);

		mockMvc.perform(post(BASE_URL + "/signup").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signupRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("User registered successfully!"));
	}

	@Test
	void shouldNotRegisterUserWithExistingUsername() throws Exception {
		when(this.userService.existsUser(signupRequest.getUsername())).thenReturn(true);

		mockMvc.perform(post(BASE_URL + "/signup").with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(signupRequest))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Error: Username is already taken!"));
	}

}
