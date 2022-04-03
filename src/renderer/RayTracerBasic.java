package renderer;
import geometries.Geometries;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * derivative class from RayTracer traces ray path in the scene noting intersections
 * with geometries in the scene
 */
public class RayTracerBasic extends RayTracer {
    public RayTracerBasic(Scene scene){
        super(scene);
    }

    /**
     *  traces the ray and its intersections with geometries to find the closest point and return its colour
     * @param ray the ray being traced
     * @return the calculated color of the closest point- to colour thus themathcing pixel
     */
    @Override
    public Color traceRay(Ray ray){
        List<GeoPoint> intersectionPoints = scene.getGeometries().findGeoIntersections(ray);
        if(intersectionPoints != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersectionPoints);
            return calcColour(closestPoint);
        }
        return scene.background;
    }

//
//    private Color calcColour(Point point){
//        return scene.ambientLight.getIntensity();
//    }
    private Color calcColour(GeoPoint gp) {
        return scene.getAmbienLight().getIntensity()
                .add(gp.geometry.getEmission());
    }
}
