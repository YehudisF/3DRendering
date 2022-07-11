package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import java.util.List;

public class DirectionalLight extends Light implements LightSource{
    /**
     * @param intensity
     */

    private final Vector direction;

    /**
     *
     * @param intensity
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }



    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    public Vector getL(Point p)    {
        return direction;
    }

    /**
     * gets the vectors of the light bam
     * @param dummyPoint3D
     * @param dummyRadius
     * @param dummyInt
     * @return
     */
    @Override
    public List<Vector> getBeamL(Point dummyPoint3D, double dummyRadius, int dummyInt) {
        return List.of(new Vector(new Double3(direction.getX(),direction.getY(),direction.getZ())));
    }

    /**
     * distance between light source and object
     * @param p
     * @return
     */
    @Override
    public double getDistance(Point p) {
        return Double.POSITIVE_INFINITY;
    }
}
