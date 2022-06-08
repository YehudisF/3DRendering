package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 *
 */
public interface LightSource {
    public Color getIntensity(Point p);

    public Vector getL(Point p);
    public List<Vector> getBeamL(Point p, double radius, int amount);

    double getDistance(Point p);

}
