package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

public class Sphere extends Geometry {
    final Point _center;
    final double _radius;

    public Sphere(Point center, double _radius) {
        this._center = center;
        this._radius = _radius;
    }

    @Override
    public Vector getNormal(Point point) {
        Vector N=point.subtract(_center);
        return N.normalize();
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "_center=" + _center +
                ", _radius=" + _radius +
                '}';
    }

    /**
     * calculates number of intersections of ray with a sphere
     * @param ray pointing towards the graphic object
     * @return list of points
     */
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray)
    {

        Point P0 = ray.getP0();
        Vector v = ray.getDir();
        //if the ray is starting from precisely the center of the sphere
        // than we know the intersection point will be scaling the point by the radius
        if (P0.equals(_center))
        {
            return List.of(new GeoPoint(this, _center.add(v.scale(_radius))) );
        }

        Vector U = _center.subtract(P0); // The vector from p0 to the center of the sphere
        double tm =alignZero(v.dotProduct(U)); // the scalar for the projection of v on u
        double d = alignZero(Math.sqrt(U.lengthSquared() - tm * tm)); // the distance of the center to the ray

        // no intersections : the ray direction is above the sphere
        if (d >= _radius) {
            return null;
        }

        double th = alignZero(Math.sqrt(_radius * _radius - d * d)); // distance from p1 to intersection with d
        double t1 = alignZero(tm - th); // from p0 to p1
        double t2 = alignZero(tm + th);// from p0 to p2

        if (t1 > 0 && t2 > 0) // take only t > 0 (going in the right direction)
        {
//            Point P1 = P0.add(v.scale(t1));
//            Point P2 = P0.add(v.scale(t2));
            Point P1 =ray.getPoint(t1);
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this, P1),new GeoPoint(this, P2));
        }
        if (t1 > 0) {
//            Point P1 = P0.add(v.scale(t1));
            Point P1 =ray.getPoint(t1);
            return List.of(new GeoPoint(this, P1));
        }
        if (t2 > 0) {
//            Point P2 = P0.add(v.scale(t2));
            Point P2 =ray.getPoint(t2);
            return List.of(new GeoPoint(this, P2));
        }
        return null;

    }
}
