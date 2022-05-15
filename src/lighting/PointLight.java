package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    private final Point position;
    private Double3 kC = Double3.ONE;
    private Double3 kL = Double3.ZERO;
    private Double3 kQ = Double3.ZERO;

    public PointLight(Color intensity, Point _position) {
        super(intensity);
        position = _position;
    }


    public PointLight setkC(double kC) {
        this.kC = new Double3(kC);
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = new Double3(kL);
        return this;
    }

    public PointLight setkQ(double kQ) {

        this.kQ = new Double3(kQ);
        return this;
    }

    @Override
    public Color getIntensity() {
        return super.getIntensity();
    }

//    protected Double3 intensityHelp(Point p) {
//        double ds = p.distanceSquared(position);
//        double d = p.distance(position);
//        return (kC.add(kL.scale(d)).add( kQ.scale(ds)));
//    }

    public Color getIntensity(Point p) {
        // but kL and Kq are 0
        Color lightIntensity = getIntensity();

        double ds = p.distanceSquared(position);
        double d = p.distance(position);
        Double3 denominator = kC.add(kL.scale(d)).add( kQ.scale(ds));

        return lightIntensity.reduce(denominator);
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
