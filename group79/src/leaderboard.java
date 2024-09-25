import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Displays a leaderboard in a JavaFX application. The leaderboard is populated with user data, showing ranks based on high scores.
 * It supports navigation back to the main screen.
 */
public class leaderboard {
    private Button backButton;
    private mainScreen mainScreen;
    private UsersHashMap usersHashMap;
    private HashMap<String, User> userMap;
    private GraphicsContext gc;
    private User user;
    /**
     * Constructs a leaderboard instance, initializing user data from a specified source.
     *
     * @param user The current user, used for certain UI operations like navigation.
     * @throws IOException If there is an issue reading the user data source.
     */
    public leaderboard(User user) throws IOException {
        this.user = user;
        this.usersHashMap = new UsersHashMap();
        this.userMap = usersHashMap.getUsersMap();
    }

    public void showLeaderboard(Stage stage) {
        Pane layout = new Pane();

        Canvas canvas = new Canvas(1300, 900);
        gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image background = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));
        gc.drawImage(background, 0, 0);

        Label leaderboardLabel = new Label("LEADERBOARD");
        leaderboardLabel.setFont(new Font("Arial", 36));
        leaderboardLabel.setLayoutX(550);
        leaderboardLabel.setStyle("-fx-font-size: 70px; -fx-font-weight: bold; -fx-text-fill: black");

        leaderboardLabel.setLayoutX(450);
        leaderboardLabel.setLayoutY(50);

        backButton = new Button("BACK");
        backButton.setOnAction(e -> {
            if (mainScreen == null) {
                try {
                    mainScreen = new mainScreen(user);
                    mainScreen.setSignedIn(true);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                mainScreen.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        backButton.setPrefSize(100, 50);
        backButton.setLayoutX(25);
        backButton.setLayoutY(750);

        backButton.setStyle("-fx-font-size: 24px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A;");
        BorderPane leaderboardContainer = new BorderPane();
        leaderboardContainer.setPrefSize(600, 500);
        leaderboardContainer.setLayoutX(350);
        leaderboardContainer.setLayoutY(150);
        leaderboardContainer.setStyle("-fx-border-color: black; -fx-border-width: 5;");
        leaderboardContainer.setStyle("-fx-font-size: 30px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: black; -fx-border-width: 5px;");

        Pane numbersPane = new Pane();
        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);

        // Headers for the leaderboard columns
        Label idHeader = new Label("ID");
        idHeader.setFont(new Font("Arial", 24));
        idHeader.setLayoutX(150);  // Align with the ID column
        idHeader.setLayoutY(10);  // Set a fixed Y position above the first entry

        Label levelHeader = new Label("Level");
        levelHeader.setFont(new Font("Arial", 24));
        levelHeader.setLayoutX(250);  // Align with the Level column
        levelHeader.setLayoutY(10);  // Set a fixed Y position above the first entry

        Label scoreHeader = new Label("Score");
        scoreHeader.setFont(new Font("Arial", 24));
        scoreHeader.setLayoutX(350);  // Align with the Score column
        scoreHeader.setLayoutY(10);  // Set a fixed Y position above the first entry

        numbersPane.getChildren().addAll(idHeader, levelHeader, scoreHeader);

        List<Map.Entry<String, User>> sortedEntries = userMap.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().getHighscore() - entry1.getValue().getHighscore())
                .collect(Collectors.toList());
        // Initialize starting Y position for the first entry in the leaderboard.
        int startY = 75;
// Initialize rank to assign to the first user.
        int rank = 1;

// Iterate over each entry in the sorted list of users (sorted by high score).
        for (Map.Entry<String, User> entry : sortedEntries) {
            User user = entry.getValue(); // Extract the User object from the current entry.

            // Create a label for displaying the user's rank, set its font, position, and add to the pane.
            Label number = new Label(Integer.toString(rank++)); // Increment rank for the next iteration.
            number.setFont(new Font("Arial", 24));
            number.setLayoutX(50); // Set the horizontal position for the rank label.
            number.setLayoutY(startY); // Set the vertical position, which increases with each iteration.

            // Create a label for displaying the user's username, set its font and position, and add to the pane.
            Label username = new Label(user.getUsername());
            username.setFont(new Font("Arial", 24));
            username.setLayoutX(150); // Set the horizontal position for the username label.
            username.setLayoutY(startY); // Vertical position aligns with the rank label.

            // Create a label for displaying the user's level, set its font and position, and add to the pane.
            Label level = new Label(String.valueOf(user.getLvl()));
            level.setFont(new Font("Arial", 24));
            level.setLayoutX(250); // Set the horizontal position for the level label.
            level.setLayoutY(startY); // Vertical position aligns with the rank and username labels.

            // Create a label for displaying the user's high score, set its font and position, and add to the pane.
            Label score = new Label(String.valueOf(user.getHighscore()));
            score.setFont(new Font("Arial", 24));
            score.setLayoutX(350); // Set the horizontal position for the score label.
            score.setLayoutY(startY); // Vertical position aligns with the other labels.

            startY += 90; // Increase the startY value for the next entry to maintain vertical spacing between entries.
            numbersPane.getChildren().addAll(number, username, level, score); // Add all labels for the current user to the pane.
        }



        numbersPane.setStyle("-fx-font-size: 30px; -fx-background-color: F9D849;");
        scroll.setStyle("-fx-border-color: black; -fx-border-width: 5; -fx-font-size: 30px; -fx-text-fill: #51361A; -fx-background-color: F9D849;");

        scroll.setContent(numbersPane);
        leaderboardContainer.setCenter(scroll);

        layout.getChildren().add(canvas);
        layout.getChildren().addAll(leaderboardLabel, leaderboardContainer, backButton);

        Scene leaderboardScene = new Scene(layout, 1300, 900);
        leaderboardScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                backButton.fire();
                event.consume();
            }
        });
        stage.setScene(leaderboardScene);
    }
}