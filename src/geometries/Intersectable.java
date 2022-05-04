package geometries;
import primitives.*;
import java.util.List;
import java.util.Objects;

/**
 * common interface for all graphic objects that intersect with ray {@link Ray}
 */
public abstract class Intersectable {
    /**
     * find all intersection points from the ray
     *
     * @param ray pointing towards the graphic object
     * @return immutable List of intersection points {@link Point}
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }


//    public  List<GeoPoint> findGeoIntersections(Ray ray)    {
//      return findGeoIntersectionsHelper(ray);
//    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }




    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);



   // protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    public static class GeoPoint {
        public final Geometry geometry;
        public final Point point;


        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

       public double getX(){
            return point.getX();
       }
       public double getY(){
            return point.getY();
       }

    }
}
