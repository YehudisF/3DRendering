package renderer;
import geometries.Geometries;
import primitives.*;
import Scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracer {
    public RayTracerBasic(Scene scene){
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray){
        Geometries geometries = scene.getGeometries();
        List<Point> intersectionPoints = geometries.findIntersections(ray);
        Point closestPoint = ray.findClosestPoint(intersectionPoints);
        return calcColour(closestPoint);
    }


    private Color calcColour(Point point){
        return scene.getAmbientLight().getIntensity();
    }
}
