package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Sphere implements Geometry{
    final Point _center;
    final double radius;

    public Sphere(Point center, double radius) {
        this._center = center;
        this.radius = radius;
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
                ", radius=" + radius +
                '}';
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
