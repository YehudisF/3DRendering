package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    void testConstructorNotZero()
    {
        assertThrows(
                IllegalArgumentException.class,
                ()->{
                    new Vector(0, 0, 0.0000000000000000000000034);
                    },
                    "Vector(0,0,0) shoud have thrown Exception"
       );
    }

    @Test
    /**
     * method for testing {@link Vector#lengthSquared()}
     */
    void testLengthSquared()
    {
        //test length
        assertEquals(14d,v1.lengthSquared(),0.000001,"Error:LengthSquared()wrong value");
    }

    @Test
    void testLength() {
        // ============ Equivalence Partitions Test ==============
        assertTrue(isZero(v1.lengthSquared() - 14),"ERROR: lengthSquared() wrong value");
        assertTrue(isZero(new Vector(0, 3, 4).length() - 5),"ERROR: length() wrong value");
    }

    @Test
    void testDotProduct() {
        // =============== Boundary Values Tests ==================
        assertTrue(isZero(v1.dotProduct(v3)),"ERROR: dotProduct() for orthogonal vectors is not zero");

        // ============ Equivalence Partitions Test ==============
        assertTrue(isZero(v1.dotProduct(v2) + 28),"ERROR: dotProduct() wrong value");
    }
//    Vector vr = v1.crossProduct(v3);
//		if (!isZero(vr.length() - v1.length() * v3.length()))
//            out.println("ERROR: crossProduct() wrong result length");
//		if (!isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)))
//            out.println("ERROR: crossProduct() result is not orthogonal to its operands");

    @Test
    /**
     * test ZERO vector from cross-product of co-lines vectors
     */
    void testCrossProduct1() {
        // =============== Boundary Values Test ==================
       assertThrows(
               IllegalArgumentException.class,
               () -> { v1.crossProduct(v2);}
               ,"ERROR: crossProduct() for parallel vectors does not throw an exceptionn");
    }

    /**
     * Test that length of cross-product is proper(orthogonal vectors taken for simplicity)
     */
    @Test
    void testCrossProduct2() {
        // ============ Equivalence Partitions Test ==============
        Vector vr = v1.crossProduct(v3);
        double vrlen = vr.length();
        double v1v3len = v1.length()*v3.length();
        assertEquals(vr.length(),
                v1.length() * v3.length(),
                0.000000000001,
                "ERROR: crossProduct() wrong result length");
    }

    /**
     * Test cross-product result orthogonality to its operands
     */
    @Test
    void testCrossProduct3() {
        // ============ Equivalence Partitions Test ==============
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");
    }
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector u = v.normalize();
        // =============== Boundary Values Tests ==================
        assertTrue(isZero(u.length() - 1), "ERROR: the normalized vector is not a unit vector");
        assertThrows( IllegalArgumentException.class,
                () -> {v.crossProduct(u);},
                "ERROR: the normalized vector is not parallel to the original one"); // test that the vectors are co-lined
        assertTrue(v.dotProduct(u) >= 0,"ERROR: the normalized vector is opposite to the original one");
    }

    @Test
    void testScale() {
        // ============ Equivalence Partitions Test ==============
        Vector vector1 = new Vector(2, 4, 6);
        assertEquals(v1.scale(2),vector1,"scale doesn't work properly");
//        // =============== Boundary Values Tests ==================
//        Vector vector2 = new Vector(0, 0, 0);
//        assertEquals(v1.scale(0),vector2,"scale doesn't work properly");
    }

    @Test
    void testTestEquals() {
        Point pt = new Point(1,1,1);
        Vector v = new Vector(1,1,1);
        assertNotEquals(pt, v, "coucou was here!!:)");
    }
}