import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelpTest {
    @Test
    void showHelp() {
        System.out.println("Testing showHelp()");
        new JFXPanel();
        User user = new User("name", 100, 3);
        Platform.runLater(()-> {
            Stage stage = new Stage();
            Help instance = new Help(user);
            instance.showHelp(stage);
            int expectedHS = 100;
            int result = user.getHighscore();
            assertEquals(expectedHS, result);
        });
    }
}