package meika.poika.cubli;

import java.awt.*;

public class Poly3d {
    Color c;
    double[] x, y, z;
    int poly = 0;

    public Poly3d(double[] x, double[] y, double[] z, Color c) {
        Screen.NumberOf3DPolygons++;
        this.x = x;
        this.y = y;
        this.z = z;
        this.c = c;
        createPolygon();
    }

    public void createPolygon(){
        poly = Screen.NumberOfPolygons;
        Screen.DrawablePolygons[Screen.NumberOfPolygons] = new PolygonObject(new double[x.length] , new double[x.length], c);
        updatePolygon();
    }

    void updatePolygon(){
        double dx = - 50 * calculator3d.CalcPositionX(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0], Screen.ViewTo[1], Screen.ViewTo[2]);
        double dy = - 50 * calculator3d.CalcPositionY(Screen.ViewFrom, Screen.ViewTo, Screen.ViewTo[0], Screen.ViewTo[1], Screen.ViewTo[2]);
        double[] newX = new double[x.length];
        double[] newY = new double[x.length];
        for(int i = 0; i < newX.length; i++) {
            newX[i] = dx + (double) MotionTracking.ScreenSize.width / 2 + 50 * calculator3d.CalcPositionX(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
            newY[i] = dy + (double) MotionTracking.ScreenSize.height / 2 + 50 * calculator3d.CalcPositionY(Screen.ViewFrom, Screen.ViewTo, x[i], y[i], z[i]);
        }
        Screen.DrawablePolygons[poly] = new PolygonObject(newX, newY, c);
        Screen.DrawablePolygons[poly].AvgDist = GetDist();
        Screen.NumberOfPolygons--;
    }

    double GetDist(){
        double total = 0;
        for(int i = 0; i < x.length; i++)
            total += GetDistanceToP(i);
        return total /x.length;
    }

    double GetDistanceToP(int i){
        return Math.sqrt((Screen.ViewFrom[0] - x[i]) * (Screen.ViewFrom[0] - x[i]) +
                         (Screen.ViewFrom[1] - y[i]) * (Screen.ViewFrom[1] - y[i]) +
                         (Screen.ViewFrom[2] - z[i]) * (Screen.ViewFrom[2] - z[i]));

    }

}
