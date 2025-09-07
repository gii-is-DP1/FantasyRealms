package es.us.dp1.l2_05_24_25.fantasy_realms.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.dto.UserUpdateProfileDTO;

public class UserUpdateProfileDTOTests {
    
    private UserUpdateProfileDTO userUpdateProfileDTO;

    @BeforeEach
    void setUp() {
        // Crear un objeto UserUpdateProfileDTO con datos de prueba
        userUpdateProfileDTO = new UserUpdateProfileDTO(
            "player1", "password123", "player1@example.com", "avatar.png", "USER"
        );
    }

    @Test
    void testConstructor() {
        // Verificar que el objeto se crea correctamente
        assertNotNull(userUpdateProfileDTO);
        assertEquals("player1", userUpdateProfileDTO.getUsername());
        assertEquals("password123", userUpdateProfileDTO.getPassword());
        assertEquals("player1@example.com", userUpdateProfileDTO.getEmail());
        assertEquals("avatar.png", userUpdateProfileDTO.getAvatar());
        assertEquals("USER", userUpdateProfileDTO.getAuthority());
    }

    @Test
    void testSettersAndGetters() {
        // Verificar que los setters y getters funcionan correctamente
        
        // Modificar los valores de los campos
        userUpdateProfileDTO.setUsername("newPlayer");
        userUpdateProfileDTO.setPassword("newPassword123");
        userUpdateProfileDTO.setEmail("newplayer@example.com");
        userUpdateProfileDTO.setAvatar("newAvatar.png");
        userUpdateProfileDTO.setAuthority("ADMIN");

        // Verificar que los valores se han actualizado
        assertEquals("newPlayer", userUpdateProfileDTO.getUsername());
        assertEquals("newPassword123", userUpdateProfileDTO.getPassword());
        assertEquals("newplayer@example.com", userUpdateProfileDTO.getEmail());
        assertEquals("newAvatar.png", userUpdateProfileDTO.getAvatar());
        assertEquals("ADMIN", userUpdateProfileDTO.getAuthority());
    }
}
