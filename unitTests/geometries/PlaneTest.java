package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {
    Point p1=new Point(0,0,0);
    Point p2=new Point(0,0,1);
    Point p3=new Point(0,1,0);
    Plane pl=new Plane(p1,p2,p3);

    @Test
    public void testConstructor1() {
        // ============ Boundary Value Tests ==============

        // Checks whether constructor correctly throws an error when given 3 point of which 2 are the same
        try {
            new Plane(new Point(0, 0, 1), new Point(0, 0, 1), new Point(0, 1, 0));
            fail("Constructed an incorrect plane");
        } catch (IllegalArgumentException e) {

            // Checks whether constructor correctly throws an error when given 3 point on the same line
        } try {
            new Plane(new Point(1, 1, 1), new Point(2, 2, 2), new Point(4, 4, 4));
            fail("Constructed an incorrect plane");
        } catch (IllegalArgumentException e) {
        }


    }

    @Test
    void testGetNormal() {
        // assertEquals(new Vector(-1,0, 0).normalize(), pl.getNormal());
        Vector expected = new Vector(-1,0,0).normalize();
        Vector expected2 = expected.scale(-1);
        assertTrue(pl.getNormal().equals(expected) || pl.getNormal().equals(expected2));

    }

    @Test
    void testTestGetNormal() {
    }

    /**
     * Test method for {@link geometries.Plane#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testfindIntersectionsRay() {
        Plane pl = new Plane(new Point(0, 0, 1), new Vector(1, 1, 1));
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray into plane
        assertEquals(List.of(new Point(1, 0, 0)),
                pl.findIntersections(new Ray(new Point(0.5, 0, 0), new Vector(1, 0, 0))),
                "Bad plane intersection");

        // TC02: Ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 0))),
                "Must not be plane intersection");

        // =============== Boundary Values Tests ==================
        // TC11: Ray parallel to plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(0, 1, -1))),
                "Must not be plane intersection");

        // TC12: Ray in plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, .5), new Vector(0, 1, -1))),
                "Must not be plane intersection");


        // TC13: Orthogonal ray into plane
        assertEquals(List.of(new Point(1d / 3, 1d / 3, 1d / 3)),
                pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(-1, -1, -1))),
                "Bad plane intersection");

        // TC14: Orthogonal ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC15: Orthogonal ray out of plane
        assertNull(pl.findIntersections(new Ray(new Point(1, 1, 1), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC16: Orthogonal ray from plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 1))),
                "Must not be plane intersection");

        // TC17: Ray from plane
        assertNull(pl.findIntersections(new Ray(new Point(0, 0.5, 0.5), new Vector(1, 1, 0))),
                "Must not be plane intersection");

//        // TC18: Ray from plane's Q point
//        assertNull(pl.findIntersections(new Ray(new Point(0, 0, 1), new Vector(1, 1, 0))),
//                "Must not be plane intersection");

    }
}