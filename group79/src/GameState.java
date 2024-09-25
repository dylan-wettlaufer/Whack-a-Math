import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
/**
 * Game State used for displaying and running the game.
 * <p>
 * The game is run using the {@link #show(Stage)} method. This method uses an Animation Timer, the
 * {@link #createMoles(int, List, List, List, List, List, List, List, List, Label)}, and the
 * {@link #resetHoles()} methods to create the main game play mechanics.
 * </p>
 * <b>Example Use:</b>
 * <pre>
 * { @code
 *          gameState = new gameState(user.getLvl(), user);
 *          gameState.show(stage);
 * }
 * <pre>
 * @version 1.0.1b
 * @author Dylan Wettlaufer
 * @author Salim Terzout
 * @author Josh Reinhart
 * @author Adam Kahloon
 * @author Travis Braun
 */
public class GameState {
    /** The root of the screen */
    private StackPane root;
    /** The scene of the screen */
    private Scene theScene;
    /** The game canvas */
    private Canvas canvas;
    /** The Graphical Context of the game */
    private GraphicsContext gc;
    /** Arrays to hold the moles coordinates and their associated answer */
    private int[] answerArray, colArray, rowArray;
    /** Integers to store the coordinates of teh clicked button, the answer to the question, and the number of questions till the next level */
    private int answerRow, answerCol, result, questionsTillNextLevel;
    /** The grid used to hold the buttons */
    private GridPane gameGrid;
    /** Images for the holes and the moles */
    Image holeImage, moleImage;
    /** boolean for when a button is clicked */
    AtomicBoolean buttonClicked;
    /** Represents the player playing the game */
    Player player;
    /** The start time of the game */
    long[] startTime;
    /** The labels associated with showing the score, question, lives, timer, level, snd next level */
    Label scoreLabel, questionLabel, timerLabel, livesLabel, levelLabel, nextLevelLabel;
    private User user;

    /**
     * GameState constructor. Creates a new game state and initializes variables
     *
     * @param level the level that is to be played
     * @param user the user that is playing the game
     * */
    public GameState(int level, User user) {

        this.holeImage = new Image(getClass().getClassLoader().getResourceAsStream("Hole.png"));
        this.buttonClicked = new AtomicBoolean(false);

        this.gameGrid = new GridPane();
        this.root = new StackPane();

        this.player = new Player(0, 3, level);

        this.startTime = new long[]{System.currentTimeMillis()};
        this.timerLabel = new Label();
        this.questionLabel = new Label();
        this.scoreLabel = new Label();
        this.livesLabel = new Label();
        this.levelLabel = new Label();
        this.nextLevelLabel = new Label();

        questionsTillNextLevel = 10 + (5 * player.getLevel());
        this.user = user;

        System.out.println(user.getUsername() + "Is on the gameState screen");
    }

    /**
     * Returns the button with the given row and column, so it can have its image turned into a mole
     *
     * @param row row the button
     * @param col column of the button
     * @return the button with the given row and column
     *
     * */
    private Button getButton(int row, int col) {
        for (Node button : gameGrid.getChildren()) {
            if (GridPane.getRowIndex(button) == row && GridPane.getColumnIndex(button) == col) {
                return (Button) button;
            }
        }
        return null;
    }

    /**
     * Resets all the images back to holes
     * @see #show(Stage)
     *
     * */
    private void resetHoles() {
        Image holeImage = new Image(getClass().getClassLoader().getResourceAsStream("Hole.png"));

        for (Node hole : gameGrid.getChildren()) { // loop for each button in the grid
            ImageView imageView = new ImageView(holeImage);
            imageView.setFitWidth(170);
            imageView.setFitHeight(170);
            Button holeButton = (Button) hole;
            holeButton.setGraphic(imageView); // updates the image to a hole
        }
    }

    /** Randomly chooses three buttons on the grid to have its images changed to moles. These images of a random answer assigned to them.
     * One of the answers is correct and is stored in an array to keep track
     *
     * @param level the level of the game
     * @param questions1 question set for level 1
     * @param answers1 answer set for level 1
     * @param questions2 question set for level 2
     * @param answers2 answer set for level 2
     * @param questions3 question set for level 3
     * @param answers3 answer set for level 3
     * @param questions4 question set for level 4
     * @param answers4 answer set for level 4
     * @param label label to display the question the player needs to answer
     *
     * */

