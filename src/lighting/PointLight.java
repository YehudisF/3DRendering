package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    private final Point position;
    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    public PointLight(Color intensity, Point _position) {
        super(intensity);
        position = _position;
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }

    protected double intensityHelp(Point p) {
        double ds = p.distanceSquared(position);
        double d = p.distance(position);
        return (kC + d * kL + ds * kQ);
    }

    public Color getIntensity(Point p) {
        // but kL and Kq are 0
        double denominator = intensityHelp(p);
        return super.getIntensity().reduce(denominator);
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point p) {
        return position.distance(p);
    }


}
