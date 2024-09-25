import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.*;

class DebugModeTest {
    @org.junit.jupiter.api.Test
    void showDebugMode() {
        System.out.println("Testing showDebugMode()");
        new JFXPanel();
        User user = new User("name", 100, 3);
        Platform.runLater(()-> {
            Stage stage = new Stage();
            DebugMode instance = new DebugMode(user);
            instance.showDebugMode(stage);
            int expectedHS = 100;
            int result = user.getHighscore();
            assertEquals(expectedHS, result);
        });
    }
}