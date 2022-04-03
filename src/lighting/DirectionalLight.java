package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light{
    /**
     * @param intensity
     */

    final Vector Direction;

    /**
     *
     * @param intensity
     * @param direction
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        Direction = direction;
    }

    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }

    public Vector getL(Point p)
    {
        return Direction;
        //TODO
    }
}
