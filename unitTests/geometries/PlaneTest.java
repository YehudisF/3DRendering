package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    Point p1=new Point(0,0,0);
    Point p2=new Point(0,0,1);
    Point p3=new Point(0,1,0);
    Plane pl=new Plane(p1,p2,p3);
    @Test
    void testGetNormal() {
        // problem is that vector doesn't have an equal function so can't use the operator ==.
        // assertEquals(new Vector(-1,0, 0).normalize(), pl.getNormal());
        Vector expected = new Vector(-1,0,0).normalize();
        Vector expected2 = expected.scale(-1);
        assertTrue(pl.getNormal().equals(expected) || pl.getNormal().equals(expected2));

    }

    @Test
    void testTestGetNormal() {
    }
}