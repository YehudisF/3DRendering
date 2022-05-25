package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

public class Cylinder extends Tube
{


    /**
     * Cylinder's height
     */
    protected final double height;
    /**
     * Cylinder's bottomCap.
     */
    protected final Plane bottomCap;
    /**
     * Cylinder's topCap.
     */
    protected final Plane topCap;

    /**
     * Creates a new cylinder by a given axis ray, radius and height.
     * @param axisRay The cylinder's axis ray.
     * @param radius The cylinder's radius.
     * @param h The cylinder's height.
     * @exception IllegalArgumentException When the radius or the height are less than 0 or equals 0.
     */
    public Cylinder(Ray axisRay, double radius, double h) {
        super(axisRay, radius);

        if (h <= 0)
        {
            throw new IllegalArgumentException("The height should be greater then 0");
        }

        height = h;
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getPoint(height);
        bottomCap = new Plane(p0, axisRay.getDir().scale(-1) /* Sets the normal directed outside of the cylinder */);
        topCap = new Plane(p1, axisRay.getDir());
    }

    /**
     * Returns the cylinder's height
     * @return the cylinder's height
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point p){
        // Finding the normal:
        // n = normalize(p - o)
        // t = v * (p - p0)
        // o = p0 + t * v

        Vector v= axisRay.getDir();
        Point p0 =axisRay.getP0();

        //if p=p0, then (p-p0) is zero vector
        //returns the vector of the base as a normal
        if(p.equals(p0)){
            return v.scale(-1);
        }

        double t= v.dotProduct(p.subtract(p0));
        //check if the point on the bottom
        if(isZero(t)){
            return v.scale(-1);
        }
        //check if the point on the top
        if(isZero(t-height)){
            return v;
        }

        Point o=p0.add(v.scale(t));
        return p.subtract(o).normalize();
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }
    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance)  {
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getPoint(height);
        List<GeoPoint> result = null;

        // Find the tube's intersections
        List<GeoPoint> tubePoints = super.findGeoIntersectionsHelper(ray,maxDistance);
        if (tubePoints != null) {
            if (tubePoints.size() == 2) {
                // Checks if the intersection points are on the cylinder
                GeoPoint q0 = tubePoints.get(0);
                GeoPoint q1 = tubePoints.get(1);
                boolean q0Intersects = isBetweenCaps(q0.point);
                boolean q1Intersects = isBetweenCaps(q1.point);

                if (q0Intersects && q1Intersects) {
                    return tubePoints;
                }

                if (q0Intersects) {
                    result = new LinkedList<>();
                    result.add(q0);
                } else if (q1Intersects) {
                    result = new LinkedList<>();
                    result.add(q1);
                }
            }

            if (tubePoints.size() == 1) {
                // Checks if the intersection point is on the cylinder
                GeoPoint q = tubePoints.get(0);
                if (isBetweenCaps(q.point)) {
                    result = new LinkedList<>();
                    result.add(q);
                }
            }
        }

        // Finds the bottom cap's intersections
        List<GeoPoint> cap0Point = bottomCap.findGeoIntersections(ray);
        if (cap0Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap0Point.get(0);
            if (gp.point.distanceSquared(p0) < radius * radius) {
                if (result == null) {
                    result = new LinkedList<>();
                }

                result.add(gp);
                if (result.size() == 2) {
                    return result;
                }
            }
        }

        // Finds the top cap's intersections
        List<GeoPoint> cap1Point = topCap.findGeoIntersections(ray);
        if (cap1Point != null) {
            // Checks if the intersection point is on the cap
            GeoPoint gp = cap1Point.get(0);
            if (gp.point.distanceSquared(p1) < radius * radius) {
                if (result == null) {
                    return List.of(gp);
                }

                result.add(gp);
            }
        }

        return result;
    }

    /**
     * Helper function that checks if a points is between the two caps (not on them, even on the edge)
     * @param p The point that will be checked.
     * @return True if it is between the caps. Otherwise, false.
     */
    private boolean isBetweenCaps(Point p) {
        Vector v0 = axisRay.getDir();
        Point p0 = axisRay.getP0();
        Point p1 = axisRay.getPoint(height);

        // Checks against zero vector...
        if (p.equals(p0) || p.equals(p1)) {
            return false;
        }

        return v0.dotProduct(p.subtract(p0)) > 0 &&
                v0.dotProduct(p.subtract(p1)) < 0;
    }





//    double height;
//    public Cylinder(Ray axisRay, double radius, double height)
//    {
//        super(axisRay, radius);
//        this.height = height;
//    }
//
//    public double getHeight() {
//        return height;
//    }
//
//    @Override
//    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
//        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray, maxDistance);
//        if (intersections == null) {
//            return null;
//        }
//        Vector va = this.axisRay.getDir();
//        Point A = this.axisRay.getP0();
//        Point B = this.axisRay.getPoint(height);
//        List<GeoPoint> intersectionsCylinder = new LinkedList<>();
//        double lowerBound, upperBound;
//        for (GeoPoint gPoint : intersections) {
//            lowerBound = va.dotProduct(gPoint.point.subtract(A));
//            upperBound = va.dotProduct(gPoint.point.subtract(B));
//            if (lowerBound > 0 && upperBound > 0) {
//                // the check for distance, if the intersection point is beyond the distance
//                if (alignZero(gPoint.point.distance(ray.getP0()) - maxDistance) <= 0)
//                    intersectionsCylinder.add(gPoint);
//            }
//        }
//        Plane topA = new Plane(A, va);
//        Plane bottomB = new Plane( B, va);
//        List<GeoPoint> intersectionPlaneA = topA.findGeoIntersections(ray, maxDistance);
//        List<GeoPoint> intersectionPlaneB = bottomB.findGeoIntersections(ray, maxDistance);
//        if (intersectionPlaneA == null && intersectionPlaneB == null) {
//            return intersectionsCylinder;
//        }
//        Point q3, q4;
//        if (intersectionPlaneA != null) {
//            q3 = intersectionPlaneA.get(0).point;
//            if (q3.subtract(A).lengthSquared() < radius * radius) {
//                intersectionsCylinder.add(intersectionPlaneA.get(0));
//            }
//        }
//        if (intersectionPlaneB != null) {
//            q4 = intersectionPlaneB.get(0).point;
//            if (q4.subtract(B).lengthSquared() < radius * radius) {
//                intersectionsCylinder.add(intersectionPlaneB.get(0));
//            }
//        }
//        if (intersectionsCylinder.isEmpty()) {
//            return null;
//        }
//        return intersectionsCylinder;
//    }
//
////    public List<GeoPoint> findGeoIntersectionHelper(Ray ray, double maxDistance)
////    {
////        //return super.findGeoIntersections(ray);
////
////    }
//     // changed it from helper to not helper
//
//
//    @Override
//    public String toString()
//    {
//        return "Cylinder{" +
//                "height=" + height +
//                ", axisRay=" + axisRay +
//                ", radius=" + radius +
//                '}';
//    }
//
//    /**
//     * yhr normal at a base is equal to central ray's direction vector or opposite
//     * @param point
//     * @return
//     */
//    @Override
//    public Vector getNormal(Point point)
//    {
//        //avigayil suggested: we should add  if statemenmt to check if the point IS actually the starting point- cdenter
//            Point bottomPoint = axisRay.getPoint(height); // returns the center point of the second disk
//        if(point.equals(bottomPoint) ||point.equals(axisRay.getP0()) )
//            return axisRay.getDir().normalize();
//        if (point.subtract(bottomPoint).length() < radius)// meaning it is on the surface of second disk
//            return axisRay.getDir().normalize();
//        if (point.subtract(axisRay.getP0()).length() < radius)// meaning it is on the surface of first disk
//            return axisRay.getDir().normalize();
//        return super.getNormal(point);// else the point is on the tube, and we use the getnormal function of the tube.(super)
//    }

}
