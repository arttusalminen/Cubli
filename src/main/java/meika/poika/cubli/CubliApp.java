package meika.poika.cubli;
import com.pi4j.Pi4J;
import com.pi4j.context.Context;
import com.pi4j.io.gpio.digital.DigitalOutput;
import com.pi4j.io.gpio.digital.DigitalState;
//import com.raspoid.additionalcomponents.MPU6050;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;
import com.pi4j.plugin.linuxfs.provider.i2c.LinuxFsI2CProvider;
import com.pi4j.provider.impl.DefaultProviders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CubliApp {
    private static final int PIN_LED = 22; // PIN 15 = BCM 22

    private static Logger LOG = LoggerFactory.getLogger(CubliApp.class);
    public static Context PI4J_CONTEXT = Pi4J.newContextBuilder().add(LinuxFsI2CProvider.newInstance()).build();

    public static void main(String[] args) throws InterruptedException {

        // --- Config ---

        // Configure default logging level, accept a log level as the first program argument
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "INFO");
        MPU6050 mpu6050 = new MPU6050();
        System.out.println("Hello, Cubli!");
        mpu6050.startUpdatingThread();
        for (int i = 0; i < 20; i++) {
            // Accelerometer angles
            double[] accelAngles = mpu6050.getAccelAngles();

            double[] accelAccelerations = mpu6050.getAccelAccelerations();

            // Gyroscope angles
            double[] gyroAngles = mpu6050.getGyroAngles();

            double[] gyroAngularSpeeds = mpu6050.getGyroAngularSpeeds();

            // Filtered angles
            double[] filteredAngles = mpu6050.getFilteredAngles();

            LOG.info("Accel angles: x={}, y={}, z={}", accelAngles[0], accelAngles[1], accelAngles[2]);
            LOG.info("Accel accelerations: x={}, y={}, z={}", accelAccelerations[0], accelAccelerations[1], accelAccelerations[2]);
            LOG.info("Gyro angles: x={}, y={}, z={}", gyroAngles[0], gyroAngles[1], gyroAngles[2]);
            LOG.info("Gyro angular speeds: x={}, y={}, z={}", gyroAngularSpeeds[0], gyroAngularSpeeds[1], gyroAngularSpeeds[2]);
            LOG.info("Filtered angles: x={}, y={}, z={}", filteredAngles[0], filteredAngles[1], filteredAngles[2]);

            Thread.sleep(1000);
        }

//        pi4j.shutdown();
    }
}
