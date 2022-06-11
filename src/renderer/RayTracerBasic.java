package renderer;

import geometries.FlatGeometry;
import geometries.Geometries;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.random;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * derivative class from RayTracer traces ray path in the scene noting intersections
 * with geometries in the scene
 */
public class RayTracerBasic extends RayTracer {
//    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final Double MIN_CALC_COLOR_K = 0.001;
    public static final Double3 INITIAL_K = Double3.ONE;

    private int glossinessRays=10;

    private int beamRay = 16;


    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    public RayTracerBasic setGlossinessRays(int glossinessRays) {
        if (glossinessRays <= 0) {
            throw new IllegalArgumentException("number of glossiness rays should be greater than 0");
        }

        this.glossinessRays = glossinessRays;
        return this;
    }

    public int getBeamRay() {
        return beamRay;
    }

    public RayTracerBasic setBeamRay(int beamRay) {
        this.beamRay = beamRay;
        return this;
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
        return calcColour(closetPoint, ray);
    }

    /**
     * @param ray
     * @return closest geopoint in intersection list
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersectionPoints = scene.getGeometries().findGeoIntersections(ray);
        if (intersectionPoints == null) {
            return null;
        }
        GeoPoint closestPoint = ray.findClosestGeoPoint(intersectionPoints);
        return closestPoint;
    }

    /**
     * calls the recursive colour calculating function add with the ambient light
     */

    private Color calcColour(GeoPoint gp, Ray ray) {

        return calcColour(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.getAmbienLight().getIntensity());
    }

    /**
     *
     * @param rays the list of rays for current pixel
     * @param middleRay the dominant ray
     * @return the average coolor calculated according to all the rays colours
     */
    @Override
    public Color averageColor(List<Ray> rays,Ray middleRay){
        Color color=Color.BLACK;
        for( Ray ray:rays){
            color=color.add(traceRay(ray));
        }
        double rayRatio = rays.size()/10; // the number of rays
        color = color.add(traceRay(middleRay).scale(rayRatio));
        return color.reduce(Double.valueOf(rays.size()) + rayRatio);
    }

    /**
     * the recursive function to calculate the colour with lights and transparency and reflectance factors
     * @param gp wantd point
     * @param ray
     * @param level recursive level in colour calculation
     * @param k
     * @return the colour with all added effects
     */
    private synchronized Color calcColour(GeoPoint gp, Ray ray, int level, Double3 k) {

        Color color = gp.geometry.getEmission().add(calcLocalEffects(gp, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));

    }

