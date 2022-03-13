package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry extends Intersectable {
    /**
     * calculates and returns the normal vector from the shape
     * @param point {@link Point} external to the shape
     * @return normal vector {@link Vector}
     */
    Vector getNormal(Point point);
}
