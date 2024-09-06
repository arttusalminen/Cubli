package meika.poika.cubli;

import java.awt.*;

public class PolygonObject {
    Polygon p;
    Color c;
    double AvgDist = 0;

    public PolygonObject(double[] x, double[] y, Color c){

        Screen.NumberOfPolygons++;
        p = new Polygon();
        for (int i = 0; i < x.length; i++)
            p.addPoint((int)x[i], (int)y[i]);
        this.c = c;
    }

    void drawPolygon(Graphics g){
        g.setColor(c);
        g.fillPolygon(p);
        g.setColor(Color.BLACK);
        g.drawPolygon(p);
    }
}
