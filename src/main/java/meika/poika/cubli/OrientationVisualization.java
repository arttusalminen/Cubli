package meika.poika.cubli;

import javax.swing.*;
import java.awt.event.WindowListener;

public class OrientationVisualization extends JFrame {
    // angles in degrees
    private double[] angles = new double[3];

    /**
     * @param listener the listener to be called when the window is closed
     */
    public OrientationVisualization(WindowListener listener) {
        super("Cubli Orientation Visualization");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        this.addWindowListener(listener);
    }

    public void updateAngles(double[] filteredAngles) {
        this.angles = filteredAngles;
        System.out.println(angles[0]);
        System.out.println(angles[1]);
        System.out.println(angles[2]);
        repaint();
    }

    @Override
    public void paint(java.awt.Graphics g) {
        super.paint(g);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
        g2.translate(getWidth() / 2, getHeight() / 2);
        g2.scale(1, -1);
        g2.setColor(java.awt.Color.BLACK);
        g2.fillRect(-getWidth() / 2, -getHeight() / 2, getWidth(), getHeight());
        g2.setColor(java.awt.Color.WHITE);
        g2.drawLine(-getWidth() / 2, 0, getWidth() / 2, 0);
        g2.drawLine(0, -getHeight() / 2, 0, getHeight() / 2);
        g2.setColor(java.awt.Color.RED);
        g2.drawLine(0, 0, (int) (getWidth() / 2 * Math.cos(Math.toRadians(angles[0]))),
                (int) (getHeight() / 2 * Math.sin(Math.toRadians(angles[0]))));
        g2.setColor(java.awt.Color.GREEN);
        g2.drawLine(0, 0, (int) (getWidth() / 2 * Math.cos(Math.toRadians(angles[1]))),
                (int) (getHeight() / 2 * Math.sin(Math.toRadians(angles[1]))));
        g2.setColor(java.awt.Color.BLUE);
        g2.drawLine(0, 0, (int) (getWidth() / 2 * Math.cos(Math.toRadians(angles[2]))),
                (int) (getHeight() / 2 * Math.sin(Math.toRadians(angles[2]))));
    }
}
