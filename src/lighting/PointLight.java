package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light{


    public PointLight(Color intensity, Point position,) {
        super(intensity);
        Position = position;
    }

    final Point Position;
    final double kC=1;
    final double kL=0;
    final double kQ=0;

    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }

    public Color getIntensity(Point P)
    {
        // but kL and Kq are 0
        double calc=(kC+Position.distance(P))*kL+(Position.distanceSquared(P));
        return super.getIntensity().reduce(calc);
    }


}
