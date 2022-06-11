package lighting;

import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

public class PointLight extends Light implements LightSource {

    private final Point position;
    private Double3 kC = Double3.ONE;
    private Double3 kL = Double3.ZERO;
    private Double3 kQ = Double3.ZERO;

    private static final Random RND = new Random();

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

    /**
     * add description here!!!
     * @param p beginnig point
     * @param radius
     * @param amount
     * @return
     */
    @Override
    public List<Vector> getBeamL(Point p, double radius, int amount) {
        if (p.equals(position)) {
            return null;
        }
        LinkedList<Vector> beam = new LinkedList<>();

        //from pointlight position to p point
        Vector v = this.getL(p);
        beam.add(v);
        if (amount <= 1) {
            return beam;
        }

        double distance = this.position.distance(p);

        if (isZero(distance)) {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        Point lightHead = new Point(v.getX(),v.getY(),v.getZ());
        Vector normX;

        if (isZero(lightHead.getX()) && isZero(lightHead.getY())) {
            normX = new Vector(lightHead.getZ() * -1, 0, 0).normalize();
        } else {
            normX = new Vector(lightHead.getY() * -1, lightHead.getX(), 0).normalize();
        }

        Vector normY = v.crossProduct(normX).normalize();
        double cosTheta;
        double sinTheta;

        double d;
        double x;
        double y;

        for (int counter = 0; counter < amount; counter++) {
            Point newPoint = new Point(this.position);
            // randomly coose cosTheta and sinTheta in the range (-1,1)
            cosTheta = 2 * RND.nextDouble() - 1;
            sinTheta = Math.sqrt(1d - cosTheta * cosTheta);

            //d ranged between -radius and  +radius
            d = radius * (2 * RND.nextDouble() - 1);
            //d ranged between -radius and  +radius
            if (isZero(d)) { //Thanks to Michael Shachor
                counter--;
                continue;
            }
            x = d * cosTheta;
            y = d * sinTheta;

            if (!isZero(x)) {
                newPoint = newPoint.add(normX.scale(x));
            }
            if (!isZero(y)) {
                newPoint = newPoint.add(normY.scale(y));
            }
            beam.add(p.subtract(newPoint).normalize());
        }
        return beam;
    }

    @Override
    public double getDistance(Point p) {
        return position.distance(p);
    }


}
