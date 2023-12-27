//package SignIn.backend;
//
//// Replace with your package name
//
//import SignIn.backend.Model.Player;
//import SignIn.backend.Model.Pokemon;
//import SignIn.backend.Repository.PlayerRepository;
//import SignIn.backend.Repository.PokemonRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import net.minidev.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class BackendApplicationTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
////    @Test
////    public void contextLoads() {
////        // Basic test to check context loading
////    }
////
////    @Test
////    public void testExampleEndpoint() throws Exception {
////        // Replace "/example-endpoint" with your actual endpoint
////        mockMvc.perform(get("/example-endpoint"))
////                .andExpect(status().isOk());
////        // Add additional assertions as needed
////    }
//
////    @Autowired
////    private MockMvc mockMvc;
//
//    @Autowired
//    private PlayerRepository playerRepository;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setup() {
//        // Initialize your test environment (e.g., create a test player in the database)
//        Player testPlayer = new Player("TestUser", "test@example.com", "password123");
//        playerRepository.save(testPlayer);
//    }
//
//    @Test
//    public void testAuthenticateUser() throws Exception {
//        JSONObject creds = new JSONObject();
//        creds.put("email", "test@example.com");
//        creds.put("password", "password123");
//
//        mockMvc.perform(post("/signin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(creds.toString()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.access").value("true,1"));
//    }
//
//    @Test
//    public void testCreateUser() throws Exception {
//        Player newPlayer = new Player("NewPlayer", "newplayer@example.com", "newpassword");
//        String json = objectMapper.writeValueAsString(newPlayer);
//
//        mockMvc.perform(post("/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("NewPlayer"))
//                .andExpect(jsonPath("$.email").value("newplayer@example.com"));
//    }
//
//    @Autowired
//    private PokemonRepository pokemonRepository;
//
//
//
//
//
//
//    @Test
//    public void testAddRandomPokemonToDatabase() throws Exception {
//        // Create a random species and nickname
//        String randomSpecies = "Species_" + UUID.randomUUID().toString();
//        String randomNickname = "Nickname_" + UUID.randomUUID().toString();
//
//        // Create a Pokemon instance with random values
//        Pokemon pokemon = new Pokemon();
//        pokemon.setSpecies(randomSpecies);
//        pokemon.setNickName(randomNickname);
//        pokemon.setLevel(10);
//        // Set other attributes as needed
//
//        // Save the Pokemon to the database
//        pokemonRepository.save(pokemon);
//
//        // Retrieve the Pokemon from the database by species
//        Pokemon savedPokemon = pokemonRepository.findBySpecies(randomSpecies);
//
//        // Perform assertions to ensure that the Pokemon was saved correctly
//        assertNotNull(savedPokemon);
//        assertEquals(randomSpecies, savedPokemon.getSpecies());
//        assertEquals(randomNickname, savedPokemon.getNickName());
//        assertEquals(10, savedPokemon.getLevel());
//
//    }
//
//
//    @Test
//    public void testDeleteUser() throws Exception {
//        // Create a new player to be deleted
//        Player playerToDelete = new Player("ToDeleteUser", "delete@example.com", "password123");
//        playerRepository.save(playerToDelete);
//
//        // Send a DELETE request to delete the user
//        mockMvc.perform(delete("/delete/{id}", playerToDelete.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//        // Verify that the user has been deleted from the database
//        assertFalse(playerRepository.existsById(playerToDelete.getId()));
//    }
//
//
//}

