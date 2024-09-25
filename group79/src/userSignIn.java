import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;

import javax.sound.sampled.*;
import java.io.*;
import java.util.HashMap;
/**
 * Represents the sign-in and sign-up screen for users in a JavaFX application.
 * This class manages user authentication and registration, including input validation
 * and updating user information in an external file. It also initiates the main screen
 * upon successful sign-in or registration.
 */
public class userSignIn {
    HashMap<String, User> userMap;
    boolean registered = false;
    mainScreen mainScreen;
    private Clip clip;
    /**
     * Constructs a new {@code userSignIn} instance. Initialization in this constructor is minimal
     * due to the requirement of IOException handling.
     *
     * @throws IOException If there is an issue reading user data from the external file.
     */
    public userSignIn() throws IOException {
    }
    /**
     * Displays the user sign-in and sign-up screen. This method sets up the JavaFX components
     * for the UI, including text fields for username input, labels for instructions, and buttons
     * for submission. It also handles the logic for user validation and registration, updating
     * the external user data file as needed.
     *
     * @param primaryStage The primary stage for this application, onto which the sign-in screen
     *                     will be set.
     */
    public void show(Stage primaryStage){

        // creating screen
        StackPane root = new StackPane();
        Scene signInScene = new Scene(root);

        Canvas canvas = new Canvas(1300, 900);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        root.getChildren().add(canvas);

        Image background = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));;
        gc.drawImage(background, 0, 0);

        // adding title
        Label title = new Label();
        title.setText("USER LOGIN/SIGN UP");
        title.setStyle("-fx-font-size: 70px; -fx-font-weight: bold; -fx-text-fill: black");
        title.setTranslateY(-355);

        Label incorrect1 = new Label("Username cannot contain commas or spaces");
        incorrect1.setStyle("-fx-font-size: 20; -fx-text-fill: red");
        incorrect1.setTranslateY(-100);

        Label incorrect2 = new Label("Username cannot begin or end with a space");
        incorrect2.setStyle("-fx-font-size: 20; -fx-text-fill: red");
        incorrect2.setTranslateY(-100);

        // sign in functionality
        TextField userInput = new TextField();
        Button enterBtn = new Button("Enter");

        enterBtn.setOnAction(e -> {
            String username = userInput.getText();

            if (username.contains(",")){
                root.getChildren().add(incorrect1);
                return;
            }

            if (username.startsWith(" ") || username.endsWith(" ")){
                root.getChildren().add(incorrect2);
                return;
            }

            try {
                this.userMap = new UsersHashMap().getUsersMap();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            registered = isUser(username);

            if (!registered){
                try {
                    addNewUserToFiles(username);
                    this.userMap = new UsersHashMap().getUsersMap();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            try {
                mainScreen = new mainScreen(userMap.get(username));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            mainScreen.setSignedIn(true);

            try {
                mainScreen.setSignedIn(true);
                mainScreen.start(primaryStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        signInScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                enterBtn.fire();
                event.consume(); // Consume the event to prevent further handling
            }
        });

        // styling input text box
        userInput.setPromptText("Password ");
        userInput.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
        userInput.setMaxWidth(300);
        userInput.setStyle("-fx-border-color: black");

        // "Enter Password:" Label
        Label enterPassword = new Label("Enter Username:");
        enterPassword.setStyle("-fx-font-size: 40px; -fx-text-fill: black;");
        enterPassword.setTranslateY(-50);

        //enter button styling
        enterBtn.setStyle("-fx-font-size: 34px; -fx-text-fill: #51361A; -fx-background-color: #F9D849; -fx-border-color: #51361A; ");
        enterBtn.setTranslateY(60);
        Rectangle box = new Rectangle(100, 100, 450, 250);
        box.setFill(Paint.valueOf("#85612c"));
        box.setArcWidth(20);
        box.setArcHeight(20);
        box.setStrokeWidth(3);
        box.setStroke(Paint.valueOf("000000"));


        // assembling and displaying screen
        root.getChildren().addAll(box, userInput, enterBtn, title, enterPassword);
        primaryStage.setScene(signInScene);
        primaryStage.show();
    }
    /**
     * Checks if a given username is already registered in the user map.
     *
     * @param username The username to check for registration.
     * @return {@code true} if the user is already registered, {@code false} otherwise.
     */
    private boolean isUser(String username){
        if (userMap.get(username) == null){
            return false;
        }
        else {
            return true;
        }
    }
    /**
     * Adds a new user with the given username to the external users file. This method assumes
     * the user is not already registered and appends their data to the file.
     *
     * @param username The username of the new user to add.
     * @throws IOException If there is an issue writing to the external file.
     */
    private void addNewUserToFiles(String username) throws IOException {
        FileWriter writer = new FileWriter(getClass().getResource("users.txt").getFile(), true);
        writer.write(username +","+"1"+","+"0" + "\n");
        writer.close();
    }
}
