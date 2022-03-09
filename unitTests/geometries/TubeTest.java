package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void testGetNormal1() {
        // ============ Equivalence Partitions Test ==============
        Tube tu = new Tube(
                new Ray(new Point(1, 1, 1), new Vector(0, 0, 1)),
                2);
        assertEquals(new Vector(1,1, 0).normalize(), tu.getNormal(new Point(2, 2, 2)));
    }
    @Test
    void testGetNormal2() {
        // ============ Boundary Value Test ==============
        Tube tu = new Tube(
                new Ray(new Point(0, 0, 1), new Vector(0, 0, 1)),
                2);
        assertEquals(new Vector(1,1, 0).normalize(), tu.getNormal(new Point(0, 2, 1)));
    }
}