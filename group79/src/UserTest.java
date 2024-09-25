import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for User with JavaFX components.
 */
public class UserTest {

    /**
     * Initializes JavaFX environment to run the test.
     */
    static {
        new JFXPanel();
    }
    @Test
    void testUserJavaFXComponent() throws InterruptedException {
        System.out.println("Testing JavaFX component interaction with User");
        final CountDownLatch latch = new CountDownLatch(1);
        User user = new User("TestUser", 100, 1);
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                int expectedHighScore = 100;
                int actualHighScore = user.getHighscore();
                assertEquals(expectedHighScore, actualHighScore);

            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}
