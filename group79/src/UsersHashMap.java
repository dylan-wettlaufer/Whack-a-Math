import java.io.*;
import java.util.HashMap;
/**
 * Represents a mapping of usernames to User objects, initialized from a file.
 * This class reads a specified file to create a map where each key is a username
 * and each value is a corresponding User object, encapsulating the user's username,
 * level, and high score.
 */
public class UsersHashMap {
    int lvl, highScore;
    String username;
    HashMap<String, User> usersMap = new HashMap<>();
    /**
     * Constructs a new UsersHashMap by reading user data from a file.
     * The file is expected to contain user information in the format:
     * username,level,highscore
     * Lines that do not match this format are skipped with a warning message.
     *
     * @throws IOException If an I/O error occurs when reading the file.
     */
    public UsersHashMap() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/users.txt")))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] eachLine = line.split(",");
                if (eachLine.length == 3) { // Ensure each line has correct format
                    username = eachLine[0].trim();
                    lvl = Integer.parseInt(eachLine[1].trim());
                    highScore = Integer.parseInt(eachLine[2].trim());
                    usersMap.put(username, new User(username, highScore, lvl));
                } else {
                    // Handle improperly formatted lines if needed
                    System.err.println("Skipping improperly formatted line: " + line);
                }
            }
        }
    }
    /**
     * Returns the users map, with usernames as keys and User objects as values.
     *
     * @return The map of usernames to User objects.
     */
    public HashMap<String, User> getUsersMap() {
        return usersMap;
    }
}
