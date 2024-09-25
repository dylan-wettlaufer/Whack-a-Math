import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.IOException;

/**
 * SignInMain displays the sign-in page in the game where users will input their username to be displayed on the leaderboard
 * <p>
 *     The page is run using the {@link #start(Stage)} method
 * </p>
 * <b>Example Use: </b>
 * <pre>
 *     SignInMain signIn = new SignInMain();
 *     signIn.start(stage);
 * </pre>
 * @version 1.0.1b
 * @author Adam Kahloun
 * @author Travis Braun
 * @author Salim Terzout
 * @author Joshua Reinhardt
 * @author Dylan Wettlaufer
 * */

public class SignInMain extends Application {
    /** instance of userSignIn */
    private userSignIn signInPage;
    /** initializing signed in status to false */
    private boolean signedIn = false;
    /** canvas for display to be drawn on */
    private Canvas canvas;
    /** graphics context to help assemble elements */
    private GraphicsContext gc;
    /** clip to import audio clip to file */
    private Clip clip;
    /** volume slider for user to adjust audio level */
    private Slider volumeSlider;
    /** box to contain volume slider */
    private HBox sliderBox;

    /**
     * SignInMain used to initialize the object and update current screen
     */
    public SignInMain() {
        System.out.println("Initial login");
    }

    /**
     * start used to build the screen and initialize all buttons, labels, and functional parts of the screen
     * also loads audio files
     * @param stage current stage being used
     * @throws Exception to catch IO exception with image and audio files
     * this is the main file of the class that contains most of its functionality
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Whacka-Math");

        this.sliderBox = new HBox();
        sliderBox.setTranslateY(350);
        sliderBox.setTranslateX(580);
        sliderBox.setSpacing(20);

        this.volumeSlider = new Slider(0.5, 1, 0.5);
        volumeSlider.setTranslateY(-100);
        volumeSlider.setTranslateX(-150);

        AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("audio.wav"));
        this.clip = AudioSystem.getClip();
        clip.open(audio);

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-50);

        clip.start();

        clip.loop(Clip.LOOP_CONTINUOUSLY);

        volumeSlider.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * newValue.floatValue()) + gainControl.getMinimum() - 1;
            gainControl.setValue(gain);
        }));

        StackPane root = new StackPane();
        Scene mainScene = new Scene(root);
        canvas = new Canvas(1300,900);
        gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);


        Label titleLabel = new Label("WELCOME TO WHACKA-MATH !");

        titleLabel.setStyle("-fx-font-size: 70px; -fx-text-fill: black; -fx-font-weight: bold"); // Set font size

        Button switchToSignInBtn = new Button("Sign In");
        switchToSignInBtn.setStyle("-fx-font-size: 30px; -fx-text-fill: #51361A; -fx-background-color: F9D849; -fx-border-color: #51361A;");


        switchToSignInBtn.setOnAction(e -> {
            try {
                signInPage = new userSignIn();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            signInPage.show(stage);
        });


        Image backgroundImage = new Image(getClass().getClassLoader().getResourceAsStream("cave_background.jpg"));


        titleLabel.setTranslateY(-335);
        root.setPadding(new javafx.geometry.Insets(20, 0, 0, 0));

        gc.drawImage(backgroundImage,0,0);

        sliderBox.getChildren().addAll(switchToSignInBtn, volumeSlider);
        root.getChildren().addAll(titleLabel, sliderBox);



        stage.setScene(mainScene);

        stage.show();
    }
/** used to launch game
 * @param args to print
 * */
    public static void main(String[] args) {
        launch(args);
    }

}