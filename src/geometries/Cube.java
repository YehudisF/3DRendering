package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Ray;

import java.util.List;

public class Cube extends Intersectable{
    Point middle;
    double halfLength;
    double halfWidth;
    double halfHeight;
    Geometries squares;

    public Cube(Point middle, double halfLength,double halfWidth, double halfHeight) {
        this.middle = middle;
        this.halfLength = halfLength;
        this.halfHeight = halfHeight;
        this.halfWidth = halfWidth;
        double x = middle.getX();
        double y = middle.getY();
        double z = middle.getZ();
        Point bottomRightnorth = new Point(x+halfWidth,y-halfHeight,z+halfLength);
        Point bottomRightsouth = new Point(x+halfWidth,y-halfHeight,z-halfLength);
        Point bottomLeftnorth = new Point(x-halfWidth,y-halfHeight,z+halfLength);
        Point bottomLeftsouth = new Point(x-halfWidth,y-halfHeight,z-halfLength);
        Point topRightnorth = new Point(x+halfWidth,y+halfHeight,z+halfLength);
        Point topRightsouth = new Point(x+halfWidth,y+halfHeight,z-halfLength);
        Point topLeftnorth = new Point(x-halfWidth,y+halfHeight,z+halfLength);
        Point topLeftsouth = new Point(x-halfWidth,y+halfHeight,z-halfLength);


        Polygon topSquare =(Polygon) new Polygon(topLeftnorth,topRightnorth,topRightsouth,topLeftsouth).setEmission(new Color(0,75,100))
                .setMaterial(new Material()
                        .setKd(0.6).setKs(0.4)
                        .setnShininess(50));;
        Polygon bottomSquare =(Polygon) new Polygon(bottomLeftnorth,bottomRightnorth,bottomRightsouth,bottomLeftsouth).setEmission(new Color(0,75,100))
                .setMaterial(new Material()
                        .setKd(0.6).setKs(0.4)
                        .setnShininess(50));;
        Polygon rightSquare =(Polygon) new Polygon(topRightnorth,bottomRightnorth,bottomRightsouth,topRightsouth).setEmission(new Color(0,75,100))
                .setMaterial(new Material()
                        .setKd(0.6).setKs(0.4)
                        .setnShininess(50));;
        Polygon foreSquare =(Polygon) new Polygon(topRightsouth,bottomRightsouth,bottomLeftsouth,topLeftsouth).setEmission(new Color(0,75,100))
                .setMaterial(new Material()
                        .setKd(0.6).setKs(0.4)
                        .setnShininess(50));;
        Polygon leftSquare =(Polygon) new Polygon(topLeftsouth,bottomLeftsouth,bottomLeftnorth,topLeftnorth).setEmission(new Color(0,75,100))
                .setMaterial(new Material()
                        .setKd(0.6).setKs(0.4)
                        .setnShininess(50));;
        Polygon backSquare =(Polygon) new Polygon(topLeftnorth,bottomLeftnorth,bottomRightnorth,topRightnorth).setEmission(new Color(0,75,100))
                .setMaterial(new Material()
                        .setKd(0.6).setKs(0.4)
                        .setnShininess(50));
        squares = new Geometries(topSquare,bottomSquare,rightSquare,leftSquare,foreSquare,backSquare);
    }

//        public void setEmission(Color color){
//        squares
//        }
//

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return squares.findGeoIntersectionsHelper(ray,maxDistance);
    }

//    public rotateCubeZ(doube theta) {
//        var sinTheta = sin(theta);
//        var cosTheta = cos(theta);
//        for (var n = 0; n < nodes.length; n++) {
//            var node = nodes[n];
//            var x = node[0];
//            var y = node[1];
//            node[0] = x * cosTheta - y * sinTheta;
//            node[1] = y * cosTheta + x * sinTheta;
//        }
//    }

//    public cube(Point middle, double halfLength, Intersectable... intersectables) {
//        super(intersectables);
//        this.middle = middle;
//        this.halfLength = halfLength;
//    }
}
