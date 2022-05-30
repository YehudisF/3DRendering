package primitives;

public class Vector extends Point {

    public Vector(double x, double y, double z) {
//        super(x,y,z);
//        if(xyz.equals(Double3.ZERO))
//        {
//            throw  new IllegalArgumentException("Vector(0,0,0) is not allowed");
//        }
        this(new Double3(x, y, z));
    }

    /**
     * @param xyz
     */
    public Vector(Double3 xyz) {
        super(xyz);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("Vector(0,0,0) is not allowed");
        }
    }

    public Vector add(Vector other)
    {
        return new Vector(xyz.add(other.xyz));
    }

    /**
     * subtract between this vector and another one
     * @param other  the second vector
     * @return new vector from this vector to the other vector
     */
    public Vector subtract(Vector other) {
        return new Vector(xyz.subtract(other.xyz));
    }

    /**
     * @return
     */
    public double lengthSquared() {
        return xyz.d1 * xyz.d1 +
                xyz.d2 * xyz.d2 +
                xyz.d3 * xyz.d3;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * dot product between two vectors (scalar product)
     *
     * @param v3
     * @return "link https://www.mathsisfun.com/algebra/vectors-dot-product.html
     */
    public double dotProduct(Vector v3) {
        return v3.xyz.d1 * xyz.d1 +
                v3.xyz.d2 * xyz.d2 +
                v3.xyz.d3 * xyz.d3;
    }

    /**
     * cross product between two vectors (vectorial product)969
     *
     * @param v3
     * @return the vector result from the cross product( Right-hand rule)
     * @link: https://www.mathsisfun.com/algebra/vectors-cross-product.html
     */
    public Vector crossProduct(Vector v3) {
        double ax = xyz.d1;
        double ay = xyz.d2;
        double az = xyz.d3;

        double bx = v3.xyz.d1;
        double by = v3.xyz.d2;
        double bz = v3.xyz.d3;

        double cx = ay * bz - az * by;
        double cy = az * bx - ax * bz;
        double cz = ax * by - ay * bx;

        return new Vector(cx, cy, cz);
    }

    /**
     * @return
     */
    public Vector normalize() {
        double len = length();
        if (len == 0)
            throw new ArithmeticException("Divide by zero!");
        return new Vector(xyz.reduce((len)));
    }

    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }


    /**
     * Rotates the vector around the x axis
     * @param alpha the amount to rotate in degrees
     * @return the current vector
     */
    public Vector rotateX(double alpha) {
        double radianAlpha = alpha * Math.PI / 180;

        double x = getX();
        double y = getY() * Math.cos(radianAlpha) - getZ() * Math.sin(radianAlpha);
        double z = getY() * Math.sin(radianAlpha) + getZ() * Math.cos(radianAlpha);


        return new Vector(x,y,z);
    }


    /**
     * Rotates the vector around the y axis
     * @param alpha the amount to rotate in degrees
     * @return the current vector
     */
    public Vector rotateY(double alpha) {
        double radianAlpha = alpha * Math.PI / 180;

        double x = getX() * Math.cos(radianAlpha) + getZ() * Math.sin(radianAlpha);
        double y = getY();
        double z = -getX() * Math.sin(radianAlpha) + getZ() * Math.cos(radianAlpha);

        return new Vector(x,y,z);
    }


    /**
     * Rotates the vector around the z axis
     * @param alpha the amount to rotate in degrees
     * @return the current vector
     */
    public Vector rotateZ(double alpha) {
        double radianAlpha = alpha * Math.PI / 180;

        double x = getX() * Math.cos(radianAlpha) - getY() * Math.sin(radianAlpha);
        double y = getX() * Math.sin(radianAlpha) + getY() * Math.cos(radianAlpha);
        double z = getZ();

        return new Vector(x,y,z);
    }



//    public boolean isZeroVector() {
//        return xyz.equals(Double3.ZERO);
//    }

}
