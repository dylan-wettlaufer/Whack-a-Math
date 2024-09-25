/**
 * The {@code Player} class represents a player in a game,
 * encapsulating their score, number of lives, and current level.
 */
public class Player {
    // Fields
    private int score;
    private int lives;
    private int level;
    /**
     * Constructs a new {@code Player} instance with the specified score, number of lives, and level.
     *
     * @param score the initial score of the player
     * @param lives the initial number of lives the player has
     * @param level the initial level the player is on
     */
    public Player (int score, int lives, int level) {
        this.score = score;
        this.lives = lives;
        this.level = level;
    }
    /**
     * Returns the current score of the player.
     *
     * @return the score of the player
     */
    public int getScore() {
        return score;
    }
    /**
     * Updates the score of the player.
     *
     * @param score the new score value to set
     */
    public void setScore(int score) {
        this.score = score;
    }
    /**
     * Returns the current number of lives of the player.
     *
     * @return the number of lives the player has
     */
    public int getLives() {
        return lives;
    }
    /**
     * Updates the number of lives of the player.
     *
     * @param lives the new number of lives to set for the player
     */
    public void setLives(int lives) {
        this.lives = lives;
    }
    /**
     * Returns the current level of the player.
     *
     * @return the level the player is on
     */
    public int getLevel() {
        return level;
    }
    /**
     * Updates the level of the player.
     *
     * @param level the new level to set for the player
     */
    public void setLevel(int level) {
        this.level = level;
    }
}
