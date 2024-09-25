import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordScreenTest {

    @Test
    void showPasswordScreen() {
        System.out.println("Testing showPasswordScreen()");
        new JFXPanel();
        User user = new User("name", 100, 3);
        Platform.runLater(()-> {
            Stage stage = new Stage();
            PasswordScreen instance = new PasswordScreen(user);
            instance.showPasswordScreen(stage, "DEBUG MODE");
            int expectedHS = 100;
            int result = user.getHighscore();
            assertEquals(expectedHS, result);
        });
    }
}