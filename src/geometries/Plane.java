package geometries;

import primitives.Point;
import primitives.Vector;

import java.util.Objects;

public class Plane implements Geometry {
    final private Point _q0;
    final private Vector _normal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return _q0.equals(plane._q0) && _normal.equals(plane._normal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_q0, _normal);
    }

    public Plane(Point q0, Vector normal) {
        _q0 = q0;
        _normal = normal;
    }

    public Plane(Point p1, Point p2, Point p3) {
        this._q0 = p1;
        //        //TODO check direction of vectors
//        Vector U = p1.subtract(p2);
//        Vector V = p3.subtract(p2);

        Vector U = p2.subtract(p1);
        Vector V = p3.subtract(p1);
        Vector N = U.crossProduct(V);
        //right hand rule
        this._normal = N.normalize();
    }

    public Point getQ0() {
        return _q0;
    }

    /**
     *  getter for _normal Vector
     * @return the infamous normal
     */
    public Vector getNormal() {
        return _normal;
    }

    /**
     *  implementation of {@link Geometry#getNormal(Point)}
     *
     * @param point external Point
     * @return normal to plane
     */
    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }
}
