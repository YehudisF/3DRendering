package primitives;

import java.util.Objects;

public class Point {
    //package friendly
    public static final Point ZERO = new Point(0d, 0d, 0d);
    protected final Double3 xyz;

    public Point(Point p) {
        xyz = p.xyz;
    }

    @Override
    public String toString() {
        return "Point " + xyz;
    }

    /**
     * secondary constructor for Point 3D
     *
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
//        this(new Double3(x,y,z));
    }

    /**
     * primary constructor for point
     *
     * @param _xyz Double3 value gor x, y, z axis
     */
    public Point(Double3 _xyz) {
        xyz = _xyz;
    }


    /**
     * @param other
     * @return d = ((x2 = x1) * (x2 = x1) + (y2 - y1) * (y2 - y1)  + (z2 = z1 ) * (z2 = z1 ))
     */
    public double distanceSquared(Point other) {
        double x1 = xyz.d1;
        double y1 = xyz.d2;
        double z1 = xyz.d3;

        double x2 = other.xyz.d1;
        double y2 = other.xyz.d2;
        double z2 = other.xyz.d3;

        return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1) + (z2 - z1) * (z2 - z1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    /**
     * @param other
     * @return d=Sqrt(lengthSquare)
     * @link https://www.engineeringtoolbox.com/distance-relationship-between-two-points-d_1854.html
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    public Vector subtract(Point p1) {
        return new Vector(xyz.subtract(p1.xyz));
    }

    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }

    public double getZ() {return xyz.d3;}
}
