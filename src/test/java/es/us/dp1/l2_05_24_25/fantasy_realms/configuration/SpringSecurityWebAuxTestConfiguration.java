package es.us.dp1.l2_05_24_25.fantasy_realms.configuration;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import es.us.dp1.l2_05_24_25.fantasy_realms.configuration.services.UserDetailsImpl;

@TestConfiguration
public class SpringSecurityWebAuxTestConfiguration {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        // Creación de objetos UserDetailsImpl considerando los nuevos atributos

        UserDetailsImpl ownerActiveUser = new UserDetailsImpl(
            1,
            "owner",
            "password",
            Arrays.asList(new SimpleGrantedAuthority("OWNER")),
            "ownerAvatar.png",
            "owner@example.com",
            new ArrayList<>()
        );

        UserDetailsImpl adminActiveUser = new UserDetailsImpl(
            2,
            "admin",
            "password",
            Arrays.asList(new SimpleGrantedAuthority("ADMIN")),
            "adminAvatar.png",
            "admin@example.com",
            new ArrayList<>()
        );

        UserDetailsImpl vetActiveUser = new UserDetailsImpl(
            3,
            "vet",
            "password",
            Arrays.asList(new SimpleGrantedAuthority("VET")),
            "vetAvatar.png",
            "vet@example.com",
            new ArrayList<>()
        );

        // Crear InMemoryUserDetailsManager con los usuarios definidos
        return new InMemoryUserDetailsManager(Arrays.asList(
            ownerActiveUser, adminActiveUser, vetActiveUser
        ));
    }
}
