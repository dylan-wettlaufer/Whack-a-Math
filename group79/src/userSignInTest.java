import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Default constructor for userSignInTest.
 * Initializes a new instance of userSignInTest.
 */
public class userSignInTest {

    /**
     * Initializes JavaFX environment to run the test.
     */
    static {
        new JFXPanel();
    }

    @Test
    void testUserSignInShowMethod() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                userSignIn signIn = new userSignIn();
                Stage testStage = new Stage();
                signIn.show(testStage);
                assertNotNull(testStage.getTitle());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
    }
}
