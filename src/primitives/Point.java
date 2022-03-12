package primitives;

import java.util.Objects;

public class Point {
    //package friendly
   protected final Double3 _xyz;

    @Override
    public String toString() {
        return "Point "  + _xyz ;
    }

    /**
     * secondary constructor for Point 3D
     *
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        _xyz = new Double3(x,y,z);
//        this(new Double3(x,y,z));
    }
    /**
     * primary constructor for point
     * @param xyz Double3 value gor x, y, z axis
     */
    public Point(Double3 xyz) {
        _xyz = xyz;
    }


    /**
     *
     * @param other
     * @return d = ((x2 = x1) * (x2 = x1) + (y2 - y1) * (y2 - y1)  + (z2 = z1 ) * (z2 = z1 ))
     */
    public double distanceSquared(Point other)
    {
         double x1 = _xyz._d1;
         double y1 = _xyz._d2;
         double z1 = _xyz._d3;

         double x2 = other._xyz._d1;
         double y2 = other._xyz._d2;
         double z2 = other._xyz._d3;

         return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)  + (z2 - z1 ) * (z2 - z1 ));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return _xyz.equals(point._xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_xyz);
    }

    /**
     *
     * @param other
     * @return d=Sqrt(lengthSquare)
     * @link https://www.engineeringtoolbox.com/distance-relationship-between-two-points-d_1854.html
     *
     */
    public double distance(Point other)
    {
        return Math.sqrt(distanceSquared(other));
    }

    public Point add(Vector vector) {
        return new Point(_xyz.add(vector._xyz));
    }

    public Vector subtract(Point p1) {
        return new Vector(_xyz.subtract(p1._xyz));
    }
}
