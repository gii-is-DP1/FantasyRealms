package es.us.dp1.l2_05_24_25.fantasy_realms.user;


import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.UserUpdateProfileDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.AccessDeniedException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.match.MatchService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerService;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.Authorities;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.AuthoritiesService;

/**
 * Test class for the {@link VetController}
 */
@WebMvcTest(controllers = UserRestController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
class UserControllerTests {

	private static final int TEST_USER_ID = 1;
	private static final int TEST_AUTH_ID = 1;
	private static final String BASE_URL = "/api/v1/users";

	@SuppressWarnings("unused")
	@Autowired
	private UserRestController userController;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthoritiesService authService;
	
	@MockBean
	private MatchService matchService;

	@MockBean
	private PlayerService playerService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	private Authorities auth;
	private User user, logged;

	@BeforeEach
	void setup() {
		auth = new Authorities();
		auth.setId(TEST_AUTH_ID);
		auth.setAuthority("ADMIN");

		user = new User();
		user.setId(1);
		user.setUsername("user");
		user.setPassword("password");
		user.setAuthority(auth);
		user.setAchievements(new ArrayList<>()); // Inicializar achievements como una lista vacía

		when(this.userService.findCurrentUser()).thenReturn(getUserFromDetails(
				(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
	}

	private User getUserFromDetails(UserDetails details) {
		logged = new User();
		logged.setUsername(details.getUsername());
		logged.setPassword(details.getPassword());
		Authorities aux = new Authorities();
		for (GrantedAuthority ga : details.getAuthorities()) {
			// ga.getAuthority() puede devolver "ROLE_ADMIN"
			// Ajusto a "ADMIN" para que hasAuthority("ADMIN") coincida
			if (ga.getAuthority().equals("ROLE_ADMIN")) {
				aux.setAuthority("ADMIN");
			} else {
				aux.setAuthority(ga.getAuthority());
			}
		}
		logged.setAuthority(aux);
		return logged;
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void shouldFindAllAuths() throws Exception {
		Authorities authAdmin = new Authorities();
		authAdmin.setId(1);
		authAdmin.setAuthority("ADMIN");

		Authorities authUser = new Authorities();
		authUser.setId(2);
		authUser.setAuthority("USER");

		when(this.authService.findAll()).thenReturn(List.of(authAdmin, authUser));

		mockMvc.perform(get(BASE_URL + "/authorities")
			.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.size()").value(2))
			.andExpect(jsonPath("$[?(@.id == 1)].authority").value("ADMIN"))
			.andExpect(jsonPath("$[?(@.id == 2)].authority").value("USER"));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void shouldReturnUser() throws Exception {
		
		Authorities authority = new Authorities();
		authority.setAuthority("USER");

		User mockUser = new User();
		mockUser.setId(TEST_USER_ID);
		mockUser.setUsername("usuario de prueba");
		mockUser.setPassword("password");
		mockUser.setAuthority(authority);
		mockUser.setPlayers(new HashSet<>());

		
		when(this.userService.findUser(TEST_USER_ID)).thenReturn(mockUser);

		
		mockMvc.perform(get(BASE_URL + "/{id}", TEST_USER_ID)
		.with(csrf()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value(TEST_USER_ID))
		.andExpect(jsonPath("$.username").value("usuario de prueba"))
		.andExpect(jsonPath("$.authority").value("USER"));

	}

	@Test
	@WithMockUser("admin")
	void shouldReturnNotFoundUser() throws Exception {
		when(this.userService.findUser(TEST_USER_ID)).thenThrow(ResourceNotFoundException.class);
		mockMvc.perform(get(BASE_URL + "/{id}", TEST_USER_ID)).andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void shouldCreateUser() throws Exception {
		UserUpdateProfileDTO aux = new UserUpdateProfileDTO();
		aux.setUsername("Prueba");
		aux.setPassword("Prueba");
		aux.setAuthority(auth.getAuthority());

		mockMvc.perform(post(BASE_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(aux))).andExpect(status().isCreated());
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void shouldDeleteOtherUserAdmin() throws Exception {
		// Configuración del usuario logueado (debe ser admin)
		logged.setId(2); // Usuario logueado tiene ID = 2
		Authorities adminAuthority = new Authorities();
		adminAuthority.setAuthority("ADMIN");
		logged.setAuthority(adminAuthority);

		// Configuración del usuario a eliminar
		user.setId(TEST_USER_ID);
		user.setUsername("userToDelete");

		// Configuración de los mocks
		when(this.userService.findUser(TEST_USER_ID)).thenReturn(user); // Usuario con ID = TEST_USER_ID a eliminar
		when(this.userService.findCurrentUser()).thenReturn(logged); // Usuario actual es admin con ID = 2
		doNothing().when(this.userService).deleteUser(TEST_USER_ID);

		// Ejecutar la petición DELETE y verificar el resultado
		mockMvc.perform(delete(BASE_URL + "/{id}", TEST_USER_ID)
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("User deleted!"));
	}

	@Test
	@WithMockUser("admin")
	void shouldNotDeleteLoggedUser() throws Exception {
		logged.setId(TEST_USER_ID);

		when(this.userService.findUser(TEST_USER_ID)).thenReturn(user);
		doNothing().when(this.userService).deleteUser(TEST_USER_ID);

		mockMvc.perform(delete(BASE_URL + "/{id}", TEST_USER_ID).with(csrf())).andExpect(status().isForbidden())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof AccessDeniedException));
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void shouldReturnAllUsersPaginated() throws Exception {
		// Configurar datos de prueba
		User user1 = new User();
		user1.setId(1);
		user1.setUsername("User1");
		user1.setEmail("user1@example.com");
		Authorities auth1 = new Authorities();
		auth1.setAuthority("USER");
		user1.setAuthority(auth1);

		User user2 = new User();
		user2.setId(2);
		user2.setUsername("User2");
		user2.setEmail("user2@example.com");
		Authorities auth2 = new Authorities();
		auth2.setAuthority("USER");
		user2.setAuthority(auth2);

		Page<User> mockPage = new PageImpl<>(List.of(user1, user2));

		when(userService.findCurrentUser()).thenReturn(user); // Usuario actual es ADMIN
		when(userService.findAll(any(Pageable.class))).thenReturn(mockPage);

		mockMvc.perform(get(BASE_URL)
				.param("page", "0")
				.param("size", "5")
				.param("sortBy", "username")
				.param("sortDirection", "asc")
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content.size()").value(2))
			.andExpect(jsonPath("$.content[0].username").value("User1"))
			.andExpect(jsonPath("$.content[1].username").value("User2"));

		verify(userService).findAll(any(Pageable.class));
	}

	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void shouldReturnForbiddenWhenNotAdmin() throws Exception {
		
		Authorities userAuth = new Authorities();
		userAuth.setAuthority("USER");
		
		User nonAdminUser = new User();
		nonAdminUser.setId(1);
		nonAdminUser.setUsername("user");
		nonAdminUser.setAuthority(userAuth);
	
		// Devolvemos un user no-admin
		when(userService.findCurrentUser()).thenReturn(nonAdminUser);
	
		mockMvc.perform(get(BASE_URL)
				.param("page", "0")
				.param("size", "5")
				.param("sortBy", "username")
				.param("sortDirection", "asc")
				.with(csrf()))
			.andExpect(status().isForbidden());
	
		verify(userService, never()).findAll(any(Pageable.class));
	}	

	@Test
	@WithMockUser(username = "regular", roles = { "USER" })
	void shouldReturnForbiddenWhenUpdatingAnotherUser() throws Exception {
		UserUpdateProfileDTO userDto = new UserUpdateProfileDTO();
		userDto.setUsername("UpdatedUser");

		Authorities userAuth = new Authorities();
		userAuth.setAuthority("USER");

		User nonAdminUser = new User();
		nonAdminUser.setId(99);
		nonAdminUser.setUsername("regular");
		nonAdminUser.setAuthority(userAuth);

		when(userService.findCurrentUser()).thenReturn(nonAdminUser);

		User targetUser = new User();
		targetUser.setId(TEST_USER_ID);
		targetUser.setUsername("anotherUser");
		Authorities targetAuth = new Authorities();
		targetAuth.setAuthority("USER");
		targetUser.setAuthority(targetAuth);

		when(userService.findUser(TEST_USER_ID)).thenReturn(targetUser);

		// Al no ser admin ni el ID propio, el controlador => 403
		mockMvc.perform(put(BASE_URL + "/{userId}", TEST_USER_ID)
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDto)))
			.andExpect(status().isForbidden());

		verify(userService, never()).updateUser(any(UserUpdateProfileDTO.class), anyInt());
	}


	@Test
	@WithMockUser(username = "user", roles = { "USER" })
	void shouldReturnForbiddenWhenDeletingAnotherUser() throws Exception {
		when(userService.findCurrentUser()).thenReturn(user); // Usuario no es ADMIN
		when(userService.findUser(TEST_USER_ID)).thenReturn(user);

		mockMvc.perform(delete(BASE_URL + "/{userId}", TEST_USER_ID)
				.with(csrf()))
			.andExpect(status().isForbidden());

		verify(userService, never()).deleteUser(TEST_USER_ID);
	}

	@Test
	@WithMockUser(username = "admin", roles = { "ADMIN" })
	void shouldDeleteUserSuccessfully() throws Exception {
		
		Authorities adminAuth = new Authorities();
		adminAuth.setAuthority("ADMIN");
		User adminUser = new User();
		adminUser.setId(99);
		adminUser.setUsername("admin");
		adminUser.setAuthority(adminAuth);

		when(userService.findCurrentUser()).thenReturn(adminUser);

		// user que se desea borrar (id=1)
		User targetUser = new User();
		targetUser.setId(TEST_USER_ID);
		targetUser.setUsername("toDelete");
		Authorities targetAuth = new Authorities();
		targetAuth.setAuthority("USER");
		targetUser.setAuthority(targetAuth);

		when(userService.findUser(TEST_USER_ID)).thenReturn(targetUser);

		// El controlador borra las partidas creadas:
		when(playerService.getMatchesCreatedByUser(TEST_USER_ID)).thenReturn(Collections.emptyList());

		doNothing().when(userService).deleteUser(TEST_USER_ID);

		mockMvc.perform(delete(BASE_URL + "/{userId}", TEST_USER_ID)
				.with(csrf()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value("User deleted!"));

		verify(userService).deleteUser(TEST_USER_ID);
		verify(playerService).getMatchesCreatedByUser(TEST_USER_ID);
		verify(matchService, never()).deleteMatch(anyInt(), anyInt());
	}

}
