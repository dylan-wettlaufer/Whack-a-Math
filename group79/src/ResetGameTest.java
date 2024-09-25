import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResetGameTest {

    @Test
    void show() {
        new JFXPanel();
        User user = new User("dw", 50, 3);
        Platform.runLater(() -> {
            Stage stage = new Stage();
            ResetGame instance = new ResetGame(user);
            int expectedLevel = 1;
            instance.show(stage);
            int result = user.getLvl();
            assertEquals(expectedLevel, result);
        });
    }
}