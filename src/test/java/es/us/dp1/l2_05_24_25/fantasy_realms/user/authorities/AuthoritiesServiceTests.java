package es.us.dp1.l2_05_24_25.fantasy_realms.user.authorities;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import es.us.dp1.l2_05_24_25.fantasy_realms.exceptions.ResourceNotFoundException;


class AuthoritiesServiceTests {
	
	@Mock
    private AuthoritiesRepository authoritiesRepository;

    @InjectMocks
    private AuthoritiesService authoritiesService;

    private Authorities authority;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authority = new Authorities();
        authority.setAuthority("ROLE_USER");
    }

    @Test
    void testFindAll() {

        Iterable<Authorities> authoritiesList = mock(Iterable.class);
        when(authoritiesRepository.findAll()).thenReturn(authoritiesList);

        Iterable<Authorities> result = authoritiesService.findAll();

        assertNotNull(result);
        verify(authoritiesRepository, times(1)).findAll();
    }

    @Test
    void testFindByAuthorityFound() {

        String authorityName = "ROLE_USER";
        when(authoritiesRepository.findByName(authorityName)).thenReturn(java.util.Optional.of(authority));

        Authorities result = authoritiesService.findByAuthority(authorityName);

        assertNotNull(result);
        assertEquals("ROLE_USER", result.getAuthority());
        verify(authoritiesRepository, times(1)).findByName(authorityName);
    }

    @Test
    void testFindByAuthorityNotFound() {

        String authorityName = "ROLE_ADMIN";
        when(authoritiesRepository.findByName(authorityName)).thenReturn(java.util.Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, 
            () -> authoritiesService.findByAuthority(authorityName));
        assertEquals("Authority not found with Name: 'ROLE_ADMIN'", exception.getMessage());
        verify(authoritiesRepository, times(1)).findByName(authorityName);
    }

	@Test
	void testSaveAuthorities() throws DataAccessException {

		authoritiesService.saveAuthorities(authority);
	
		verify(authoritiesRepository, times(1)).save(authority);
	}

    @Test
    void testSaveAuthoritiesFailed() {

        doThrow(new DataIntegrityViolationException("Test exception")).when(authoritiesRepository).save(any(Authorities.class));

        assertThrows(DataIntegrityViolationException.class, () -> authoritiesService.saveAuthorities(authority));
    }

}
