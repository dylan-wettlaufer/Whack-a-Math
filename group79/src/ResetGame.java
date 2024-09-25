import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The screen that allows the player to reste their game
 *
 * <p>
 *       The screen is run once the player runs out of lives using the {@link  #show(Stage)} method.
 *       The show method prints the results to the screen using labels
 * </p>
 * <pre>
 * {@code
 *          ResetGame resetGame = new ResetGame(user);
 *          resetGame.show(stage);
 * }
 * </pre>
 * @version 1.0.1b
 * @author Dylan Wettlaufer
 * */
public class ResetGame {
    /** The root of the screen */
    private StackPane root;
    /** The game canvas */
    private Canvas canvas;
    /** The scene of the screen */
    private Scene theScene;
    /** The Graphical Context of the game */
    private GraphicsContext gc;
    /** The user playing the game */
    User user;

    /** Constructor for the ResetGame class
     *
     * @param user the user playing the game
     * */

    public ResetGame(User user) {
        this.user = user;
        root = new StackPane();
        theScene = new Scene(root);
        canvas = new Canvas(1300, 900);
        gc = canvas.getGraphicsContext2D();
    }

    /**
     * Used to display the screen
     * <p>
     *     Longer Description: Creates labels and buttons and displays them on the screen.
     *     When the player clicks on the rest game button their level is reduced to level one and a message is displayed.
     * </p>
     *
     * @param stage the stage to be displayed.
     *
     * */

    public void show(Stage stage) {
        stage.setTitle("RESET GAME");
        root.getChildren().add(canvas);

        Label levelSelectLabel = new Label("RESET GAME");
        levelSelectLabel.setStyle("-fx-font-size: 70px; -fx-text-fill: black; -fx-font-weight: bold");
        levelSelectLabel.setTranslateY(-350);
        root.getChildren().add(levelSelectLabel);

        Label resetText = new Label("RESETTING THE GAME WILL BRING YOU BACK TO LEVEL 1! \n           YOUR HIGH SCORE WILL NOT BE DELETED!");
        resetText.setStyle("-fx-font-size: 30px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A;");
        resetText.setTranslateY(-150);
        root.getChildren().add(resetText);

        Button resetButton = new Button("RESET GAME");
        Button backButton = new Button("BACK");


        resetButton.setPrefSize(400, 100);
        resetButton.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; ");
        backButton.setPrefSize(200, 100);
        backButton.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; ");

        resetButton.setTranslateY(20);
        backButton.setTranslateY(330);
        backButton.setTranslateX(-380);

        backButton.setOnAction(actionEvent -> {
            try {
                mainScreen mainScreen = new mainScreen(user);
                mainScreen.setSignedIn(true);
                mainScreen.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        resetButton.setOnAction(actionEvent -> {
            user.setLvl(1); // sets level to 1
            Label label = new Label("GAME RESTARTED"); // crates label to tell player they have reset the game
            label.setStyle("-fx-font-size: 25px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A;");
            label.setTranslateY(150);
            root.getChildren().add(label);
        });


        root.getChildren().addAll(resetButton, backButton);

        Image background = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));
        gc.drawImage(background, 0, 0);

        stage.setScene(theScene);
        stage.show();
    }

}
