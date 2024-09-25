import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * DebugMode is used by the developer to skip ahead levels in the game
 * <p>
 * The page is run using the {@link #showDebugMode(Stage)} method, and is password protected by the @PasswordScreen class
 * </p>
 * <pre>
 *     DebugMode debugMode = new DebugMode(user);
 *     debugMode.showDebugMode(stage);
 * </pre>
 * @version 1.0.1b
 * @author Travis Braun
 * @author Dylan Wettlaufer
 * @author Adam Kahloon
 */

public class DebugMode {
    /** current user */
    private User user;
    /** game to be loaded after selecting level */
    private GameState gameState;
    /** back to the main screen (accessed by back button) */
    private mainScreen mainScreen;
    /** constructor for the class, creates a new DebugMode and initializes the user
     * @param user is the user that is currently accessing the screen
     * */
    public DebugMode(User user){
        this.user = user;
        System.out.println(user.getUsername() + "Is on the debug debugMode screen");
    }

    /** Used to style and show the DebugMode screen, displays the stage and canvas with backgrounds and all buttons and labels
     * @param stage is the stage that the canvas and all attributes will be drawn on
     *              all attributes are added at the end of the method
     * */
    public void showDebugMode(Stage stage) {
        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_CENTER);
        Scene debugScene = new Scene(root, 1300, 900);

        Canvas canvas = new Canvas(1300, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image background = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));
        gc.drawImage(background, 0, 0, 1300, 900);

        Label title = new Label();
        title.setText("DEBUG MODE");
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



        debugScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                backButton.fire();
                event.consume(); // Consume the event to prevent further handling
            }
        });

        Rectangle box = new Rectangle(780, 300);
        box.setFill(Paint.valueOf("#85612c"));
        box.setArcWidth(20);
        box.setArcHeight(20);
        box.setStrokeWidth(3);
        box.setStroke(Paint.valueOf("000000"));
        box.setTranslateY(325);

        Button l1, l2, l3, l4;
        l1 = new Button("1");
        l2 = new Button("2");
        l3 = new Button("3");
        l4 = new Button("4");

        l1.setStyle("-fx-font-size: 34px; -fx-text-fill: #51361A; -fx-background-color: #F9D849; -fx-border-color: #51361A;");
        l1.setPrefSize(150, 150);
        l1.setTranslateY(400);
        l1.setTranslateX(-240);

        l2.setStyle("-fx-font-size: 34px; -fx-text-fill: #51361A; -fx-background-color: #F9D849; -fx-border-color: #51361A;");
        l2.setPrefSize(150, 150);
        l2.setTranslateY(400);
        l2.setTranslateX(-80);

        l3.setStyle("-fx-font-size: 34px; -fx-text-fill: #51361A; -fx-background-color: #F9D849; -fx-border-color: #51361A;");
        l3.setPrefSize(150, 150);
        l3.setTranslateY(400);
        l3.setTranslateX(80);

        l4.setStyle("-fx-font-size: 34px; -fx-text-fill: #51361A; -fx-background-color: #F9D849; -fx-border-color: #51361A;");
        l4.setPrefSize(150, 150);
        l4.setTranslateY(400);
        l4.setTranslateX(240);

        Label select = new Label("SELECT START LEVEL");
        select.setStyle("-fx-text-fill: black; -fx-font-size: 34");
        select.setTranslateY(345);

        l1.setOnAction(e -> {
            gameState = new GameState(1, user);
            try {
                gameState.show(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        l2.setOnAction(e -> {
            gameState = new GameState(2, user);
            try {
                gameState.show(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        l3.setOnAction(e -> {
            gameState = new GameState(3, user);
            try {
                gameState.show(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        l4.setOnAction(e -> {
            gameState = new GameState(4, user);
            try {
                gameState.show(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

        });


        root.getChildren().addAll(canvas, box, backButton, title, l1, l2, l3, l4, select);

        stage.setScene(debugScene);
        stage.show();
    }
}
