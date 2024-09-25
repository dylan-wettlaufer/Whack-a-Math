import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;


import java.io.IOException;
import java.util.List;

/**
 * Main Screen is used as the starting point of the project where the user decides where to navigate.
 * <p>
 *
 * The game is run using the {@link #start(Stage)} method. This method presents the sign in screen which continues on
 * to different screens
 *
 * {@link #mainScreen(User)} (user)} creates new main screen with our user
 * {@link #start(Stage)} creates a new screen for the user to sign in and navigate through to the main screen.
 * {@link #setSignedIn(boolean)} sets the true or false value for a user being succesfully signed in or not.
 * </p>
 * <b>Example Use:</b>
 * <pre>
 * { @code
 *          try {
 *                 signInPage = new userSignIn();
 *             } catch (IOException ex) {
 *                 throw new RuntimeException(ex);
 *             }
 *             signInPage.show(stage);
 *         };
 *
 * </pre>
 * @version 1.0.1b
 * @author Salim Terzout
 * @author Dylan Wettlaufer
 * @author Josh Reinhart
 * @author Adam Kahloon
 * @author Travis Braun
 */

public class mainScreen {

    /** The sign in screen */
    private userSignIn signInPage;
    /** The password screen */
    private PasswordScreen passwordScreen;
    /** The GameState screen */
    private GameState gameState;
    /** The Leaderboard screen */
    private leaderboard leaderboard;
    /** The Help screen */
    private Help helpScreen;
    /** The check for sign in as true or false */
    private boolean signedIn = false;
    /** The canvas of the game */
    private Canvas canvas;
    /** Used to set the graphics of our game */
    private GraphicsContext gc;
    /** Used to select users from the hashmap */
    private UsersHashMap userMap;
    /** User on the game */
    private User user;
    /** the instructor dashboard screen */
    private InstructorDashboard instruct;


    /**
     * mainScreen constructor. Creates a new main screen
     * @param user the user that is playing the game
     * @throws IOException in case files are not found
     * */
    public mainScreen(User user) throws IOException {
        this.userMap = new UsersHashMap();
        this.user = user;
        System.out.println(user.getUsername() + "Is on the main screen");

    }


    /**
     * Creates the title screen along with the different options screen with buttons to traverse to different menus
     *
     * Each screen can be accessed by the click of a button with the creation of private instance variables
     *
     * This screen also sets the graphical interface of the main screen when the users signs in
     *
     * @param stage is the stage being shown
     * @throws Exception in case an exception is thrown
     *
     * */
    public void start(Stage stage) throws Exception {
        stage.setTitle("Whacka-Math");

        StackPane root = new StackPane();
        Scene mainScene = new Scene(root);
        canvas = new Canvas(1300,900);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);


        Label titleLabel = new Label("WELCOME TO WHACKA-MATH !");
        Image moleWelcome = new Image(getClass().getClassLoader().getResourceAsStream("mole.png"));

        titleLabel.setStyle("-fx-font-size: 70px; -fx-text-fill: black; -fx-font-weight: bold"); // Set font size

        Button switchToMainGameButton = new Button("Play game");
        Button switchToLeaderboardButton = new Button("Leaderboard");
        Button switchToHelpScreenButton = new Button("Help");
        Button switchToSignInBtn = new Button("Sign In");
        Button switchToDebugButton = new Button("Debug Mode");
        Button switchToInstructorDashboard = new Button("Instructor Dashboard");
        switchToSignInBtn.setStyle("-fx-font-size: 30px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A;");


        switchToSignInBtn.setOnAction(e -> {
            try {
                signInPage = new userSignIn();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            signInPage.show(stage);
        });

        switchToDebugButton.setPrefSize(400, 100);
        switchToDebugButton.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; "
        );
        switchToDebugButton.setOnAction(e -> {
            passwordScreen = new PasswordScreen(user);
            passwordScreen.showPasswordScreen(stage, "DEBUG MODE");
        });



