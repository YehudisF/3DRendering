package lighting;

import primitives.Color;
import primitives.Point;

public class PointLight extends Light{

    /**
     *
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        Position = position;
    }
    /**
     * @param intensity
     */

    final Point Position;
    final double kC=1;
    final double kL=0;
    final double kQ=0;

    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }
}
