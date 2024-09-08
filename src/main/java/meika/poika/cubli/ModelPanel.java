package meika.poika.cubli;

import javax.swing.*;
import java.awt.*;

public class ModelPanel extends JPanel{

    private matikkaMatskut model;

    public ModelPanel() {
        model = new matikkaMatskut();
    }

    public void update() {
        model.updateAngles();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.fillRect(0, 0, width, height);
        model.render(g2d, halfWidth, halfHeight);
    }


}
