package meika.poika.cubli;

import java.awt.*;

import static meika.poika.cubli.CubliApp.mpu6050;

public class matikkaMatskut {

    private double angleX, angleY, angleZ;

    private int[][] vertexes = {
            {70, 50, 10},
            {-70, 50, 10},
            {-70, -50, 10},
            {70, -50, 10},
            {70, 50, -10},
            {-70, 50, -10},
            {-70, -50, -10},
            {70, -50, -10},
    };

    private int[][] edges = {
            {0, 1}, {1, 2}, {2, 3}, {3, 0},
            {4, 5}, {5, 6}, {6, 7}, {7, 4},
            {0, 4}, {1, 5}, {2, 6}, {3, 7}
    };

    public void updateAngles(){
        double[] angles = mpu6050.getGyroAngles();
        System.out.println(angles[1]);
        System.out.println(angles[0]);
        System.out.println(angles[2]);

        angleX = Math.toRadians(angles[1]);
        angleY = Math.toRadians(angles[0]);
        angleZ = Math.toRadians(angles[2]);
    }

    public void render (Graphics2D g2d, int halfWidth, int halfHeight) {
        g2d.setColor(Color.red);
        for(int[] edge : edges) {
            int x1 = vertexes[edge[0]][1];
            int x2 = vertexes[edge[1]][1];
            int y1 = vertexes[edge[0]][0];
            int y2 = vertexes[edge[1]][0];
            int z1 = vertexes[edge[0]][2];
            int z2 = vertexes[edge[1]][2];

            // rotation around x
            double tempY1 = y1 * Math.cos(angleX) - z1 * Math.sin(angleX);
            double tempY2 = y2 * Math.cos(angleX) - z2 * Math.sin(angleX);

            double tempZ1 = y1 * Math.sin(angleX) + z1 * Math.cos(angleX);
            double tempZ2 = y2 * Math.sin(angleX) + z2 * Math.cos(angleX);

            // rotation around y
            double tempX1 = x1 * Math.cos(angleY) - tempZ1 * Math.sin(angleY);
            double tempX2 = x2 * Math.cos(angleY) - tempZ2 * Math.sin(angleY);

            double tempZ1Y = x1 * Math.sin(angleY) + tempZ1 * Math.cos(angleY);
            double tempZ2Y = x2 * Math.sin(angleY) + tempZ2 * Math.cos(angleY);

            // rotation around z
            double tempX1Y = tempX1 * Math.cos(angleZ) - tempY1 * Math.sin(angleZ);
            double tempX2Y = tempX2 * Math.cos(angleZ) - tempY2 * Math.sin(angleZ);

            double tempY1Z = tempX1 * Math.sin(angleZ) + tempY1 * Math.cos(angleZ);
            double tempY2Z = tempX2 * Math.sin(angleZ) + tempY2 * Math.cos(angleZ);

            g2d.drawLine(halfWidth + (int) tempX1Y, halfHeight - (int) tempY1Z,
                         halfWidth + (int) tempX2Y, halfHeight - (int) tempY2Z);
        }
    }

}
