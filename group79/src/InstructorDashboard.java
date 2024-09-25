import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.*;
/**
 * Main Screen is used as the starting point of the project where the user decides where to navigate.
 * <p>
 *
 * The game is run using the {@link #showDash(Stage)} method. This method presents the instructor board screen
 *
 * {@link #InstructorDashboard(User)}  constructor to create instructor Dash board for our user
 * {@link #showDash(Stage)} shows the user the stage/screen of instructor Dash
 * </p>
 * <b>Example Use:</b>
 * <pre>
 * { @code
 *          switchToInstructorDashboard = new Button("Instructor Mode");
 *         switchToInstructorDashboard.setPrefSize(400, 100);
 *         switchToInstructorDashboard.setStyle("-fx-font-size: 45px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; "
 *         );
 *         switchToInstructorDashboard.setOnAction(e -> {
 *             passwordScreen = new PasswordScreen(user);
 *             passwordScreen.showDebugMode(stage, "INSTRUCTOR MODE");
 *         });
 *
 * </pre>
 * @version 1.0.1b
 * @author Salim Terzout
 */

public class InstructorDashboard {
    private Button backButton;
    private mainScreen mainScreen;
    private UsersHashMap usersHashMap;
    private HashMap<String, User> userMap;
    private GraphicsContext gc;
    private User user;

    public InstructorDashboard(User user) throws IOException {
        this.user = user;
        this.usersHashMap = new UsersHashMap();
        this.userMap = usersHashMap.getUsersMap();
    }
    /**
     * showDash constructor. displays our instructor dashboard screen. It allows for us to display buttons
     * stats, colours and all that sort of stuff onto the screen.
     *
     * @param stage is the scenery that is being shown
     * */
    public void showDash(Stage stage) {
        Pane layout = new Pane();


        Canvas canvas = new Canvas(1300, 900);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Image background = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));
        gc.drawImage(background, 0, 0);
        // setting background and fills

        Label instructorLabel = new Label("Instructor Dashboard");
        instructorLabel.setFont(new Font("Arial", 36));
        instructorLabel.setStyle("-fx-font-size: 70px; -fx-font-weight: bold; -fx-text-fill: black");
        instructorLabel.setLayoutX(350);
        instructorLabel.setLayoutY(50);
        // setting title and position on screen


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
        backButton.setStyle("-fx-font-size: 24px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A; ");
        //initializing back button with colours, position and return screen as main

        BorderPane instructorDash = new BorderPane();
        instructorDash.setPrefSize(1000, 500);
        instructorDash.setLayoutX(170);
        instructorDash.setLayoutY(150);
        instructorDash.setStyle("-fx-border-color: black; -fx-border-width: 5;");
        instructorDash.setStyle("-fx-font-size: 30px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: black; -fx-border-width: 5px;");
        //creating boarder pane for the instructor mode statistics board

        Pane numbersPane = new Pane();

        Label idHeader = new Label("ID");
        idHeader.setFont(new Font("Arial", 32));
        idHeader.setLayoutX(130);
        idHeader.setLayoutY(30);
        // setting pane to align columns with their respective stat starting with ID

        Label currLevel = new Label("Current Level");
        currLevel.setFont(new Font("Arial", 32));
        currLevel.setLayoutX(230);  // Align with the Level column
        currLevel.setLayoutY(30);  // Set a fixed Y position above the first entry
        // setting pane with Current level



        Label highScore = new Label("Score");
        highScore.setFont(new Font("Arial", 32));
        highScore.setLayoutX(490);  // Align with the Score column
        highScore.setLayoutY(30);  // Set a fixed Y position above the first entry
        // setting pane with Score

        Label levelsCompleted = new Label("Levels Completed");
        levelsCompleted.setFont(new Font("Arial", 32));
        levelsCompleted.setLayoutX(650);
        levelsCompleted.setLayoutY(30);
        // setting pane with Levels Completed by the player


        numbersPane.getChildren().addAll(idHeader, currLevel, highScore, levelsCompleted);
        // adding all the labels to the numbersPane
        List<User> userList = new ArrayList<>(userMap.values());
        Collections.sort(userList, Comparator.comparingInt(User::getHighscore).reversed());


        int yStart = 85;
        int ranking = 1;
        for (int i = 0; i < userMap.size(); i++){

            Label rank = new Label(Integer.toString(ranking));
            rank.setFont(new Font("Arial", 32));
            rank.setLayoutX(30);
            rank.setLayoutY(yStart);
            ranking++;


            String addName = userList.get(i).getUsername();
            Label userName = new Label(addName);
            userName.setFont(new Font("Arial", 32));
            userName.setLayoutX(100);
            userName.setLayoutY(yStart);

            String currentLevel = Integer.toString(userList.get(i).getLvl());
            Label level = new Label(currentLevel);
            level.setFont(new Font("Arial", 32));
            level.setLayoutX(320);
            level.setLayoutY(yStart);

            String score = Integer.toString(userList.get(i).getHighscore());
            Label highestScore = new Label(score);
            highestScore.setFont(new Font("Arial", 32));
            highestScore.setLayoutX(490);
            highestScore.setLayoutY(yStart);

            StringBuilder listOfCurrentLevels = new StringBuilder();
            User userlevels = userList.get(i);
            for (int j = 1; j <= userlevels.getLvl(); j++){
                if (1 == userlevels.getLvl()) {
                    listOfCurrentLevels.append(Integer.toString(j));
                    break;
                }else if (j == userlevels.getLvl()){
                    listOfCurrentLevels.append("1 - " + j);
                }
            }
            listOfCurrentLevels.toString();
            String levelsList = listOfCurrentLevels.toString();
            Label levelsDone = new Label(levelsList);
            levelsDone.setFont(new Font("Arial", 32));
            levelsDone.setLayoutX(760);
            levelsDone.setLayoutY(yStart);


            numbersPane.getChildren().addAll(rank, userName,level, highestScore, levelsDone);
            yStart += 100;

        }

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(numbersPane);


        scroller.setPrefSize(1300, 900);
        scroller.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        layout.getChildren().add(canvas);
        layout.getChildren().addAll(instructorLabel, instructorDash, backButton, scroller);
        instructorDash.setCenter(scroller);
        Scene instructorScene = new Scene(layout, 1300, 900);

        instructorScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                backButton.fire();
                event.consume();
            }
        });

        stage.setScene(instructorScene);
    }
}