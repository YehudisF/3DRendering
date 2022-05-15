package renderer;

import geometries.FlatGeometry;
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
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double MIN_CALC_COLOR_K = 0.001;
    public static final Double3 INITIAL_K=Double3.ONE;


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
    GeoPoint closetPoint = findClosestIntersection(ray);
    if (closetPoint == null) {
            return scene.getBackground();
        }
     return calcColour(closetPoint,ray);
    }
    /**
     *
     * @param ray
     * @return closest geopoint in intersection list
     */
    private GeoPoint findClosestIntersection(Ray ray){
        List<GeoPoint> intersectionPoints = scene.getGeometries().findGeoIntersections(ray);
        if (intersectionPoints == null) {
            return null;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersectionPoints);
        return closestPoint;
    }

    private Color calcColour(GeoPoint gp, Ray ray){
//        return scene.getAmbienLight().getIntensity()
//                .add(calcLocalEffects(gp, ray));
        return calcColour(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbienLight().getIntensity());
    }


    private Color calcColour(GeoPoint gp, Ray ray,int level, Double3 k) {

        Color color =gp.geometry.getEmission().add(calcLocalEffects(gp, ray,k));
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));

    }

    /**
     * calculates the concurrent local effects such a specular and diffusion effects
     * @param gp
     * @param ray
     * @return the color of the area add to concurrent local effect
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir().normalize();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color.BLACK;
        Material material = gp.geometry.getMaterial();
        color = color.BLACK;
        for (LightSource lightSource : scene.getLights())
        {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0)
            { // sign(nl) == sing(nv)

               // if(transparency(gp,lightSource,n,nl,nv,l)) {
                    Double3 ktr=transparency(l,n,lightSource,gp,nv);
                    if(!(ktr.product(k).lowerThan( MIN_CALC_COLOR_K)))
                    {
                        Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                        color = color.add(
                                iL.scale(calcDiffusive(material, nl)),
                                iL.scale(calcSpecular(material, n, l, nl, v)));
                    }
                }
            }
        return color;
    }



    private Ray constructRefractedRay(GeoPoint pointGeo, Ray inRay, Vector n) {
        return new Ray(pointGeo.point, inRay.getDir(),n);
    }

    private Ray constructReflectedRay(Vector n,Point pointGeo, Ray inRay ) {

        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);

//        if (vn == 0) {
//            return null;
//        }

        //r = v - 2.(v.n).n

        Vector r = v.subtract(n.scale(2 * vn)).normalize();
        return new Ray(pointGeo,r,n);
    }

//
//    private double transparencyBeam(double lightDistance, LightSource lightSource, Vector n, GeoPoint geoPoint) {
//        double ktr;
//        List<Vector> beamL = lightSource.getBeamL(geoPoint.getPoint(), _beamRadius, _rayCounter);
//        double tempKtr = 0;
//        for (Vector vl : beamL) {
//            tempKtr += transparency(lightDistance, vl, n, geoPoint);
//        }
//        ktr = tempKtr / beamL.size();
//
//        return ktr;
//    }



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
        Vector r = l.subtract(n.scale(l.dotProduct(n) * 2));
        //Vector r = l.add(n.scale(-2*nl));
        double minusVR = alignZero(-v.dotProduct(r));
        if(minusVR <= 0)
            return Double3.ZERO;
      //  return material.kS.scale( Math.pow(Math.max(0, r.dotProduct(v.scale(-1d))), material.nShininess));
        return material.kS.scale( Math.pow(minusVR, material.nShininess));
    }

//    private Color calcSpecular(Double3 kS, Vector l, Vector n, Vector v, int nShininess, Color intensity) {
//        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
//        double vr = alignZero(-v.dotProduct(r));
//        if(vr <= 0)
//            return  Color.BLACK;
//        return intensity.scale(kS.scale(Math.pow(vr, nShininess)));
//    }


    /**
     * checks whether a point on  a geometry is shaded
     * @param gp
     * @param lightSource
     * @param n
     * @return
     */
    private Double3 transparency( Vector l,Vector n, LightSource lightSource, GeoPoint gp,double nv){
        Vector lightDirection = l.scale(-1).normalize(); // from point to light source//
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        double lightDistance = lightSource.getDistance(gp.point);
        var intersections = scene.getGeometries().findGeoIntersections(lightRay);
        if (intersections == null)
            return Double3.ONE; //no intersection
        for (var geo : intersections ) {
            double dist = geo.point.distance(gp.point);
            if (dist >= lightDistance){
                intersections.remove(geo);
            }
        }
        if(intersections.isEmpty()){
            return  Double3.ONE;
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint geopoint : intersections) {
                ktr = ktr.product(geopoint.geometry.getMaterial().kT);
                if (ktr.lowerThan( MIN_CALC_COLOR_K) )
                    return Double3.ZERO;
        }
        return ktr;
//        Point point = gp.point;
//      //  Vector l = lightSource.getL(point);
//        Vector lightDirection  = l.scale(-1);
//        Vector delVector = n.scale(nv < 0 ? DELTA : -DELTA);
//        Point pointRay = point.add(delVector);
//        Ray lightRay = new Ray(pointRay, lightDirection);
//        double maxDistance = lightSource.getDistance(point);
//        double lightDistance = lightSource.getDistance(gp.point);
//        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);
//        if (intersections == null)
//            return 1.1; //no intersection
//        Double ktr = 1.0;
//        for (GeoPoint geopoint : intersections) {
//            if (alignZero(geopoint.point.distance(gp.point) - lightDistance) <= 0) {
//                ktr = (geopoint.geometry.getMaterial().kT).scale(ktr);
//                if (ktr< MIN_CALC_COLOR_K)
//                    return 0.0;
//            }
//        }
//        return ktr;
//       // return intersections == null;
    }




    private Color calcGlobalEffects(GeoPoint geopoint, Ray inRay, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = geopoint.geometry.getMaterial();
       // Double3 MIN_CALC=new Double3(MIN_CALC_COLOR_K,MIN_CALC_COLOR_K,MIN_CALC_COLOR_K);
        Double3 kr = material.kR;
        Double3 kkr = k.product(kr);
        Vector n = geopoint.geometry.getNormal(geopoint.point);//
        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
            Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
            if (reflectedPoint != null) {
                color = color.add(calcColour(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
            }
        }
        Double3 kt = material.kT;
        Double3 kkt = k.product(kt);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
            Ray refractedRay = constructRefractedRay(geopoint, inRay, n);
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
            if (refractedPoint != null) {
                color = color.add(calcColour(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
            }
        }
        return color;
    }





//    private Color calcColour(GeoPoint gp, Ray ray) {
//        Color lightContribution = new Color(Dou);
//        return scene.getAmbienLight().getIntensity()
//                .add(gp.geometry.getEmission())
//                .add();
//    }
}


//package renderer;
//import geometries.FlatGeometry;
//import geometries.Intersectable.*;
//import lighting.LightSource;
//import primitives.*;
//import scene.Scene;
//
//import java.sql.PreparedStatement;
//import java.util.List;
//
//import static primitives.Double3.*;
//import static primitives.Util.alignZero;
//
//public class RayTracerBasic extends RayTracer {
//
//    private static final Double3 INITIAL_K = new Double3(1,1,1);
//    private static final int MAX_CALC_COLOR_LEVEL = 10;
//    private static final double MIN_CALC_COLOR_K = 0.001;
//    private static final double DELTA = 0.1;
//
//    /**
//     * Constructor that calls the super constructor
//     *
//     * @param scene
//     */
//    public RayTracerBasic(Scene scene) {
//        super(scene);
//    }
//
//    /**
//     * Get color of the intersection of the ray with the scene
//     *
//     * @param ray           Ray to trace
//
//     * @return Color of intersection
//     */
//    @Override
//    public Color traceRay(Ray ray) {
//
//        List<GeoPoint> myPoints = scene.getGeometries().findGeoIntersections(ray);
//        if (myPoints != null) {
//            GeoPoint myPoint = ray.findClosestGeoPoint(myPoints);
//            return calcColor(myPoint, ray);
//        }
//        return scene.getBackground();
//    }
//
////private Color calcColor(GeoPoint gp,Ray ray){
////        Color color=_scene.getAmbientlight().getIntensity();
////        color=color.add(calcLocalEffects(gp,ray));
////        return color;
////}
//    /**
//     * Calculate color using recursive function
//     *
//     * @param geopoint      the point of intersection
//     * @param ray           the ray
//     * @return the color
//     */
//    private Color calcColor(GeoPoint geopoint, Ray ray) {
//        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.getAmbienLight().getIntensity());
//
//    }
//
//    /**
//     * Get the color of an intersection point using the Phong model
//     * Recursive function
//     *
//     * @param geoPoint      point of intersection
//     * @return Color of the intersection point
//     */
//    private Color calcColor(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
//        Color color = geoPoint.geometry.getEmission().add(calcLocalEffects(geoPoint, ray,k));
//        return 1 == level ? color : color.add(calcGlobalEffects(geoPoint, ray, level, k));
//    }
//
//    /**
//     * It calculates the color of the point by calculating the color of the reflected and refracted rays
//     *
//     * @param geopoint The point on the geometry that the ray intersected with.
//     * @param ray the ray that hit the geometry
//     * @param level the recursion level.
//     * @param k the color of the light that is reflected from the current point.
//     * @return The color of the point.
//     */
//    private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, Double3 k) {
//        Color color = Color.BLACK;
//        Material material = geopoint.geometry.getMaterial();
//        Double3 kkr = k.product(material.kR);
//        Vector n = geopoint.geometry.getNormal(geopoint.point);
//        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
//            Ray reflectedRay = constructReflectedRay(n, geopoint.point, ray.getDir());
//            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
//            if (reflectedPoint != null) {
//                color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(material.kR));
//            }
//        }
//        Double3 kkt = k.product(material.kT);
//        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
//            Ray refractedRay = constructRefractedRay(n, geopoint.point, ray.getDir());
//            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
//            if (refractedPoint != null) {
//                color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(material.kT));
//            }
//        }
//        return color;
//    }
//
//
//    /**
//     * The function calculates the color of a point on a geometry, by calculating the color of the light sources that
//     * affect the point, and the color of the reflected rays from the point
//     *
//     * @param geoPoint The point on the geometry that the ray intersects with.
//     * @param ray the ray that hit the point
//     * @param k the color of the geometry
//     * @return The color of the point.
//     */
//    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
//        Vector v = ray.getDir();
//        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
//        double nv = alignZero(n.dotProduct(v));
//        if (nv == 0) return Color.BLACK;
//        Material material = geoPoint.geometry.getMaterial();
//        Color color = Color.BLACK;
//        for (LightSource lightSource : this.scene.getLights()) {
//            Vector l = lightSource.getL(geoPoint.point);
//            double nl = alignZero(n.dotProduct(l));
//            if (nl * nv > 0) { // sign(nl) == sing(nv)
//                Double3 ktr = transparency(geoPoint,lightSource,l,n);//transparency(l, n, geopoint, nv);
//                var x = ktr.product(k);
//                if (!x.lowerThan(MIN_CALC_COLOR_K)){
//                    Color intensity = lightSource.getIntensity(geoPoint.point).scale(ktr);
//                    color = color.add(calcDiffusive(material.kD, l, n, intensity),
//                            calcSpecular(material.kS, l, n, v, material.nShininess, intensity));
//                }
//            }
//        }
//        return color;
//    }
//
//
//    /**
//     * The function calculates the transparency of a point on a geometry, by checking if there are any other geometries
//     * between the point and the light source
//     *
//     * @param geoPoint The point on the geometry that we're currently shading.
//     * @param ls the light source
//     * @param l the vector from the point to the light source
//     * @param n the normal vector of the point on the geometry
//     * @return The transparency of the point.
//     */
//    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n){
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
//        double lightDistance = ls.getDistance(geoPoint.point);
//        var intersections = scene.getGeometries().findGeoIntersections(lightRay);
//        if (intersections == null) return new Double3(1.0); //no intersection
//        Double3 ktr = new Double3(1.0);
//        for (GeoPoint gp : intersections) {
//            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
//                //ktr *= gp.geometry.getMaterial().kT;
//                ktr = ktr.product(gp.geometry.getMaterial().kT);
//                if (ktr.lowerThan(MIN_CALC_COLOR_K))
//                    return Double3.ZERO;
//            }
//        }
//        return ktr;
//    }
//
//    /**
//     * "Calculate the specular component of the light's contribution to the color of the surface at the given point."
//     *
//     * The function takes the following parameters:
//     *
//     * * kS: The specular reflectivity of the surface.
//     * * l: The direction of the light.
//     * * n: The normal of the surface at the point.
//     * * v: The direction of the viewer.
//     * * nShininess: The shininess of the surface.
//     * * intensity: The intensity of the light
//     *
//     * @param kS The specular coefficient.
//     * @param l the vector from the point to the light source
//     * @param n normal vector
//     * @param v the vector from the point to the camera
//     * @param nShininess The shininess of the material.
//     * @param intensity the color of the light source
//     * @return The color of the point on the surface of the object.
//     */
//    private Color calcSpecular(Double3 kS, Vector l, Vector n, Vector v, int nShininess, Color intensity) {
//        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
//        double vr = alignZero(-v.dotProduct(r));
//        if(vr <= 0)
//            return  Color.BLACK;
//        return intensity.scale(kS.scale(Math.pow(vr, nShininess)));
//    }
//
//
//    private Color calcDiffusive(Double3 kD,Vector l,Vector n,Color intensity){
//        return intensity.scale(kD.scale(Math.abs(l.dotProduct(n))));
//    }
//    /**
//     * Construct the ray getting refracted on a point
//     *
//     * @param n     normal to the point
//     * @param point the point
//     * @return the refracted ray
//     */
//    private Ray constructRefractedRay(Vector n, Point point, Vector v) {
//        return new Ray(point, v,n);
//    }
//    /**
//     * Construct the ray getting reflected on a point
//     *
//     * @param n     normal to the point
//     * @param point the point
//     * @return the reflected ray
//     */
//    private Ray constructReflectedRay(Vector n, Point point, Vector v) {
////
//        return new Ray(point,v.subtract(n.scale(2*v.dotProduct(n))),n);
//    }
//
//    /**
//     * Find closest intersection point between a ray and the scene's geometries
//     *
//     * @param ray the ray
//     * @return the closest point
//     */
//    private GeoPoint findClosestIntersection(Ray ray) {
//        List<GeoPoint> geoPoints = scene.getGeometries().findGeoIntersections(ray);
//        return ray.findClosestGeoPoint(geoPoints);
//    }
//
//    /**
//     * If the ray from the point to the light source intersects with any opaque object, then the point is in shadow
//     *
//     * @param light the light source
//     * @param gp the point on the geometry that we're shading
//     * @param l the vector from the light source to the point on the geometry
//     * @param n the normal vector of the point
//     * @return The color of the pixel.
//     */
//    private boolean unshaded(LightSource light, GeoPoint gp, Vector l, Vector n) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//        Ray lightRay = new Ray(gp.point, lightDirection, n); // refactored ray head move
//        List<GeoPoint> intersections = this.scene.getGeometries().findGeoIntersections(lightRay, light.getDistance(gp.point));
//        if (intersections != null) {
//            for (GeoPoint intersection : intersections) {
//                if (intersection.geometry.getMaterial().kT == Double3.ZERO) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//}