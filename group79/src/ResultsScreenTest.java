import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import javafx.embed.swing.JFXPanel;

import static org.junit.jupiter.api.Assertions.*;

class ResultsScreenTest {

    @Test
    void show() {
        new JFXPanel();
        User user = new User("dw", 50, 1);
        Platform.runLater(()-> {
            Stage stage = new Stage();
            ResultsScreen instance = new ResultsScreen(50, 1, 0, user);
            int expectedLevel = 2;
            instance.show(stage);
            int result = user.getLvl();
            assertEquals(expectedLevel, result);
        });
    }
}