import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Default constructor for PlayerTest
 * Initializes a new instance of PlayerTest
 */
public class PlayerTest {

    private Player player;

    /**
     * Initializes JavaFX environment to run the test.
     */
    static {
        new JFXPanel();
    }

    /**
     * Sets up the Player object before each test.
     */
    @BeforeEach
    void setUp() {
        player = new Player(100, 3, 1);
    }

    @Test
    void testPlayerJavaFXComponent() throws InterruptedException {
        System.out.println("Testing JavaFX component interaction with Player");
        final CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                int expectedScore = 100;
                int actualScore = player.getScore();
                assertEquals(expectedScore, actualScore);

            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}
