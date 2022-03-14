package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
    public List<Point> findIntersections(Ray ray)
    {
        return super.findIntersections(ray);
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

           if(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir()) == 0)// if the dotproduct is zero`than the point is on the disk and the vector of the ray is the normal
               return axisRay.getDir().normalize();
           return super.getNormal(point);// else the point is on the tube, and we use the getnormal function of the tube.(super)
    }

}
