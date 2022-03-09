package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    Point p1 = new Point(1,2,3);
    Point p2 = new Point(1,1,1);

    /**
     * Test method for {@link Point#distanceSquared(Point)} )}
     */
    @Test
    void testDistanceSquared() {
        assertEquals(p1.distanceSquared(p2),5, "distanceSquared doesnt work properly");
    }

    /**
     * Test method for {@link Point#distance(Point)} )}
     */
    @Test
    void testDistance() {
        assertEquals(p1.distance(p2),Math.sqrt(5),"distance doesn't work properly");
    }

    @Test
    /**
     * Test method for {@link Point#add(Vector)}
     */
    void testAdd() {
        Vector v = new Vector(1,2,3);
        Point point = new Point(1,1,1);

        assertEquals(new Point(2,3,4), point.add(v),"add doesn't work properly");
    }

    /**
     * Test method for {@link Point#subtract(Point)} )}
     */
    @Test
    void testSubtract() {
        assertEquals(new Vector(0,1,2), p1.subtract(p2),"add doesn't work properly");
    }
}