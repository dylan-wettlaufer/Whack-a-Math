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
 * Help screen is to inform the user on how the game works
 * <p>
 * The page is run using the {@link #showHelp(Stage)} method
 * </p>
 * <pre>
 *     Help helpScreen = new Help(user);
 *     helpScreen.showHelp(stage);
 * </pre>
 * @version 1.0.1b
 * @author Travis Braun
 */

public class Help {
    /** link back to main menu (accessed by back button) */
    private mainScreen mainScreen;
    /** user currently accessing the page */
    private User user;
    /** constructor for the class, creates a new Help variable and initializes the user
     * @param user is the user that is currently accessing the screen
     * */
    public Help(User user){
        this.user = user;
        System.out.println(user.getUsername() + "Is on the help screen");
    }
    /** Used to style and show the Help screen, displays the stage and canvas with backgrounds and all buttons and labels, and informative text
     * @param stage is the stage that the canvas and all attributes will be drawn on
     *              all attributes are added at the end of the method
     * */

    public void showHelp(Stage stage) {
        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_CENTER);
        Scene helpScene = new Scene(root, 1300, 900);

        Canvas canvas = new Canvas(1300, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image background = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));
        gc.drawImage(background, 0, 0, 1300, 900);

        Label title = new Label();
        title.setText("HELP");
        title.setStyle("-fx-font-size: 70px; -fx-font-weight: bold; -fx-text-fill: black");
        title.setTranslateY(50);

        Button backButton = new Button("BACK");
        backButton.setOnAction(e -> {
            if (mainScreen == null) {
                try {
                    mainScreen = new mainScreen(user);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            try {
                mainScreen.setSignedIn(true);
                mainScreen.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        backButton.setPrefSize(100, 50);
        backButton.setTranslateY(750);
        backButton.setTranslateX(-550);
        backButton.setStyle("-fx-font-size: 24px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; ");

        Label instructionsText = new Label();
        instructionsText.setText("After selecting “Play Game”, promptly solve each math question, and quickly \n" +
                " select the mole with the correct number. To progress through the levels \n" +
                "and build your score, continue selecting the correct moles until you have \n" +
                "lost all of your lives. Once each new level is reached, you can choose to \n" +
                "continue building your score by continuing to play, or by progressing to \n" +
                "the next level by losing all of your lives. Each level presents a new set \n" +
                "of problems, with potential to unlock an increased number of points. After \n" +
                "each game is completed, you may view your score in the “Leaderboard” tab, to \n" +
                "compare your score against others. ");
        instructionsText.setStyle("-fx-font-size: 30px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A;");
        instructionsText.setTranslateY(235);

        Label instructionsLabel = new Label();
        instructionsLabel.setText("INSTRUCTIONS");
        instructionsLabel.setStyle("-fx-font-size: 50px; -fx-text-fill: black; -fx-font-weight: bold");
        instructionsLabel.setTranslateY(135);

        helpScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                backButton.fire();
                event.consume();
            }
        });

        root.getChildren().addAll(canvas, backButton, title, instructionsText, instructionsLabel);

        stage.setScene(helpScene);
        stage.show();
    }
}
