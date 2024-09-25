import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Default constructor for UsersHashMapTest.
 * Initializes a new instance of UsersHashMapTest.
 */
public class UsersHashMapTest {
    /** create instance of userHashMap to be used */
    private UsersHashMap usersHashMap;

    /**
     * Initializes UsersHashMap for each test. This setup could be adapted to use a specific test file or
     * to mock the file reading process to ensure consistency and isolation from external dependencies.
     */
    @BeforeEach
    void setUp() throws IOException {
        // Initialize UsersHashMap here. Consider using a specific test file or mocking the file reading.
        usersHashMap = new UsersHashMap(); // Replace with a test-specific setup if necessary.
    }

    /**
     * Tests whether UsersHashMap successfully loads data into the map and the map is not empty.
     */
    @Test
    void testUsersMapIsNotEmpty() {
        HashMap<String, User> map = usersHashMap.getUsersMap();
        assertNotNull(map, "Users map should not be null");
        assertFalse(map.isEmpty(), "Users map should not be empty after initialization");
    }

    /**
     * Tests if the map contains specific expected users after initialization.
     * This test assumes the presence of certain users in the test file.
     */
    @Test
    void testMapContainsSpecificUsers() {
        // Assuming "testUser" is a username in your test file with known highScore and level.
        String expectedUsername = "testUser";
        int expectedHighScore = 100; // Replace with expected values
        int expectedLevel = 1; // Replace with expected values

        User user = usersHashMap.getUsersMap().get(expectedUsername);

        assertNotNull(user, "Expected user should be present in the map");
        assertEquals(expectedHighScore, user.getHighscore(), "HighScore should match the expected value");
        assertEquals(expectedLevel, user.getLvl(), "Level should match the expected value");
    }

}