    /**
     * factors in the effects of lighting reflection and refraction
     * @param geopoint wanted point
     * @param inRay
     * @param level level for recursive
     * @param k
     * @return
     */
    private Color calcGlobalEffects(GeoPoint geopoint, Ray inRay, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = geopoint.geometry.getMaterial();
        Double3 kr = material.kR;
        Double3 kkr = k.product(kr);
        Vector v=inRay.getDir();
        Vector n = geopoint.geometry.getNormal(geopoint.point);//
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
        {
            Ray[] reflectedRays = constructReflectedRays(geopoint.point, v, n, material.kG, glossinessRays);
            for (Ray reflectedRay : reflectedRays)
            {
                GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
                if (reflectedPoint != null)
                {
                    color = color.add(calcColour(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
                }
            }
        }
        Double3 kt = material.kT;
        Double3 kkt = k.product(kt);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
        {
            Ray[] refractedRays = constructRefractedRays(geopoint.point, v, n.scale(-1), material.kG, glossinessRays);
            for (Ray refractedRay : refractedRays)
            {
                GeoPoint refractedPoint = findClosestIntersection(refractedRay);
                if (refractedPoint != null)
                {
                    color = color.add(calcColour(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
                }
            }
        }
        return color;
    }



    /**
     * calculates the concurrent local effects such a specular and diffusion effects
     *
     * @param gp the wantd point
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
        Double3 ktr;
        for (LightSource lightSource : scene.getLights()) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                if (softshadows) {
                    ktr = transparencyBeam(lightSource, n, gp);
                }
                else {
                    ktr = transparency(l, n, lightSource, gp);
                }
                    if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
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
        return new Ray(pointGeo.point, inRay.getDir(), n);
    }

    /**
     * Constructs randomized refraction rays at the intersection point according to kG.
     * If kG is 1 then only one ray is returned with the vector v (which is the specular vector).
     *
     * @param point the intersection point
     * @param v     the intersection's ray direction
     * @param n     the normal at the intersection point
     * @param kG    the glossiness parameter in range of [0,1], where 0 - matte, 1 - glossy
     * @return randomized refraction rays
     */
    private Ray[] constructRefractedRays(Point point, Vector v, Vector n, double kG, int numOfRays) {
        // If kG is equals to 1 then return only 1 ray, the specular ray (v)
        if (isZero(kG - 1)) {
            return new Ray[]{new Ray(point, v, n)};
        }

        Vector[] randomizedVectors = createRandomVectorsOnSphere(n, numOfRays);

        // If kG is equals to 0 then select all the randomized vectors
        if (isZero(kG)) {
            return Arrays.stream(randomizedVectors)
                    .map(vector -> new Ray(point, vector, n))
                    .toArray(Ray[]::new);
        }

        // If kG is in range (0,1) then move the randomized vectors towards the specular vector (v)
        return Arrays.stream(randomizedVectors)
                .map(vector -> new Ray(point,
                        vector.scale(1 - kG).add(v.scale(kG)), n))
                .toArray(Ray[]::new);
    }

    private Ray constructReflectedRay(Vector n, Point pointGeo, Ray inRay) {

        Vector v = inRay.getDir();
        double vn = v.dotProduct(n);
        Vector r = v.subtract(n.scale(2 * vn)).normalize();
        return new Ray(pointGeo, r, n);
    }

    /**
     * Constructs randomized reflection rays at the intersection point according to kG.
     * If kG is 1 then only one ray is returned with the specular vector
     *
     * @param point the intersection point
     * @param v     the intersection's ray direction
     * @param n     the normal at the intersection point
     * @param kG    the glossiness parameter in range of [0,1], where 0 - matte, 1 - glossy
     * @return randomized reflection rays
     */
    private Ray[] constructReflectedRays(Point point, Vector v, Vector n, double kG, int numOfRays) {
        Vector n2vn = n.scale(-2 * v.dotProduct(n));
        Vector r = v.add(n2vn);

        // If kG is equals to 1 then return only 1 ray, the specular ray (r)
        if (isZero(kG - 1)) {
            return new Ray[]{new Ray(point, r, n)};
        }

        Vector[] randomizedVectors = createRandomVectorsOnSphere(n, numOfRays);

        // If kG is equals to 0 then select all the randomized vectors
        if (isZero(kG)) {
            return Arrays.stream(randomizedVectors)
                    .map(vector -> new Ray(point, vector, n))
                    .toArray(Ray[]::new);
        }

        // If kG is in range (0,1) then move the randomized vectors towards the specular vector (v)
        return Arrays.stream(randomizedVectors)
                .map(vector -> new Ray(point,
                        vector.scale(1 - kG).add(r.scale(kG)), n))
                .toArray(Ray[]::new);
    }

    /**
     * Creates random vectors on the unit hemisphere with a given normal on the hemisphere's bottom.<br>
     * source: https://my.eng.utah.edu/~cs6958/slides/pathtrace.pdf#page=18
     *
     * @param n normal to the hemisphere's bottom
     * @return the randomized vectors
     */
    private Vector[] createRandomVectorsOnSphere(Vector n, int numOfVectors) {
        // pick axis with smallest component in normal
        // in order to prevent picking an axis parallel
        // to the normal and eventually creating zero vector
        Vector axis;
        if (Math.abs(n.getX()) < Math.abs(n.getY()) && Math.abs(n.getX()) < Math.abs(n.getZ())) {
            axis = new Vector(1, 0, 0);
        } else if (Math.abs(n.getY()) < Math.abs(n.getZ())) {
            axis = new Vector(0, 1, 0);
        } else {
            axis = new Vector(0, 0, 1);
        }

        // find two vectors orthogonal to the normal
        Vector x = n.crossProduct(axis);
        Vector z = n.crossProduct(x);

        Vector[] randomVectors = new Vector[numOfVectors];
        for (int i = 0; i < numOfVectors; i++) {
            // pick a point on the hemisphere bottom
            double u, v, u2, v2;
            do {
                u = random() * 2 - 1;
                v = random() * 2 - 1;
                u2 = u * u;
                v2 = v * v;
            } while (u2 + v2 >= 1);

            // calculate the height of the point
            double w = Math.sqrt(1 - u2 - v2);

            // create the new vector according to the base (x, n, z) and the coordinates (u, w, v)
            randomVectors[i] = x.scale(u)
                    .add(z.scale(v))
                    .add(n.scale(w));
        }

        return randomVectors;
    }

    /**
     * for soft shadowing ,creates a beam of circle of rays to to geometry thus calculating the average shading
     * @param lightSource the light source being tested for shadowing
     * @param n the normal
     * @param geoPoint the point tested for shadowing
     * @return
     */
    private Double3 transparencyBeam(LightSource lightSource, Vector n, GeoPoint geoPoint) {
        Double3 tempKtr = new Double3(0d);
        List<Vector> beamL = lightSource.getBeamL(geoPoint.point, beamRadius, beamRay);

        for (Vector vl : beamL) {
           tempKtr = tempKtr.add(transparency(vl,n,lightSource, geoPoint));
        }
        tempKtr = tempKtr.reduce(beamL.size());

        return tempKtr;
    }


    /**
     * calculates the diffusion effect in a ray hitting  a surface
     *
     * @param material
     * @param nl
     * @return the diffusion effect
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(Math.abs(nl));
    }


    /**
     * calculates the specular effect in a ray intersection with a geometry
     *
     * @param material teh material in question
     * @param n normal to point
     * @param l the lights direction vector
     * @param nl
     * @param v
     * @return the specular parameter
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(nl * 2));
        double minusVR = alignZero(-v.dotProduct(r));
        if (minusVR <= 0)
            return Double3.ZERO;
        return material.kS.scale(Math.pow(minusVR, material.nShininess));
    }

    /**
     * checks whether a point on  a geometry is shaded
     *
     * @param gp point tried
     * @param lightSource
     * @param n
     * @return
     */
    private Double3 transparency(Vector l, Vector n, LightSource lightSource, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source//
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        double lightDistance = lightSource.getDistance(gp.point);
        var intersections = scene.getGeometries().findGeoIntersections(lightRay);
        if (intersections == null)
            return Double3.ONE; //no intersection
        for (var geo : intersections) {
            double dist = geo.point.distance(gp.point);
            if (dist >= lightDistance) {
                intersections.remove(geo);
            }
        }
        if (intersections.isEmpty()) {
            return Double3.ONE;
        }
        Double3 ktr = Double3.ONE;
        for (GeoPoint geopoint : intersections) {
            ktr = ktr.product(geopoint.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K))
                return Double3.ZERO;
        }
        return ktr;

    }

}