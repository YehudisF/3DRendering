package renderer;

import geometries.Geometries;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * derivative class from RayTracer traces ray path in the scene noting intersections
 * with geometries in the scene
 */
public class RayTracerBasic extends RayTracer {
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * traces the ray and its intersections with geometries to find the closest point and return its colour
     *
     * @param ray the ray being traced
     * @return the calculated color of the closest point- to colour thus themathcing pixel
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.getGeometries().findGeoIntersections(ray);
        if (intersectionPoints == null) {
            return scene.getBackground();
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersectionPoints);
        return calcColour(closestPoint,ray);
    }

    //
//    private Color calcColour(Point point){
//        return scene.ambientLight.getIntensity();
//    }
    private Color calcColour(GeoPoint gp, Ray ray) {

        return scene.getAmbienLight().getIntensity()
                .add(calcLocalEffects(gp, ray));


    }

    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir().normalize();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(material, nl)),
                        iL.scale(calcSpecular(material, n, l, nl, v)));
            }
        }
        return color;
    }

    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2)).normalize();
        return material.kS.scale( Math.pow(Math.max(0, r.dotProduct(v.scale(-1d))), material.nShininess));

    }


//    private Color calcColour(GeoPoint gp, Ray ray) {
//        Color lightContribution = new Color(Dou);
//        return scene.getAmbienLight().getIntensity()
//                .add(gp.geometry.getEmission())
//                .add();
//    }
}
