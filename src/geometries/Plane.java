package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    final Point _q0;
    final Vector _normal;

    public Point getQ0() {
        return _q0;
    }

    /**
     * getter of _normal field
     * @deprecated use (@link plane#getNormal(point3D with))
     * @return referance to normal
     */
//    @Deprecated
//    public Vector getNormal() {
//        return _normal;
//    }

    /**
     *
     * @param q0
     * @param normal
     */
    public Plane(Point q0, Vector normal) {
        _q0 = q0;
        _normal = normal.normalize();
    }

    /**
     * constructor of plane from 3 points on its surface
     * the points are ordered from right to left
     * forming an arc in the right direction
     * @param vertex
     * @param vertex1
     * @param vertex2
     */
    public Plane(Point vertex, Point vertex1, Point vertex2) {
        _q0 = vertex;
        Vector U = vertex1.subtract(vertex);
        Vector V = vertex2.subtract(vertex);
        Vector N = U.crossProduct(V);
        N.normalize();
        _normal = N;
    }

    /**
     * getter for normal fiels -nomral
     * @return
     */
    public Vector getNormal()
{
    return _normal;
}

    /**
     * get normal which implements getnormal from geometries
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point) {
        return getNormal();
    }

    @Override
    public String toString() {
        return "Plane{" +
                "_q0=" + _q0 +
                ", _normal=" + _normal +
                '}';
    }
}
