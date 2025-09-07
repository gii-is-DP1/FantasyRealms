package es.us.dp1.l2_05_24_25.fantasy_realms.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.UserUpdateProfileDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.AuthoritiesService;

//@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@SpringBootTest
@AutoConfigureTestDatabase
class UserServiceTests {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthoritiesService authService;


	// Mockear para que cargue el contexto correctamente. Si no da fallo al cargar WebSocket
	@MockBean
    private ServerEndpointExporter serverEndpointExporter;

	// Si esta prueba se ejecuta de forma individual, da 17, por eso puede fallar.

	@Test
    @Transactional
    void shouldFindAllUsers() {
        List<User> users = this.userService.findAll(Pageable.unpaged()).getContent();
        assertEquals(18, users.size());
    }

	@Test
	@WithMockUser(username = "user1", password = "0wn3r")
	void shouldFindCurrentUser() {
		User user = this.userService.findCurrentUser();
		assertEquals("user1", user.getUsername());
	}

	@Test
	@WithMockUser(username = "prueba")
	void shouldNotFindCorrectCurrentUser() {
		assertThrows(ResourceNotFoundException.class, () -> this.userService.findCurrentUser());
	}

	@Test
	void shouldNotFindAuthenticated() {
		assertThrows(ResourceNotFoundException.class, () -> this.userService.findCurrentUser());
	}

	@Test
	void shouldFindUsersByUsername() {
		User user = this.userService.findUser("user1");
		assertEquals("user1", user.getUsername());
	}

	@Test
    void shouldFindUsersByAuthority() {
        Page<User> players = this.userService.findAllByAuthority("PLAYER", Pageable.unpaged());
        assertEquals(16, players.getContent().size());

        Page<User> admins = this.userService.findAllByAuthority("ADMIN", Pageable.unpaged());
        assertEquals(1, admins.getContent().size());

        Page<User> vets = this.userService.findAllByAuthority("VET", Pageable.unpaged());
        assertEquals(0, vets.getContent().size());
    }

	@Test
	void shouldNotFindUserByIncorrectUsername() {
		assertThrows(ResourceNotFoundException.class, () -> this.userService.findUser("usernotexists"));
	}	


	@Test
	void shouldFindSingleUser() {
		User user = this.userService.findUser(4);
		assertEquals("user1", user.getUsername());
	}


	@Test
	void shouldNotFindSingleUserWithBadID() {
		assertThrows(ResourceNotFoundException.class, () -> this.userService.findUser(100));
	}

	@Test
	void shouldExistUser() {
		assertEquals(true, this.userService.existsUser("user1"));
	}

	@Test
	void shouldNotExistUser() {
		assertEquals(false, this.userService.existsUser("player10000"));
	}

	@Test
	void shouldUpdateUserPassword() {
		int idToUpdate = 1;
		String newPassword = "newPassword";

		UserUpdateProfileDTO userDTO = new UserUpdateProfileDTO();
		userDTO.setPassword(newPassword);

		userService.updateUser(userDTO, idToUpdate);

		User user = this.userService.findUser(idToUpdate);
		assertEquals(newPassword, user.getPassword());
	}

	@Test
	void shouldUpdateUserEmail() {
		int idToUpdate = 1;
		String newEmail = "newemail@example.com";

		UserUpdateProfileDTO userDTO = new UserUpdateProfileDTO();
		userDTO.setEmail(newEmail);

		userService.updateUser(userDTO, idToUpdate);

		User user = this.userService.findUser(idToUpdate);
		assertEquals(newEmail, user.getEmail());
	}

	@Test
	void shouldUpdateUserAvatar() {
		int idToUpdate = 1;
		String newAvatar = "new-avatar.png";

		UserUpdateProfileDTO userDTO = new UserUpdateProfileDTO();
		userDTO.setAvatar(newAvatar);

		userService.updateUser(userDTO, idToUpdate);

		User user = this.userService.findUser(idToUpdate);
		assertEquals(newAvatar, user.getAvatar());
	}