    private void createMoles(int level, List<String> questions1, List<Integer> answers1, List<String> questions2, List<Integer> answers2, List<String> questions3, List<Integer> answers3, List<String> questions4, List<Integer> answers4, Label label) {
        // arrays to store the cols and rows of the chosen buttons
        colArray = new int[3];
        rowArray = new int[3];
        answerArray = new int[3]; // an array to hold the answer for each of the chosen buttons
        Random answer = new Random();
        String question = "";

        if (level == 1){ // load addition question set
            int randomVal = answer.nextInt(questions1.size());
            question = questions1.get(randomVal);
            result = answers1.get(randomVal);
            label.setText(question);
        }else if(level == 2) { // load subtraction question set
            int randomVal = answer.nextInt(questions2.size());
            question = questions2.get(randomVal); // gets random question
            result = answers2.get(randomVal); // gets the questions answer
            label.setText(question);

        } else if (level == 3){ // load multiplication question set
            int randomVal = answer.nextInt(questions3.size());
            question = questions3.get(randomVal);
            result = answers3.get(randomVal);
            label.setText(question);
        } else if (level == 4) {
            int randomVal = answer.nextInt(questions4.size());
            question = questions4.get(randomVal);
            result = answers4.get(randomVal);
            label.setText(question);
        }



        answerArray[0] = result; // stores the correct answer into the array
        //answerArray[1] = 0;
        for (int i = 1; i < 3; i++) { // for loop creates alternative answers that are incorrect
            int otherAnswer = answer.nextInt((result+5)-(result-5)) + (result-5); // produces an incorrect answer
            while (otherAnswer == result || otherAnswer == answerArray[1] ||  otherAnswer < 0) {
                otherAnswer = answer.nextInt((result+5)-(result-5)) + (result-5);
            }
            answerArray[i] = otherAnswer; // adds the incorrect answer to the array
        }

        int i = 0;
        while (i != 3) { // loop to select new buttons to act as potential answers
            Random row = new Random();
            Random col = new Random();
            int rowNumber = row.nextInt(3); // random row for the button
            int colNumber = col.nextInt(3); // random col for the button

            boolean exists = false; // boolean for if the button's row and col are in the respective arrays
            for (int j = 0; j < rowArray.length; j++) {
                if (rowArray[j] == rowNumber && colArray[j] == colNumber) { // checks if the buttons coordinates are in array
                    exists = true; // if the button has already been added to the arrays
                    break;
                }
            }
            if (!exists) { // if the button isn't in the array, add it to the arrays
                colArray[i] = colNumber;
                rowArray[i] = rowNumber;

                Button randomButton = getButton(rowNumber, colNumber); // get the button using the col and row
                moleImage = new Image(getClass().getClassLoader().getResourceAsStream("mole" + answerArray[i] + ".png"));
                ImageView imageView = new ImageView(moleImage);
                randomButton.setGraphic(imageView); // update button image with given graphic
                i++; // increment counter to tell while loop that a button has been added
            }

        }
    }

