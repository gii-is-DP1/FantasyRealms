package es.us.dp1.l2_05_24_25.fantasy_realms.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(CurrentRestController.class)
class CurrentRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; 

    @Test
    void testGetCurrentUserUnauthorized() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currentuser"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetCurrentUserServerError() throws Exception {

        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken("testUsername", null, new ArrayList<>());
        SecurityContext securityContext = org.mockito.Mockito.mock(SecurityContext.class);
        org.mockito.Mockito.when(securityContext.getAuthentication()).thenReturn(principal);
        SecurityContextHolder.setContext(securityContext);

        when(userService.findUser("testUsername")).thenThrow(new RuntimeException("Error retrieving user"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currentuser")
                .principal(principal))
                .andExpect(status().isInternalServerError());
    }

}