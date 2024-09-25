import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class instructorDashTest {
    @Test
    void showInstructor() {
        System.out.println("Testing instructor Test");
        new JFXPanel();
        User user1 = new User("name", 120, 3);
        Platform.runLater(()-> {
            Stage stage = new Stage();
            InstructorDashboard instance = null;
            try {
                instance = new InstructorDashboard(user1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            instance.showDash(stage);
            int expectedHS = 120;
            int result = user1.getHighscore();
            assertEquals(expectedHS, result);
        });
    }
}