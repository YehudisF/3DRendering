package geometries;
import primitives.*;
import java.util.List;

/**
 * common interface for all graphic objects that intersect with ray {@link Ray}
 */
public interface Intersectable {
    /**
     * find all intersection points from the ray
     * @param ray pointing towards the graphic object
     * @return immutable List of intersection points {@link Point}
     */
    List<Point> findIntersections(Ray ray);
}