    /**
     * Used to run the game
     * 
     * <p>
     * Longer description. <i>Game reads the Questions.txt file and loads up the different question lists.
     * game sets up grid and builds the different buttons and their event handlers.
     * When the game starts the animation timer is used to display how much time the user has to guess.
     * The player then plays the game until their lives run out</i>
     * <p>
     * 
     * @param primaryStage the stage to be displayed
     * @see #initializeButtons() 
     * @see #resetHoles() 
     * @see #createMoles(int, List, List, List, List, List, List, List, List, Label) 
     * @see #getButton(int, int) 
     * @see #initializeLabels() 
     * @see #readQuestionsFromFile(List, List, List, List, List, List, List, List)
     * */
    public void show(Stage primaryStage) throws Exception {
        List<String> questions1 = new ArrayList<>();
        List<Integer> answers1 = new ArrayList<>();
        List<String> questions2 = new ArrayList<>();
        List<Integer> answers2 = new ArrayList<>();
        List<String> questions3 = new ArrayList<>();
        List<Integer> answers3 = new ArrayList<>();
        List<String> questions4 = new ArrayList<>();
        List<Integer> answers4 = new ArrayList<>();
        readQuestionsFromFile(questions1, answers1, questions2, answers2, questions3, answers3, questions4, answers4);


        primaryStage.setTitle("WELCOME TO WHACKA-MATH!!!");

        AudioInputStream audioCorrect = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("correct.wav"));
        AudioInputStream audioIncorrect = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("incorrect.wav"));
        Clip correctClip = AudioSystem.getClip();
        Clip incorrectClip = AudioSystem.getClip();
        correctClip.open(audioCorrect);
        incorrectClip.open(audioIncorrect);

        FloatControl gainControlCorrect = (FloatControl) correctClip.getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControlIncorrect = (FloatControl) incorrectClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControlIncorrect.setValue(-25);
        gainControlCorrect.setValue(-25);




        initializeButtons();

        gameGrid.setAlignment(Pos.CENTER);
        gameGrid.setHgap(40);
        gameGrid.setVgap(40);

        theScene = new Scene(root);


        primaryStage.setScene(theScene);

        canvas = new Canvas(1300, 900);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        Image background = new Image(getClass().getClassLoader().getResourceAsStream("level" + player.getLevel() + ".jpg"));


        root.getChildren().add(gameGrid);
        gc.drawImage(background, 0, 0);

        initializeLabels();

        final int[] questionCounter = {0};
        final long[] totalDuration = {6000}; // length of the timer
        final boolean[] start = {true};
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (start[0]) { // runs when the game first starts
                    this.stop();

                    // adds a question to the screen
                    createMoles(player.getLevel(), questions1, answers1, questions2, answers2, questions3, answers3, questions4, answers4, questionLabel);
                    start[0] = false; // makes start false so this code doesn't run again
                    this.start();
                }

                long elapsedTime = System.currentTimeMillis() - startTime[0];
                long remainingTime = totalDuration[0] - elapsedTime; // Calculate remaining time
                long remainingSeconds = Math.max(0, remainingTime / 1000); // Ensure non-negative seconds
                timerLabel.setText("0:0" + remainingSeconds);

                if (elapsedTime >= totalDuration[0] - 1000) { // when time runs out
                    resetHoles();
                    startTime[0] = System.currentTimeMillis(); // resets timer
                    player.setLives(player.getLives() - 1);
                    incorrectClip.setFramePosition(0);
                    incorrectClip.start();
                    if (player.getLives() == 0) {
                        incorrectClip.stop();
                        incorrectClip.close();
                        this.stop();
                        ResultsScreen screen = new ResultsScreen(player.getScore(), player.getLevel(), questionsTillNextLevel, user);
                        screen.show(primaryStage);
                    }
                    questionCounter[0]++;
                    if (questionCounter[0] == 15 && totalDuration[0] != 3000) {
                        questionCounter[0] = 0;
                        totalDuration[0] = totalDuration[0] - 1000;
                    }
                    livesLabel.setText(String.valueOf("Lives: " + player.getLives()));
                    createMoles(player.getLevel(), questions1, answers1, questions2, answers2, questions3, answers3, questions4, answers4, questionLabel);

                } else if (buttonClicked.get()) { // when a button is clicked this becomes true
                    this.stop();
                    boolean clickedMole = false;
                    for (int i = 0; i < 3; i++) {
                        if (colArray[i] == answerCol && rowArray[i] == answerRow) { // checks if the mole clicked is in the array
                            clickedMole = true; //if it is this becomes true
                            if (result == answerArray[i]) { // checks if the clicked moles corresponding answer is equal to the questions result
                                player.setScore(player.getScore()+(int)remainingSeconds); // player clicked the right button and points are added based off how quick they answered
                                scoreLabel.setText("Score: " + player.getScore());
                                correctClip.setFramePosition(0);
                                correctClip.start();

                                if (questionsTillNextLevel > 0)  {
                                    questionsTillNextLevel--;
                                    if (questionsTillNextLevel == 0 && player.getLevel() == 4) nextLevelLabel.setText("GAME COMPLETE");
                                    else if (questionsTillNextLevel == 0) nextLevelLabel.setText("NEXT LEVEL UNLOCKED");
                                    else nextLevelLabel.setText(questionsTillNextLevel + " TILL NEXT LEVEL");
                                }

                                break;
                            } else { // player idn;t click the right button and a life is removed
                                player.setLives(player.getLives()-1);
                                livesLabel.setText("Lives: " + player.getLives());
                                incorrectClip.setFramePosition(0);
                                incorrectClip.start();

                            }
                        }
                    }
                    if (!clickedMole) { // if the player didn't click a button that is the array a life is removed
                        player.setLives(player.getLives()-1);
                        livesLabel.setText("Lives: " + player.getLives());
                        incorrectClip.setFramePosition(0);
                        incorrectClip.start();
                    }
                    if (player.getLives() == 0) {
                        incorrectClip.stop();
                        incorrectClip.close();
                        this.stop();
                        ResultsScreen screen = new ResultsScreen(player.getScore(), player.getLevel(), questionsTillNextLevel, user);
                        screen.show(primaryStage);
                    }
                    resetHoles(); // resets the holes for the next question
                    startTime[0] = System.currentTimeMillis(); // resets the timer

                    questionCounter[0]++;
                    if (questionCounter[0] == 15 && totalDuration[0] != 3000) {
                        questionCounter[0] = 0;
                        totalDuration[0] = totalDuration[0] - 1000;
                    }

                    //creates an all new question set with answers
                    createMoles(player.getLevel(), questions1, answers1, questions2, answers2, questions3, answers3, questions4, answers4, questionLabel);

                    buttonClicked.set(false);
                    this.start();

                }
            }
        }.start();

        primaryStage.setScene(theScene);
        primaryStage.show();

        Platform.runLater(() -> {
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
        });
    }
    
    /** 
     * Reads the context of the questions text file and stores it into lists based off of level.
     * 
     * @param q1 question list for level 1
     * @param a1 answer list for level 1
     * @param q2 question list for level 2
     * @param a2 answer list for level 2
     * @param q3 question list for level 3
     * @param a3 answer list for level 3
     * @param q4 question list for level 4
     * @param a4 answer list for level 4
     * */

    private void readQuestionsFromFile(List<String> q1, List<Integer> a1, List<String> q2, List<Integer> a2, List<String> q3, List<Integer> a3, List<String> q4, List<Integer> a4) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Questions.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            int Level = 0;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("Level") && Level == 1) {
                    String[] pieces = line.split("\\?");
                    q1.add(pieces[0]);
                    int pieces1 = Integer.parseInt(pieces[1].trim());
                    a1.add(pieces1);
                } else if (!line.contains("Level") && Level == 2) {
                    String[] pieces = line.split("\\?");
                    q2.add(pieces[0]);
                    int pieces2 = Integer.parseInt(pieces[1].trim());
                    a2.add(pieces2);
                } else if (!line.contains("Level") && Level == 3) {
                    String[] pieces = line.split("\\?");
                    q3.add(pieces[0]);
                    int pieces3 = Integer.parseInt(pieces[1].trim());
                    a3.add(pieces3);
                } else if (!line.contains("Level") && Level == 4) {
                    String[] pieces = line.split("\\?");
                    q4.add(pieces[0]);
                    int pieces4 = Integer.parseInt(pieces[1].trim());
                    a4.add(pieces4);
                } else if (line.contains("Level")) {
                    Level++;
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Input error reading questions file");
        }
    }

    
    /** Create the buttons and game grid. creates event handlers for buttons 
     * 
     * @see #show(Stage) 
     * */

    private void initializeButtons(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                Button hole = new Button();
                ImageView imageView = new ImageView(holeImage);
                imageView.setPreserveRatio(true);

                imageView.setFitWidth(170);
                imageView.setFitHeight(170);

                hole.setGraphic(imageView);
                hole.setMinSize(200, 200);
                hole.setMaxSize(200, 200);
                hole.setPrefSize(200, 200);

                hole.setStyle("-fx-font-size: 32px;");
                hole.setStyle("-fx-background-color: transparent;");
                gameGrid.add(hole, j, i);

                hole.setOnAction(value -> {
                    Button button = (Button) value.getSource(); // Get the button that triggered the event
                    answerRow = GridPane.getRowIndex(button);
                    answerCol = GridPane.getColumnIndex(button);
                    buttonClicked.set(true);
                });
            }
        }
    }
    
    /** 
     * Create labels for timer, lives, question, score, level, and next level
     * 
     * @see #show(Stage) 
     * */

    private void initializeLabels(){
        timerLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-font-weight: bold"); // Set font size and text color
        timerLabel.setTranslateY(-370);
        timerLabel.setTranslateX(-500);
        root.getChildren().add(timerLabel);

        livesLabel.setText(String.valueOf("LIVES: " + player.getLives()));
        livesLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-font-weight: bold"); // Set font size and text color
        livesLabel.setTranslateY(350);
        livesLabel.setTranslateX(500);
        root.getChildren().add(livesLabel);

        questionLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-font-weight: bold");
        questionLabel.setTranslateY(-370);
        questionLabel.setTranslateX(20);
        root.getChildren().add(questionLabel);

        scoreLabel.setText("SCORE: 0");
        scoreLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-font-weight: bold");
        scoreLabel.setTranslateY(350);
        scoreLabel.setTranslateX(-500);
        root.getChildren().add(scoreLabel);

        levelLabel.setText("LEVEL " + player.getLevel());
        levelLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-font-weight: bold;");
        levelLabel.setTranslateY(-370);
        levelLabel.setTranslateX(500);
        root.getChildren().add(levelLabel);

        nextLevelLabel.setText(questionsTillNextLevel + " TILL NEXT LEVEL");
        nextLevelLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: black; -fx-font-weight: bold");
        nextLevelLabel.setTranslateY(390);
        root.getChildren().add(nextLevelLabel);

    }

}