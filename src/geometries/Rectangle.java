package geometries;

import primitives.*;
/**
 * class for board that contains 6 polygons
 */
public class Rectangle extends Geometries {
//point is left botton corner
    public Rectangle(Point point, int width, int height, int depth, java.awt.Color color) {
//front polygon
        Material trMaterial = new Material().setKd(0.5).setKs(0.5).setKr(1d).setKt(0.1).setnShininess(300);
        Geometry polygon1 = new Polygon(point,
                new Point(point.getX()+width, point.getY(), point.getZ()),
                new Point(point.getX()+width, point.getY()+height, point.getZ()),
                new Point(point.getX(), point.getY()+height, point.getZ()))
                .setEmission(new Color(color).reduce(1.5))
                .setMaterial(trMaterial); //plane
//backpolygon
        Geometry polygon2 = new Polygon( new Point(point.getX(), point.getY(), point.getZ()+depth),
                new Point(point.getX()+width, point.getY(), point.getZ()+depth),
                new Point(point.getX()+width, point.getY()+height, point.getZ()+depth),
                new Point(point.getX(), point.getY()+height, point.getZ()+depth))
                .setEmission(new Color(color).reduce(1.5))
                .setMaterial(trMaterial); // parallel to the plane

        //top polygon
        Geometry polygon3 = new Polygon( new Point(point.getX(), point.getY()+height, point.getZ()),
                new Point(point.getX()+width, point.getY()+height, point.getZ()),
                new Point(point.getX()+width, point.getY()+height, point.getZ()+depth),
                new Point(point.getX(), point.getY()+height, point.getZ()+depth))
                .setEmission(new Color(color).reduce(1.5))
                .setMaterial(trMaterial); //#1
//botton polygon
        Geometry polygon4 = new Polygon( new Point(point.getX(), point.getY(), point.getZ()),
                new Point(point.getX()+width, point.getY(), point.getZ()),
                new Point(point.getX()+width, point.getY(), point.getZ()+depth),
                new Point(point.getX(), point.getY(), point.getZ()+depth))
                .setEmission(new Color(color).reduce(1.5))
                .setMaterial(trMaterial); //#2
//left side
        Geometry polygon5 = new Polygon(point,
                new Point(point.getX(), point.getY(), point.getZ()+depth),
                new Point(point.getX(), point.getY()+height, point.getZ()+depth),
                new Point(point.getX(), point.getY()+height, point.getZ()))
                .setEmission(new Color(color).reduce(1.5))
                .setMaterial(trMaterial); //#3
//right side
        Geometry polygon6 = new Polygon(new Point(point.getX()+width, point.getY(), point.getZ()),
                new Point(point.getX()+width, point.getY(), point.getZ()+depth),
                new Point(point.getX()+width, point.getY()+height, point.getZ()+depth),
                new Point(point.getX()+width, point.getY()+height, point.getZ()))
                .setEmission(new Color(color).reduce(1.5))
                .setMaterial(trMaterial); //#4

        add(polygon1);
        add(polygon2);
        add(polygon3);
        add(polygon4);
        add(polygon5);
        add(polygon6);
    }
}