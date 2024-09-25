import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The screen that displays the players result after the game
 *
 * <p>
 *      The screen is run once the player runs out of lives using the {@link  #show(Stage)} method.
 *      The show method prints the results to the screen using labels
 * </p>
 * <b>Example Use:</b>
 * <pre>
 * {@code
 *       if (player.getLives() == 0) {
 *               this.stop();
 *               ResultsScreen screen = new ResultsScreen(player.getScore(), player.getLevel(), questionsTillNextLevel, user);
 *               screen.show(primaryStage);
 *               }
 * }
 * </pre>
 * @version 1.0.1b
 * @author Dylan Wettlaufer
 * */

public class ResultsScreen {
    /** The root of the screen */
    private StackPane root;
    /** The game canvas */
    private Canvas canvas;
    /** The scene of the screen */
    private Scene theScene;
    /** The Graphical Context of the game */
    private GraphicsContext gc;
    /** The player socre, level, and the number of questions till the level is complete  */
    final private int score, level, levelCompleted;
    /**Label for the header of the page  */
    private Label resultsLabel;
    /** The user playing the game */
    private User user;
    /** the main screen that is created if the player wants to return to the main menu */
    private mainScreen mainScreen;


    /**
     * Constructor for the Results Screen.
     *
     * @param playerScore the score that the player got in the game
     * @param playerLevel the level the player completed
     * @param levelCompleted the number of questions till the level is completed
     * @param user current user
     *
     * */
    public ResultsScreen(int playerScore, int playerLevel, int levelCompleted, User user) {

        score = playerScore;
        level = playerLevel;

        this.levelCompleted = levelCompleted;
        this.user = user;

        if (user.getHighscore() < playerScore){
            user.setHighscore(playerScore);
        }

    }

    /**
     * Used to display the screen.
     *
     * <p>
     * Longer description. <i>Method creates the labels and displays them on the screen. The method
     * calculates if the player reached the next level based off of the number of questions remaining.
     * If they passed the level the level of the user is increased and the labels text change to display that they passed.
     * Buttons for play again, main menu, and leaderboard are also created.</i>
     * </p>
     *
     * @param stage the stage to be displayed
     *
     * */

    public void show(Stage stage) {
        stage.setTitle("RESULTS");
        root = new StackPane();
        theScene = new Scene(root);
        canvas = new Canvas(1300, 900);
        gc = canvas.getGraphicsContext2D();
        resultsLabel = new Label("RESULTS");

        root.getChildren().add(canvas);

        resultsLabel.setStyle("-fx-font-size: 70px; -fx-text-fill: black; -fx-font-weight: bold");
        resultsLabel.setTranslateY(-350);
        root.getChildren().add(resultsLabel);

        Label levelLabel = new Label("LEVEL " + level );
        levelLabel.setStyle("-fx-font-size: 45px; -fx-text-fill: black; -fx-font-weight: bold");
        levelLabel.setTranslateY(-270);
        root.getChildren().add(levelLabel);

        Label scoreLabel = new Label("YOUR SCORE: " + score);
        scoreLabel.setStyle("-fx-font-size: 45px; -fx-text-fill: black; -fx-font-weight: bold");
        scoreLabel.setTranslateY(-200);
        root.getChildren().add(scoreLabel);

        Label nextLevelLabel = new Label();
        if (levelCompleted == 0 && level == 4) { // if the level is 4 the game has been complete
            nextLevelLabel.setText("GAME COMPLETE");
        }
        else if (levelCompleted == 0) { // if the level isn't 4 the game is not complete and the user progresses to the next level
            nextLevelLabel.setText("LEVEL COMPLETE");
            user.setLvl(level+1);

        }
        // player did not beat the game or make it to the next level
        else nextLevelLabel.setText(levelCompleted + " QUESTIONS TILL NEXT LEVEL");
        nextLevelLabel.setStyle("-fx-font-size: 45px; -fx-text-fill: black; -fx-font-weight: bold");
        nextLevelLabel.setTranslateY(-120);
        root.getChildren().add(nextLevelLabel);

        Button menuButton = new Button("MAIN MENU");
        menuButton.setPrefWidth(400);
        menuButton.setPrefHeight(100);
        menuButton.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; ");
        menuButton.setTranslateY(340);
        root.getChildren().add(menuButton);

        Button playAgain = new Button();
        if (levelCompleted == 0 && level != 4) playAgain.setText("NEXT LEVEL");
        else playAgain.setText("PLAY AGAIN");
        playAgain.setPrefSize(400, 100);
        playAgain.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; ");
        playAgain.setTranslateY(10);
        root.getChildren().add(playAgain);


        Button leaderboard = new Button("LEADERBOARD");
        leaderboard.setPrefSize(400, 100);
        leaderboard.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; ");
        leaderboard.setTranslateY(180);
        root.getChildren().add(leaderboard);

        menuButton.setOnAction(actionEvent -> {
            try {
                mainScreen = new mainScreen(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                mainScreen.setSignedIn(true);
                mainScreen.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        playAgain.setOnAction(actionEvent -> {
            GameState gameState;
            gameState = new GameState(user.getLvl(), user);
            try {
                gameState.show(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        leaderboard.setOnAction(actionEvent -> {
            leaderboard leaderboard1 = null;
            try {
                leaderboard1 = new leaderboard(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            leaderboard1.showLeaderboard(stage);
        });

        theScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                menuButton.fire();
                event.consume(); // Consume the event to prevent further handling
            }
        });

        javafx.scene.image.Image background = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));
        gc.drawImage(background, 0, 0);

        stage.setScene(theScene);
        stage.show();

    }

}
