package meika.poika.cubli;

public class Vector {
    double x = 0, y = 0, z = 0;
    public Vector(double x, double y, double z){
        double Length = Math.sqrt(x*x + y*y + z*z);
        if (Length > 0){
            this.x = x/Length;
            this.y = y/Length;
            this.z = z/Length;
        }
    }

    Vector CrossProduct(Vector V){
        return new Vector(
                y * V.z - z * V.y,
                z * V.x - x * V.z,
                x * V.y - y * V.x);

    }
}
