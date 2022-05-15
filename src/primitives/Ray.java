package primitives;

import geometries.Intersectable;
import geometries.Intersectable.GeoPoint;

import java.util.List;
import java.util.Objects;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Ray {
    private static final double DELTA = 0.1;

    /**
     * starting point of the ray
     */
    final Point p0;
    /**
     * ray normalized direction vector
     */
    final Vector dir;


    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    public Ray(Point point, Vector direction, Vector normal) {
        //point + normal.scale(±EPSILON)
        dir = direction.normalize();

        double nv = alignZero(normal.dotProduct(dir));

        Vector normalDelta = normal.scale((nv > 0 ? DELTA : -DELTA));
        p0 = point.add(normalDelta);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }

    public Point getPoint(double delta) {
        if (isZero(delta)) {
            return p0;
        }
        return p0.add(dir.scale(delta));
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints) {

        double minDistance = Double.POSITIVE_INFINITY;
        double pointDistance = 0d;
        GeoPoint closestPoint = null;
        for (GeoPoint geopoint : geoPoints) {
            pointDistance = geopoint.point.distanceSquared(p0);
            if (pointDistance < minDistance) {
                minDistance = pointDistance;
                closestPoint = geopoint;
            }
        }
        return closestPoint;
    }

    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points
                .stream()
                .map(p -> new GeoPoint(null, p))
                .toList())
                .point;
    }


}