	@Test
	void shouldUpdateUserAuthority() {
		int idToUpdate = 1;
		String newAuthority = "ADMIN";

		UserUpdateProfileDTO userDTO = new UserUpdateProfileDTO();
		userDTO.setAuthority(newAuthority);

		userService.updateUser(userDTO, idToUpdate);

		User user = this.userService.findUser(idToUpdate);
		assertEquals(newAuthority, user.getAuthority().getAuthority());
	}

	@Test
	void shouldUpdateAllFields() {
		int idToUpdate = 1;
		String newName = "UpdatedName";
		String newPassword = "UpdatedPassword";
		String newEmail = "updatedemail@example.com";
		String newAvatar = "updated-avatar.png";
		String newAuthority = "ADMIN";

		UserUpdateProfileDTO userDTO = new UserUpdateProfileDTO();
		userDTO.setUsername(newName);
		userDTO.setPassword(newPassword);
		userDTO.setEmail(newEmail);
		userDTO.setAvatar(newAvatar);
		userDTO.setAuthority(newAuthority);

		userService.updateUser(userDTO, idToUpdate);

		User user = this.userService.findUser(idToUpdate);
		assertEquals(newName, user.getUsername());
		assertEquals(newPassword, user.getPassword());
		assertEquals(newEmail, user.getEmail());
		assertEquals(newAvatar, user.getAvatar());
		assertEquals(newAuthority, user.getAuthority().getAuthority());
	}

	@Test
	void shouldNotUpdateAnyField() {
		int idToUpdate = 1;

		UserUpdateProfileDTO userDTO = new UserUpdateProfileDTO();

		User userBeforeUpdate = this.userService.findUser(idToUpdate);

		userService.updateUser(userDTO, idToUpdate);

		User userAfterUpdate = this.userService.findUser(idToUpdate);

		assertEquals(userBeforeUpdate.getUsername(), userAfterUpdate.getUsername());
		assertEquals(userBeforeUpdate.getPassword(), userAfterUpdate.getPassword());
		assertEquals(userBeforeUpdate.getEmail(), userAfterUpdate.getEmail());
		assertEquals(userBeforeUpdate.getAvatar(), userAfterUpdate.getAvatar());
		assertEquals(userBeforeUpdate.getAuthority().getAuthority(), userAfterUpdate.getAuthority().getAuthority());
	}

	@Test
    void shouldUpdateUserName() {
        int idToUpdate = 1;
        String newName = "Change";

        UserUpdateProfileDTO userDTO = new UserUpdateProfileDTO();
        userDTO.setUsername(newName);

        userService.updateUser(userDTO, idToUpdate);

        User user = this.userService.findUser(idToUpdate);
        assertEquals(newName, user.getUsername());
    }

	@Test
    void shouldInsertUser() {
        long count = this.userService.findAll(Pageable.unpaged()).getTotalElements();

        User user = new User();
        user.setUsername("Sam");
        user.setPassword("password");
        user.setAuthority(authService.findByAuthority("PLAYER"));
        user.setAchievements(new ArrayList<>());

        this.userService.saveUserBasic(user);

        long finalCount = this.userService.findAll(Pageable.unpaged()).getTotalElements();
        assertEquals(count + 1, finalCount);
    }

    @Test
    void shouldSaveUserWithAuthority() {
        
        UserUpdateProfileDTO userDto = new UserUpdateProfileDTO();
        userDto.setUsername("testUserWithAuthority");
        userDto.setPassword("testPassword");
        userDto.setEmail("authority@example.com");
        userDto.setAvatar("avatar.png");
        userDto.setAuthority("PLAYER");

        
        User savedUser = userService.saveUser(userDto);

        
        assertNotNull(savedUser.getId());
        assertEquals("testUserWithAuthority", savedUser.getUsername());
        assertNotNull(savedUser.getAuthority());
        assertEquals("PLAYER", savedUser.getAuthority().getAuthority());
    }

}
