package meika.poika.cubli;

import java.awt.*;
import javax.swing.*;

public class MotionTracking extends JFrame{

    static Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Screen ScreenObject = new Screen();

    public MotionTracking(){
        add(ScreenObject);
        setUndecorated(true);
        setSize(ScreenSize);
        setVisible(true);
    }

}
