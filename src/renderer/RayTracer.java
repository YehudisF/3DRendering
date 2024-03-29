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
    public abstract Color traceRay(Ray ray);

    public abstract Color averageColor(List<Ray> rays,Ray middleRay);

    public boolean isSoftshadows() {
        return softshadows;
    }

    /**
     * boolan funciton to decide if softshadows shoud be added
     * @param softshadows
     * @return
     */
    public RayTracer setSoftshadows(boolean softshadows) {
        this.softshadows = softshadows;
        return this;
    }

    public RayTracer setBeamRadius(double beamRadius) {
        this.beamRadius = beamRadius;
        return this;
    }

    public abstract Color AdaptiveSuperSamplingRec(Point centerP, double Width, double Height, double minWidth, double minHeight, Point cameraLoc, Vector Vright, Vector Vup, List<Point> prePoints);
}
