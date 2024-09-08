package meika.poika.cubli;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
//import com.raspoid.additionalcomponents.MPU6050;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CubliApp {
    private static final int PIN_LED = 22; // PIN 15 = BCM 22

    private static Logger LOG = LoggerFactory.getLogger(CubliApp.class);
    public static Context PI4J_CONTEXT = Pi4J.newContextBuilder().add(LinuxFsI2CProvider.newInstance()).build();

    public static MPU6050 mpu6050 = new MPU6050();

    public static void main(String[] args) throws InterruptedException {

        // --- Config ---

        // Configure default logging level, accept a log level as the first program argument
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "INFO");

        System.out.println("Hello, Cubli!");

        JFrame frame = new JFrame("Motion tracking");
        mpu6050.startUpdatingThread();
        ModelPanel panel = new ModelPanel();
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    mpu6050.stopUpdatingThread();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        while (!mpu6050.isUpdatingThreadStopped()) {
            panel.update();
            Thread.sleep(100);
        }

        PI4J_CONTEXT.shutdown();



        //JFrame f = new MotionTracking();

        /*
        mpu6050.startUpdatingThread();
        OrientationVisualization orientationVisualization = new OrientationVisualization(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    mpu6050.stopUpdatingThread();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        while (!mpu6050.isUpdatingThreadStopped()) {
            orientationVisualization.updateAngles(mpu6050.getGyroAngles());
            Thread.sleep(100);
        }
        */
    }
}
