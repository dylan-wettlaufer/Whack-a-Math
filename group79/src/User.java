import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a user in the system, encapsulating their username, high score, and level.
 * This class also provides the capability to update a CSV file with the user's data.
 */
public class User {
    //fields
    private int highScore;
    private int lvl;
    private String username;
    /**
     * Constructs a new User with the specified username, high score, and level.
     *
     * @param username  the username of the user
     * @param highScore the high score of the user
     * @param lvl       the level of the user
     */
    public User(String username, int highScore, int lvl) {
        this.username = username;
        this.highScore = highScore;
        this.lvl = lvl;
    }

    /**
     * Returns the user's high score.
     *
     * @return the high score of this user
     */
    public int getHighscore() {
        return highScore;
    }

    /**
     * Updates the user's high score and modifies the associated CSV file to reflect this change.
     *
     * @param highScore the new high score to set for the user
     */
    public void setHighscore(int highScore) {
        this.highScore = highScore;
        editCSV(2);
    }

    /**
     * Returns the user's level.
     *
     * @return the level of this user
     */
    public int getLvl() {
        return lvl;
    }

    /**
     * Updates the user's level and modifies the associated CSV file to reflect this change.
     *
     * @param lvl the new level to set for the user
     */
    public void setLvl(int lvl) {
        this.lvl = lvl;
        editCSV(1);
    }

    /**
     * Returns the user's username.
     *
     * @return the username of this user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates the user's username.
     *
     * @param username the new username to set for the user
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Private helper method to modify a CSV file based on the user's current data.
     * Depending on the value of {@code toChange}, the method updates either the level or high score of the user
     * in the CSV file. If the user does not exist in the file, it adds a new entry.
     *
     * @param toChange indicator of the attribute to change in the CSV: 1 for level, 2 for high score
     */
    private void editCSV(int toChange) {
        List<String> modifiedLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/users.txt")))) {
            String line;
            boolean modified = false;
            while ((line = reader.readLine()) != null) {
                String[] eachLine = line.split(",");
                if (eachLine.length == 3 && eachLine[0].equals(username)) {
                    // Modify the proper text
                    if (toChange == 1) {
                        eachLine[1] = String.valueOf(lvl);
                    } else if (toChange == 2) {
                        eachLine[2] = String.valueOf(highScore);
                    }
                    line = String.join(",", eachLine);
                    modified = true;
                }
                modifiedLines.add(line);
            }

            if (!modified) {
                // If no modification was made, add the new entry
                modifiedLines.add(username + "," + lvl + "," + highScore);
            }

            // Write modified content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("group79/src/users.txt"))) {
                for (String modifiedLine : modifiedLines) {
                    writer.write(modifiedLine + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
