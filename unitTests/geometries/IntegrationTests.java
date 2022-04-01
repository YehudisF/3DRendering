package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Vector;
import renderer.Camera;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testing integration of camera various functionalities
 * through the viewPlane
 */
public class IntegrationTests
{
    static final Point ZERO_POINT = new Point(0, 0, 0);
    Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVPDistance(1)
            .setVPSize(3,3);

    /**
     * function to create rays for all the picels
     * @param Nx amount of pixels in the width
     * @param Ny amount of pixles in the height
     * @return
     */
    List<Ray> createRays(int Nx, int Ny)
    {
        List<Ray> ray = new LinkedList<>();
        for(int i = 0; i< Nx; i++)
        {
            for(int j = 0; j< Ny; j++)
            {
               ray.add(camera.constructRay(Nx, Ny, i, j));
            }
        }
        return ray;
    }

    int sumIntersections(List<Ray> rays,Geometries geometries )
    {
        int sum = 0;
        for (var ray: rays)
        {
           var cnt = geometries.findIntersections(ray);
           sum += cnt!= null ? cnt.size() : 0;
        }
        return sum;
    }

    @Test
    void testIntegrationIntersections()
    {
        // tests for the sphere
        camera.setP0(new Point(0, 0, 0.5));
        List<Ray> rays=createRays(3,3);

        //2 points of intersection with sphere
        Geometries grSphere1=new Geometries(new Sphere(new Point(0, 0, -3),1));
        assertEquals(2,sumIntersections(rays,grSphere1),"test failed for smaller shape with 2 intersections");


        // 18 points of intersection with sphere

        //checks for 2 intersections for every ray
        Geometries grSphere2=new Geometries(new Sphere(new Point(0, 0, -2.5),2.5));
        assertEquals(18,sumIntersections(rays,grSphere2),"test failed for shape with 2 intersections per ray");

        // 10 points of intersection with sphere
        //checks for intersection for the 5 inner rays 2 intersections
        Geometries grSphere3=new Geometries(new Sphere(new Point(0, 0, -2),2));
        assertEquals(10,sumIntersections(rays,grSphere3),"test failed for shape with 2 interections for the 5 inner rays");

        // 6 intersection points from within the sphere
        Geometries grSphere4=new Geometries(new Sphere(new Point(0, 0, 0),4));
        assertEquals(9,sumIntersections(rays,grSphere4),"test failed for points within in the sphere");

        // 0 intersections, sphere is behind
        Geometries grSphere5=new Geometries(new Sphere(new Point(0, 0, 2),0.5));
        assertEquals(0,sumIntersections(rays,grSphere5),"test failed for sphere behind viewPlane");


        // tests for triangle

        //2 intersection points
        Geometries grTriangle1= new Geometries(new Triangle(new Point(0,20,-2),new Point(-1,-1,-2), new Point(1,-1,-2)));
        assertEquals(2,sumIntersections(rays,grTriangle1), "test failed for 2 intersections with triangle");

        //1 intersection point
        Geometries grTriangle2= new Geometries(new Triangle(new Point(0,1,-2),new Point(-1,-1,-2), new Point(1,-1,-2)));
        assertEquals(1,sumIntersections(rays,grTriangle2), "test failed for 1 intersection with triangle");

        //test for plane
        // 9 intersection points with plae fully facing the viewPlane
        Geometries grPlane1=new Geometries(new Plane(new Point(0,0,-8),new Vector(0,0,1)));
        assertEquals(9,sumIntersections(rays,grPlane1), "test failed for 9 intersections with plane");

        //9 intersection with plane slided diagonal
        Geometries grPlane2=new Geometries(new Plane(new Point(0,0,-8),new Vector(0,-1,1)));
        assertEquals(6,sumIntersections(rays,grPlane2), "test failed for 9 intersections with plane");

        // 6 intersection with plane
        Geometries grPlane3=new Geometries(new Plane(new Point(0,0,-8),new Vector(0,-0.5,1)));
        assertEquals(9,sumIntersections(rays,grPlane3), "test failed for 9 intersections with plane");

    }
}
