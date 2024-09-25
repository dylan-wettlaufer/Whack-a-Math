import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * PasswordScreen password protects all features that are not meant to be used by the regular user
 * passwords will be provided to everyone who needs access to these
 * <p>
 * The page is run using the {@link #showPasswordScreen(Stage, String)} showPasswordScreen(Stage, page)} method
 * </p>
 * <pre>
 *     PasswordScreen passwordScreen = new PasswordScreen(user);
 *     passwordScreen.showPasswordScreen(stage, "DEBUG MODE");
 * </pre>
 * @version 1.0.1b
 * @author Travis Braun
 * @author Salim Terzout
 */

public class PasswordScreen {
    /** call to main menu (accessed by back button */
    private mainScreen mainScreen;
    /** access to debug mode if that is the option selected from main screen */
    private DebugMode debugMode;
    /** access to the instructor dashboard if that is the option selected from main screen */
    private InstructorDashboard instructorMode;
    /** debug mode password */
    private static final String password1 = "debug";
    /** instructor dashboard password */
    private static final String password2 = "instructor";
    /** current user */
    private User user;
    /** constructor for the class, creates a new PasswordScreen and initializes the user
     * @param user is the user that is currently accessing the screen
     * */

    public PasswordScreen(User user){
        this.user = user;
        System.out.println(user.getUsername() + "Is on the password screen");
    }
    /** Used to style and show the DebugMode screen, displays the stage and canvas with backgrounds and all buttons and labels
     * @param stage is the stage that the canvas and all attributes will be drawn on
     * @param page is a string with the desired page as this class is used to protect multiple pages, can be either "INSTRUCTOR MODE" or "DEBUG MODE"
     * */

    public void showPasswordScreen(Stage stage, String page) {
        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_CENTER);
        Scene passwordScene = new Scene(root, 1300, 900);
        TextField userInput = new TextField();
        Button enterBtn = new Button("ENTER");

        Canvas canvas = new Canvas(1300, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image background = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));
        gc.drawImage(background, 0, 0, 1300, 900); // Draw the image at (0, 0) with specified dimensions

        Label title = new Label();
        title.setText(page);
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
        enterBtn.setTranslateY(475);
        backButton.setStyle("-fx-font-size: 24px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; ");



        passwordScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                backButton.fire();
                event.consume(); // Consume the event to prevent further handling
            }
        });

        // styling input text box
        userInput.setPromptText("Password ");
        userInput.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
        userInput.setMaxWidth(300);
        userInput.setStyle("-fx-border-color: black");
        userInput.setTranslateY(436);

        // "Enter Password:" Label
        Label enterPassword = new Label("Enter Password:");
        enterPassword.setStyle("-fx-font-size: 40px; -fx-text-fill: black;");
        enterPassword.setTranslateY(375);

        //enter button styling
        enterBtn.setStyle("-fx-font-size: 34px; -fx-text-fill: #51361A; -fx-background-color: #F9D849; -fx-border-color: #51361A; ");

        Rectangle box = new Rectangle(100, 100, 450, 250);
        box.setFill(Paint.valueOf("#85612c"));
        box.setArcWidth(20);
        box.setArcHeight(20);
        box.setStrokeWidth(3);
        box.setStroke(Paint.valueOf("000000"));
        box.setTranslateY(325);

        Label incorrect = new Label("Incorrect Password");
        incorrect.setStyle("-fx-font-size: 20; -fx-text-fill: red");
        incorrect.setTranslateY(350);


        enterBtn.setOnAction(e -> {
            String userText = userInput.getText();
            if (userText.equals(password1) && page.equals("DEBUG MODE")) {
                debugMode = new DebugMode(user);
                debugMode.showDebugMode(stage);
            }
            else if (userText.equals(password2) && page.equals("INSTRUCTOR MODE")){
                try {
                    instructorMode = new InstructorDashboard(user);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                instructorMode.showDash(stage);
            }
            else {
                root.getChildren().add(incorrect);
            }
                });


        passwordScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                enterBtn.fire();
                event.consume(); // Consume the event to prevent further handling
            }
        });


        root.getChildren().addAll(canvas, box, backButton, title, enterBtn, userInput, enterPassword);

        stage.setScene(passwordScene);
        stage.show();
    }
}
