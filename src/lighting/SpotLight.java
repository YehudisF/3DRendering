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
        this.direction = direction;
    }

    final Vector direction;

    @Override
    public Color getIntensity() {
        return super.getIntensity();
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


