package es.us.dp1.l2_05_24_25.fantasy_realms.match;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import es.us.dp1.l2_05_24_25.fantasy_realms.deck.Deck;
import es.us.dp1.l2_05_24_25.fantasy_realms.discard.Discard;
import es.us.dp1.l2_05_24_25.fantasy_realms.dto.MatchDTO;
import es.us.dp1.l2_05_24_25.fantasy_realms.modules.social.user.friendship.FriendshipService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.Player;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerService;
import es.us.dp1.l2_05_24_25.fantasy_realms.player.PlayerType;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.User;
import es.us.dp1.l2_05_24_25.fantasy_realms.user.UserService;


@WebMvcTest(controllers = MatchController.class)
public class MatchControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @MockBean
    private UserService userService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private FriendshipService friendshipService;

    @MockBean
    private MatchRepository matchRepository;

    private Match match1;
    private Match match2;
    private User u1;

    @BeforeEach
    public void setUp() {

        // Crear el usuario y la partida para pruebas
        u1 = spy(new User());
        u1.setId(1);
        u1.setUsername("user1");

        // Creamos jugador
        Player player1 = new Player(u1, PlayerType.CREADOR);

        // Crear partidas de prueba

        match1 = new Match();
        match1.setId(1);
        match1.setStartDate(LocalDateTime.now());
        match1.setEndDate(null); // Partida no terminada
        match1.setPlayers(List.of(player1));
        match1.setDeck(new Deck());
        match1.setDiscard(new Discard());

        match2 = new Match();
        match2.setId(2);
        match2.setStartDate(LocalDateTime.now().minusDays(1));
        match2.setEndDate(LocalDateTime.now());
        match2.setPlayers(List.of(player1));
        match2.setDeck(new Deck());
        match2.setDiscard(new Discard());
    }
    
    // Caso 1: Llamada por un usuario con rol de jugador (no admin)
 
    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testFindMatchesByStatus_Lobby() throws Exception {

        // Crear datos de prueba
        MatchDTO matchDTO = new MatchDTO(match1);
        List<MatchDTO> matchDTOList = List.of(matchDTO);
        Page<MatchDTO> matchDTOPage = new PageImpl<>(matchDTOList, PageRequest.of(0, 10), 1);

        // Configurar el mock para devolver los datos de prueba
        when(matchService.findMatchesByStatus(0, 10, "lobby")).thenReturn(matchDTOPage);
        when(userService.findCurrentUser()).thenReturn(u1);
        doReturn(false).when(u1).hasAuthority("ADMIN");

        // Realizar la petición GET al controlador
        mockMvc.perform(get("/api/v1/matches")
                .param("page", "0")
                .param("size", "10")
                .param("status", "lobby")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(matchDTO.getId()))
                .andExpect(jsonPath("$.content[0].name").value(matchDTO.getName()));

        // Verificar que se llamó al método del servicio con los parámetros correctos
        verify(matchService, times(1)).findMatchesByStatus(0, 10, "lobby");
    }

    // Caso 2: Para partidas en progreso llamada por ADMIN -> no debe dar excepción

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void testFindMatchesByStatus_InProgress() throws Exception {
        // Crear datos de prueba
        MatchDTO matchDTO = new MatchDTO(match1);
        List<MatchDTO> matchDTOList = List.of(matchDTO);
        Page<MatchDTO> matchDTOPage = new PageImpl<>(matchDTOList, PageRequest.of(0, 10), 1);

        // Configurar el mock para devolver los datos de prueba
        when(matchService.findMatchesByStatus(0, 10, "inProgress")).thenReturn(matchDTOPage);
        when(userService.findCurrentUser()).thenReturn(u1);
        doReturn(true).when(u1).hasAuthority("ADMIN");

        // Realizar la petición GET al controlador
        mockMvc.perform(get("/api/v1/matches")
                .param("page", "0")
                .param("size", "10")
                .param("status", "inProgress")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(matchDTO.getId()))
                .andExpect(jsonPath("$.content[0].name").value(matchDTO.getName()));

        // Verificar que se llamó al método del servicio con los parámetros correctos
        verify(matchService, times(1)).findMatchesByStatus(0, 10, "inProgress");
    }

    // Caso 3: Para partidas finalizadas llamada por ADMIN -> no debe dar excepción

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    public void testFindMatchesByStatus_Finished() throws Exception {
        // Crear datos de prueba
        MatchDTO matchDTO = new MatchDTO(match1);
        List<MatchDTO> matchDTOList = List.of(matchDTO);
        Page<MatchDTO> matchDTOPage = new PageImpl<>(matchDTOList, PageRequest.of(0, 10), 1);

        // Configurar el mock para devolver los datos de prueba
        when(matchService.findMatchesByStatus(0, 10, "finished")).thenReturn(matchDTOPage);
        when(userService.findCurrentUser()).thenReturn(u1);
        doReturn(true).when(u1).hasAuthority("ADMIN");

        // Realizar la petición GET al controlador
        mockMvc.perform(get("/api/v1/matches")
                .param("page", "0")
                .param("size", "10")
                .param("status", "finished")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(matchDTO.getId()))
                .andExpect(jsonPath("$.content[0].name").value(matchDTO.getName()));

        // Verificar que se llamó al método del servicio con los parámetros correctos
        verify(matchService, times(1)).findMatchesByStatus(0, 10, "finished");
    }

    // Caso 4: Llamada a un método que no sea lobby por un usuario que no es admin -> debe dar excepción

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testFindMatchesByStatus_InvalidStatus() throws Exception {

        // Configurar el mock para devolver los datos de prueba
        when(userService.findCurrentUser()).thenReturn(u1);
        doReturn(false).when(u1).hasAuthority("ADMIN");

        // Realizar la petición GET al controlador
        mockMvc.perform(get("/api/v1/matches")
                .param("page", "0")
                .param("size", "10")
                .param("status", "inProgress")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        // Verificar que no se llamó al método del servicio con los parámetros correctos
        verify(matchService, times(0)).findMatchesByStatus(0, 10, "invalid");
    }


   @Test
   @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testFindMatchById() throws Exception {
        // Crear un objeto Match
        Match match = new Match();
        match.setId(1);
        match.setStartDate(LocalDateTime.now());

        // Crear un usuario y un jugador con el rol de CREADOR
        User user = new User();
        user.setId(1);
        user.setUsername("creator");

        Player creatorPlayer = new Player(user, PlayerType.CREADOR);
        creatorPlayer.setPlayerHand(Collections.emptyList());

        // Añadir el jugador al match
        match.setPlayers(new ArrayList<>());
        match.addPlayer(creatorPlayer);

        // Asignar un Discard no nulo para evitar NullPointerException
        Discard discard = new Discard();
        discard.setId(1); // Asegúrate de que el Discard tenga un ID válido
        match.setDiscard(discard);

        // Asignar un Deck no nulo para evitar NullPointerException
        Deck deck = new Deck();
        deck.setId(1); // Asegúrate de que el Deck tenga un ID válido
        match.setDeck(deck);

        // Crear MatchDTO
        MatchDTO matchDTO = new MatchDTO(match);

        // Configurar el mock para devolver el match cuando se busca por ID
        when(matchService.findMatchById(1)).thenReturn(match);

        // Realizar la petición GET al controlador
        mockMvc.perform(get("/api/v1/matches/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Esperamos una respuesta 200 OK
                .andExpect(jsonPath("$.id").value(matchDTO.getId()))  // Comprobamos el ID del match
                .andExpect(jsonPath("$.name").value(matchDTO.getName()))  // Comprobamos el nombre del match
                .andExpect(jsonPath("$.players[0].username").value(matchDTO.getPlayers().get(0).getUsername()));  // Verificamos el jugador

        // Verificar que se llamó al método del servicio con el ID correcto
        verify(matchService, times(1)).findMatchById(1);
    }


    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testCreateMatch() throws Exception {

        // Configuramos la partida creada
        Player creator = new Player(u1, PlayerType.CREADOR);
        Match matchCreate = new Match("Partida1", new Deck(), new Discard(), List.of(creator));
        matchCreate.setStartDate(LocalDateTime.now()); // Configuramos la fecha de inicio

        // Configuramos el comportamiento de los mocks
        when(userService.findCurrentUser()).thenReturn(u1);
        when(matchService.createMatch(eq(u1), eq("Partida1"))).thenReturn(matchCreate);

        // Realizamos la solicitud POST para crear una partida
        mockMvc.perform(post("/api/v1/matches/create")
                .param("matchName", "Partida1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()) // Aseguramos que el status sea 201 Created
                .andExpect(jsonPath("$.name").value("Partida1")) // Validar que el nombre de la partida es el esperado
                .andExpect(jsonPath("$.startDate").isNotEmpty()) // Validar que la fecha de inicio no está vacía
                .andExpect(jsonPath("$.players").isArray()) // Validar que hay un array de jugadores
                .andExpect(jsonPath("$.players.length()").value(1)) // Validar que hay un jugador
                .andExpect(jsonPath("$.players[0].username").value("user1")); // Validar que el primer jugador es "user1"

        // Verificar que el servicio fue llamado una vez con los parámetros esperados
        verify(matchService, times(1)).createMatch(eq(u1), eq("Partida1"));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testJoinMatch() throws Exception {
        // Preparar datos de prueba
        User u1 = new User();
        u1.setUsername("user1");
    
        Deck deck = new Deck();
        deck.setId(1);  // Simular un deck con ID válido
    
        Discard discard = new Discard();
        discard.setId(2);  // Simular un discard válido
    
        Match match = new Match();
        match.setId(1);
        match.setDeck(deck);
        match.setDiscard(discard);
    
        Player player1 = new Player(u1, PlayerType.CREADOR);
        match.setPlayers(List.of(player1));
    
        // Mockear servicios
        when(userService.findCurrentUser()).thenReturn(u1);
        when(matchService.joinMatch(1, u1)).thenReturn(match);
    
        // Realizar la solicitud y verificar la respuesta
        mockMvc.perform(post("/api/v1/matches/1/join")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players.length()").value(1))
                .andExpect(jsonPath("$.players[0].username").value("user1"))
                .andExpect(jsonPath("$.deckId").value(1));
    
        // Verificar interacción con el servicio
        verify(matchService, times(1)).joinMatch(1, u1);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testStartMatch_Success() throws Exception {
        // Configurar datos de prueba
        MatchDTO matchDTO = new MatchDTO(match1);
        when(userService.findCurrentUser()).thenReturn(u1);
        when(matchService.startMatch(match1.getId(), u1.getId())).thenReturn(match1);

        // Realizar la solicitud POST al endpoint
        mockMvc.perform(post("/api/v1/matches/{id}/start", match1.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verificar que el estado HTTP sea 200 OK
                .andExpect(jsonPath("$.id").value(matchDTO.getId())) // Verificar el ID de la partida
                .andExpect(jsonPath("$.name").value(matchDTO.getName())); // Verificar el nombre de la partida

        // Verificar interacciones con los servicios
        verify(userService, times(1)).findCurrentUser();
        verify(matchService, times(1)).startMatch(match1.getId(), u1.getId());
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testDeleteMatch() throws Exception {
        when(userService.findCurrentUser()).thenReturn(u1);
        doNothing().when(matchService).deleteMatch(eq(1), eq(1));

        mockMvc.perform(delete("/api/v1/matches/1/delete")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(matchService, times(1)).deleteMatch(1, u1.getId());
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testFindCreatorOfMatch() throws Exception {
        // Preparar datos de prueba
        Integer matchId = 1;
        User creatorUser = new User();
        creatorUser.setId(1);
        creatorUser.setUsername("creatorUser");
    
        Player creatorPlayer = new Player(creatorUser, PlayerType.CREADOR);
        creatorPlayer.setUser(creatorUser); // Asociar el jugador al usuario
    
        Match match = new Match();
        match.setId(matchId);
        match.setPlayers(new ArrayList<>());
        match.addPlayer(creatorPlayer); // Asignar el creador a la partida
    
        // Mockear el servicio
        when(matchService.findMatchById(matchId)).thenReturn(match);
    
        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/matches/{id}/creator", matchId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    
        // Verificar la interacción con el servicio
        verify(matchService, times(1)).findMatchById(matchId);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testFindMatchesForCurrentUser() throws Exception {

        // Crear datos de prueba
        MatchDTO matchDTO1 = new MatchDTO(match1);
        MatchDTO matchDTO2 = new MatchDTO(match2);
        List<MatchDTO> matchDTOList = List.of(matchDTO1, matchDTO2);
        Page<MatchDTO> matchDTOPage = new PageImpl<>(matchDTOList, PageRequest.of(0, 10), 2);

        // Configurar el mock para devolver los datos de prueba
        when(userService.findCurrentUser()).thenReturn(u1);
        when(matchService.findMatchesForUser(eq(u1.getId()), eq(0), eq(10), eq(false))).thenReturn(matchDTOPage);

        // Realizar la solicitud GET y verificar la respuesta
        mockMvc.perform(get("/api/v1/matches/myMatches")
                .param("page", "0")
                .param("size", "10")
                .param("onlyCreator", "false")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Verificar que el estado de la respuesta sea 200 
                .andExpect(jsonPath("$.content.length()").value(2)) // Verificar que se devuelvan 2 partidas
                .andExpect(jsonPath("$.content[0].id").value(1)) // Verificar el ID de la primera partida
                .andExpect(jsonPath("$.content[1].id").value(2)); // Verificar el ID de la segunda partida

        // Verificar la interacción con el servicio
        verify(userService, times(1)).findCurrentUser();
        verify(matchService, times(1)).findMatchesForUser(eq(u1.getId()), eq(0), eq(10), eq(false));
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testToggleRole() throws Exception {
        Integer matchId = 1;
        User currentUser = new User();
        currentUser.setId(1);
        currentUser.setUsername("user1");

        when(userService.findCurrentUser()).thenReturn(currentUser);
        doNothing().when(matchService).togglePlayerRole(matchId, currentUser);

        mockMvc.perform(post("/api/v1/matches/{matchId}/toggle-role", matchId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(matchService, times(1)).togglePlayerRole(matchId, currentUser);
    }

    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testRemovePlayer() throws Exception {

        Integer matchId = 1;
        Integer userIdToRemove = 2;

        User currentUser = new User();
        currentUser.setId(1);
        currentUser.setUsername("user1");

        User userToRemove = new User();
        userToRemove.setId(userIdToRemove);
        userToRemove.setUsername("user2");

        Player creator = new Player(currentUser, PlayerType.CREADOR);
        Player playerToRemove = new Player(userToRemove, PlayerType.PARTICIPANTE);

        Match match = new Match("Partida1", new Deck(), new Discard(), List.of(creator, playerToRemove));
        match.setId(matchId);
        match.setStartDate(LocalDateTime.now());

        when(userService.findCurrentUser()).thenReturn(currentUser);
        when(matchService.removePlayerFromMatch(eq(matchId), eq(currentUser.getId()), eq(userIdToRemove)))
                .thenReturn(match);

        mockMvc.perform(delete("/api/v1/matches/{matchId}/remove-player/{userIdToRemove}", matchId, userIdToRemove)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Partida1"))
                .andExpect(jsonPath("$.id").value(matchId));

        verify(matchService, times(1)).removePlayerFromMatch(eq(matchId), eq(currentUser.getId()), eq(userIdToRemove));
    }


    @Test
    @WithMockUser(username = "user", roles = { "PLAYER" })
    public void testGetPlayerRole() throws Exception {
        Integer matchId = 1;

        User currentUser = new User();
        currentUser.setUsername("user1");

        when(userService.findCurrentUser()).thenReturn(currentUser);
        when(playerService.findRoleByMatchIdAndUsername(eq(matchId), eq("user1")))
                .thenReturn(PlayerType.CREADOR);

        mockMvc.perform(get("/api/v1/matches/{id}/player-role", matchId)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PlayerType.CREADOR.toString()));

        verify(playerService, times(1)).findRoleByMatchIdAndUsername(matchId, "user1");
    }

}
