package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

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



    public List<GeoPoint> findGeoIntersectionHelper(Ray ray)
    {
        return super.findGeoIntersectionsHelper(ray);
    }


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
