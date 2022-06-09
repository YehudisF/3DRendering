package renderer;
import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * an abstract class for tracing the rays path through the scene
 */
public abstract class RayTracer {
    protected  final Scene scene;
    protected boolean softshadows = false;
    protected double beamRadius = 20d;

    public RayTracer(Scene scene) {
        this.scene = scene;
    }


    /**
     * An abstract function that get a ray and return the color of the point that cross the ray
     * @param ray ray that intersect the scene
     * @return Color
     */
    public abstract Color traceRay(Ray ray,int rayCounter);

    public abstract Color averageColor(List<Ray> rays,Ray middleRay);

    public boolean isSoftshadows() {
        return softshadows;
    }

    public RayTracer setSoftshadows(boolean softshadows) {
        this.softshadows = softshadows;
        return this;
    }

    public RayTracer setBeamRadius(double beamRadius) {
        this.beamRadius = beamRadius;
        return this;
    }
}
