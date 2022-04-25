package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight {

    /**
     *
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    final Vector direction;


    public Color getIntensity(Point p)
    {
//        // but kL and Kq are 0
//        double denominator= intensityHelp(p);
//        Vector l= getL(p);
//        return (getIntensity().scale(Math.max(0,direction.normalize().dotProduct(l))).reduce(denominator));
        Color pointIntensity = super.getIntensity(p);
        double factor = Math.max(0, direction.dotProduct(getL(p)));
        return pointIntensity.scale(factor);
    }


}


//    private SpotLight(SpotLightBuilder builder,Color intensity, Point position,)
//    {
//        super(intensity, position);
//        this.direction=builder.direction;
//        this.kC=builder.kC;
//        this.kL=builder.kL;
//        this.kQ=builder.kQ;
//    }

//    public static class SpotLightBuilder {
//        private final Vector direction;
//        private final double kC;
//        private final double kL;
//        private final double kQ;
//
//        public SpotLightBuilder(Vector direction, double kC, double kL, double kQ) {
//            this.direction = direction;
//            this.kC = kC;
//            this.kL = kL;
//            this.kQ = kQ;
//        }
//
//    }



        // need to make a builder pattern that returns this, check because they are final


