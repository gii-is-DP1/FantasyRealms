package es.us.dp1.l2_05_24_25.fantasy_realms.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.UserUpdateProfileDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities.Authorities;

public class UserTests {
    
    private User user;
    private Authorities authority;
    private UserUpdateProfileDTO userUpdateProfileDTO;

    @BeforeEach
    void setUp() {
        authority = new Authorities();
        authority.setAuthority("PLAYER");

        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setAvatar("avatar.png");
        user.setEmail("test@example.com");
        user.setAchievements(Collections.emptyList());
        user.setAuthority(authority);
        user.setPlayers(Collections.emptySet());

        userUpdateProfileDTO = new UserUpdateProfileDTO(
            "updatedUser",
            "updatedPassword",
            "updated@example.com",
            "updatedAvatar.png",
            "PLAYER"
        );
    }

    @Test
    void testHasAnyAuthority_SingleMatch() {
        assertTrue(user.hasAnyAuthority("PLAYER"));
    }

    @Test
    void testHasAnyAuthority_MultipleMatch() {
        assertTrue(user.hasAnyAuthority("ADMIN", "PLAYER", "MODERATOR"));
    }

    @Test
    void testHasAnyAuthority_NoMatch() {
        assertFalse(user.hasAnyAuthority("ADMIN", "MODERATOR"));
    }

    @Test
    void testConstructorWithAllFields() {
        User userWithConstructor = new User(
            "constructorUser",
            "constructorPassword",
            "constructorAvatar.png",
            "constructor@example.com",
            Collections.emptyList(),
            authority,
            Collections.emptySet()
        );

        assertNotNull(userWithConstructor);
        assertEquals("constructorUser", userWithConstructor.getUsername());
        assertEquals("constructorPassword", userWithConstructor.getPassword());
        assertEquals("constructorAvatar.png", userWithConstructor.getAvatar());
        assertEquals("constructor@example.com", userWithConstructor.getEmail());
        assertEquals(authority, userWithConstructor.getAuthority());
        assertTrue(userWithConstructor.getAchievements().isEmpty());
        assertTrue(userWithConstructor.getPlayers().isEmpty());
    }

    @Test
    void testConstructorWithDTO() {
        User userWithDTO = new User(userUpdateProfileDTO);

        assertNotNull(userWithDTO);
        assertEquals("updatedUser", userWithDTO.getUsername());
        assertEquals("updatedPassword", userWithDTO.getPassword());
        assertEquals("updatedAvatar.png", userWithDTO.getAvatar());
        assertEquals("updated@example.com", userWithDTO.getEmail());
    }
}
