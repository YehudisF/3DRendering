package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

import java.util.LinkedList;
import java.util.List;

public class Cylinder extends Tube
{
    double height;
    public Cylinder(Ray axisRay, double radius, double height)
    {
        super(axisRay, radius);
        this.height = height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = super.findGeoIntersectionsHelper(ray, maxDistance);
        if (intersections == null) {
            return null;
        }
        Vector va = this.axisRay.getDir();
        Point A = this.axisRay.getP0();
        Point B = this.axisRay.getPoint(height);
        List<GeoPoint> intersectionsCylinder = new LinkedList<>();
        double lowerBound, upperBound;
        for (GeoPoint gPoint : intersections) {
            lowerBound = va.dotProduct(gPoint.point.subtract(A));
            upperBound = va.dotProduct(gPoint.point.subtract(B));
            if (lowerBound > 0 && upperBound > 0) {
                // the check for distance, if the intersection point is beyond the distance
                if (alignZero(gPoint.point.distance(ray.getP0()) - maxDistance) <= 0)
                    intersectionsCylinder.add(gPoint);
            }
        }
        Plane topA = new Plane(A, va);
        Plane bottomB = new Plane( B, va);
        List<GeoPoint> intersectionPlaneA = topA.findGeoIntersections(ray, maxDistance);
        List<GeoPoint> intersectionPlaneB = bottomB.findGeoIntersections(ray, maxDistance);
        if (intersectionPlaneA == null && intersectionPlaneB == null) {
            return intersectionsCylinder;
        }
        Point q3, q4;
        if (intersectionPlaneA != null) {
            q3 = intersectionPlaneA.get(0).point;
            if (q3.subtract(A).lengthSquared() < radius * radius) {
                intersectionsCylinder.add(intersectionPlaneA.get(0));
            }
        }
        if (intersectionPlaneB != null) {
            q4 = intersectionPlaneB.get(0).point;
            if (q4.subtract(B).lengthSquared() < radius * radius) {
                intersectionsCylinder.add(intersectionPlaneB.get(0));
            }
        }
        if (intersectionsCylinder.isEmpty()) {
            return null;
        }
        return intersectionsCylinder;
    }

//    public List<GeoPoint> findGeoIntersectionHelper(Ray ray, double maxDistance)
//    {
//        //return super.findGeoIntersections(ray);
//
//    }
     // changed it from helper to not helper


    @Override
    public String toString()
    {
        return "Cylinder{" +
                "height=" + height +
                ", axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    /**
     * yhr normal at a base is equal to central ray's direction vector or opposite
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point point)
    {
        //avigayil suggested: we should add  if statemenmt to check if the point IS actually the starting point- cdenter
            Point bottomPoint = axisRay.getPoint(height); // returns the center point of the second disk
        if(point.equals(bottomPoint) ||point.equals(axisRay.getP0()) )
            return axisRay.getDir().normalize();
        if (point.subtract(bottomPoint).length() < radius)// meaning it is on the surface of second disk
            return axisRay.getDir().normalize();
        if (point.subtract(axisRay.getP0()).length() < radius)// meaning it is on the surface of first disk
            return axisRay.getDir().normalize();
        return super.getNormal(point);// else the point is on the tube, and we use the getnormal function of the tube.(super)
    }

}