        switchToMainGameButton.setPrefSize(400, 100);
        switchToMainGameButton.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; "
        );
        switchToMainGameButton.setOnAction(e -> {
            gameState = new GameState(user.getLvl(), user);
            try {
                gameState.show(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });


        switchToLeaderboardButton = new Button("Leaderboard");
        switchToLeaderboardButton.setPrefSize(400, 100);
        switchToLeaderboardButton.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; "
        );
        switchToLeaderboardButton.setOnAction(e -> {
            if (leaderboard == null) {
                try {
                    leaderboard = new leaderboard(user);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }

            leaderboard.showLeaderboard(stage);

        });


        switchToHelpScreenButton = new Button("Help");
        switchToHelpScreenButton.setPrefSize(400, 100);
        switchToHelpScreenButton.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; "
        );
        switchToHelpScreenButton.setOnAction(e -> {
            if (helpScreen == null) {
                helpScreen = new Help(user);
            }
            helpScreen.showHelp(stage);
        });

        switchToInstructorDashboard = new Button("Instructor Mode");
        switchToInstructorDashboard.setPrefSize(400, 100);
        switchToInstructorDashboard.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; "
        );
        switchToInstructorDashboard.setOnAction(e -> {
            passwordScreen = new PasswordScreen(user);
            passwordScreen.showPasswordScreen(stage, "INSTRUCTOR MODE");
        });

        Button levelSelectButton = new Button("RESET GAME");
        levelSelectButton.setPrefSize(350, 100);
        levelSelectButton.setTranslateY(330);
        levelSelectButton.setTranslateX(-380);
        levelSelectButton.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A;");
        levelSelectButton.setOnAction(actionEvent -> {
            ResetGame resetGame = new ResetGame(user);
            resetGame.show(stage);
        });
        if (user.getLvl() > 1) root.getChildren().add(levelSelectButton);

        Button exitGame = new Button("Exit Game");
        exitGame.setPrefSize(400, 100);
        exitGame.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; ");

        exitGame.setOnAction(actionEvent -> {
            stage.close();;
        });



        Image backgroundImage = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));


        titleLabel.setTranslateY(-335);
        root.setPadding(new javafx.geometry.Insets(20, 0, 0, 0));

        switchToMainGameButton.setTranslateY(-200);
        switchToMainGameButton.setTranslateX(430);
        switchToDebugButton.setTranslateY(-90);
        switchToDebugButton.setTranslateX(430);

        switchToLeaderboardButton.setTranslateY(20); // Move up
        switchToLeaderboardButton.setTranslateX(430);
        switchToHelpScreenButton.setTranslateY(130); // Move up
        switchToHelpScreenButton.setTranslateX(430);
        switchToInstructorDashboard.setTranslateY(240); // Move up
        switchToInstructorDashboard.setTranslateX(430);

        exitGame.setTranslateX(430);
        exitGame.setTranslateY(350);

        Label subtitle = new Label("Created By: \n " +
                "Adam Kahloun \nTravis Braun \nDylan Wettlaufer \nSalim Terzout \nJoshua Reinhart\n\n" +
                "Group 79\nCS2212 Winter 2024\nWestern University");
        subtitle.setStyle("-fx-font-size: 24; -fx-text-fill: black");
        subtitle.setTranslateY(-125);
        subtitle.setTranslateX(-425);

        gc.drawImage(backgroundImage,0,0);

        if (signedIn) {
            gc.drawImage(moleWelcome, 500, 450, 300,300 );
            root.getChildren().addAll(switchToMainGameButton, switchToLeaderboardButton, switchToHelpScreenButton, titleLabel, switchToDebugButton, switchToInstructorDashboard, exitGame, subtitle);

        }
        else {
            root.getChildren().addAll(titleLabel, switchToSignInBtn);
        }



        stage.setScene(mainScene);

        stage.show();
    }
    /** initialize setSignedIn method
     *
     * @param l used to set our signedIn boolean value
     *
     * */
    public void setSignedIn(boolean l){
        signedIn = l;
    }
}