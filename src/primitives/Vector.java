package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
//        super(x,y,z);
//        if(_xyz.equals(Double3.ZERO))
//        {
//            throw  new IllegalArgumentException("Vector(0,0,0) is not allowed");
//        }
        this(new Double3(x,y,z));
    }

    /**
     *
     * @param xyz
     */
    public Vector(Double3 xyz){
        super(xyz);
        if(_xyz.equals(Double3.ZERO))
        {
            throw  new IllegalArgumentException("Vector(0,0,0) is not allowed");
        }
    }

    /**
     *
     * @return
     */
    public  double lengthSquared()
    {
        return _xyz._d1 * _xyz._d1 +
                _xyz._d2 * _xyz._d2 +
                _xyz._d3 * _xyz._d3;
    }

    public double length()
    {
        return  Math.sqrt(lengthSquared());
    }

    /**
     *dot product between two vectors (scalar product)
     * @param v3
     * @return
     * "link https://www.mathsisfun.com/algebra/vectors-dot-product.html
     */
    public double dotProduct(Vector v3)
    {
        return v3._xyz._d1 * _xyz._d1 +
                v3._xyz._d2 * _xyz._d2 +
                v3._xyz._d3 * _xyz._d3;
    }

    /**
     *cross product between two vectors (vectorial product)969
     * @param v3
     * @return the vector result from the cross product( Right-hand rule)
     * @link: https://www.mathsisfun.com/algebra/vectors-cross-product.html
     */
    public  Vector crossProduct(Vector v3)
    {
        double ax = _xyz._d1;
        double ay = _xyz._d2;
        double az = _xyz._d3;
        double bx = v3._xyz._d1;
        double by = v3._xyz._d2;
        double bz = v3._xyz._d3;

       double cx = ay * bz - az * by;
          double  cy = az * bx - ax * bz;
           double cz = ax * by - ay * bx;
           return  new Vector(cx, cy, cz);
    }

    /**
     *
     * @return
     */
    public Vector normalize() {
        double len = length();
        if(len == 0)
            throw new ArithmeticException("Divide by zero!");
        return new Vector(_xyz.reduce((len)));
    }

    public Vector scale(double scalar)
    {
        return new Vector(_xyz.scale(scalar));
    }


}
