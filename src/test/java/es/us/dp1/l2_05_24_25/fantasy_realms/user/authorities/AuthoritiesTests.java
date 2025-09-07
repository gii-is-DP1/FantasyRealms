package es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
public class AuthoritiesTests {

    @Test
    void testParameterizedConstructor() {
        // Crear un usuario de prueba
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");

        // Probar el constructor con parámetros
        Authorities authorities = new Authorities(user, "ADMIN");

        // Verificar los valores
        assertEquals(user, authorities.getUser());
        assertEquals("ADMIN", authorities.getAuthority());
    }
    
}
