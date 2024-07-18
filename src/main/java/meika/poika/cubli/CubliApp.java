package meika.poika.cubli;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;

public class CubliApp {
    private static final int PIN_LED = 22; // PIN 15 = BCM 22

    public static void main(String[] args) throws InterruptedException {

        // --- Config ---

        // Configure default logging level, accept a log level as the first program argument
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "INFO");


        System.out.println("Hello, Cubli!");

        Context pi4j = Pi4J.newAutoContext();
        DigitalOutput led = pi4j.digitalOutput().create(PIN_LED);
        int pressCount = 0;
        while (pressCount < 5) {
            if (led.state() == DigitalState.HIGH) {
                led.low();
            } else {
                led.high();
            }
            Thread.sleep(500 / (pressCount + 1));
            pressCount++;
        }

        pi4j.shutdown();
    }
}
