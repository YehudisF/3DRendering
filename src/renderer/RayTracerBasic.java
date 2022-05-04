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
    private static final double DELTA=0.1;

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

    /**
     * calculates the concurrent local effects such a specular and diffusion effects
     * @param gp
     * @param ray
     * @return the color of the area add to concurrent local effect
     */
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

    /**
     * calculates the diffusion effect in a ray hitting  a surface
     * @param material
     * @param nl
     * @return the diffusion effect
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }

    /**
     * calculates the specular effect in a ray intersection with a geometry
     * @param material
     * @param n
     * @param l
     * @param nl
     * @param v
     * @return the specular parameter
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2)).normalize();
        //Vector r = l.add(n.scale(-2*nl));
        double minusVR = 0-alignZero(r.dotProduct(v));
        if(minusVR <= 0)
            return Double3.ZERO;
        return material.kS.scale( Math.pow(Math.max(0, r.dotProduct(v.scale(-1d))), material.nShininess));
    }

    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector n, double nl){
        Point point = gp.point;
        Vector l = lightSource.getL(point);
        Vector lightDirection  = l.scale(-1);
        Ray lightRay = new Ray(gp.point, n, lightDirection);

        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay);
        return intersections == null;
    }


//    private Color calcColour(GeoPoint gp, Ray ray) {
//        Color lightContribution = new Color(Dou);
//        return scene.getAmbienLight().getIntensity()
//                .add(gp.geometry.getEmission())
//                .add();
//    }
}
