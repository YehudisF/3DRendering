package lighting;
import static java.awt.Color.*;

import org.junit.jupiter.api.Test;
import renderer.ImageWriter;
import lighting.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import static java.awt.Color.*;

public class AllEffectsTest {



    private Scene scene= new Scene.SceneBuilder("Test Scene").build();
    @Test
    public void AllEffectsTest() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

//        scene.getLights().add( //
//                new SpotLight(new Color(PINK), new Point(-50, -50, 200), new Vector(1, 1, -3)) //
//                        .setkL(1E-5).setkQ(1.5E-7));
        Material trMaterial = new Material().setKd(0.5).setKs(0.5).setnShininess(30);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE),new Double3(0.25)));
        scene.getGeometries().add(
//                new Cylinder(new Ray(new Point(0,0,0),new Vector(0,1,0)),5,-0d)
//                        .setEmission(new Color(BLUE))
//                        .setMaterial(new Material().setnShininess(30).setKd(0.5).setKs(0.5)),
//                new Polygon(new Point(-20,20,0),new Point(20,20,0)
//                        ,new Point(20,60,0),new Point(-20,60,0))
//                        .setEmission(new Color(WHITE)).setMaterial(trMaterial),
                //.setMaterial(new Material().setnShininess(30).setKd(0.5).setKs(0.5)),
                new Sphere (new Point(-0,-70,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
                new Sphere (new Point(0,-50,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
                new Sphere (new Point(0,-30,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
                new Sphere (new Point(0,-10,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
                new Sphere (new Point(0,10,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
                new Sphere (new Point(0,30,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
                new Sphere (new Point(0,50,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
                new Sphere (new Point(0,70,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
                new Tube(new Ray(new Point(0,0,0),new Vector(0,1,0)),20).setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.4).setKr(0.1).setnShininess(30))
//                new Sphere (new Point(0,50,0),3).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                new Sphere (new Point(0,50,0),3).setEmission(new Color(DARK_GRAY)).setMaterial(trMaterial),
//                new Sphere (new Point(0,50,0),3).setEmission(new Color(YELLOW)).setMaterial(trMaterial),




                //new Polygon(new Point(-5,-70,0),new Point(5,-70,0),new Point(5,20,0),new Point(-5,20,0)).setEmission(new Color(WHITE))
        );

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setkL(4E-5).setkQ(2E-7));
        scene.getLights().add( //
                new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setkL(4E-4).setkQ(2E-5));


        ImageWriter imageWriter = new ImageWriter("myPicture", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }











//        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setVPSize(150, 150).setVPDistance(1000);
//
//        scene.setAmbientLight(new AmbientLight(new Color(PINK),new Double3(0.25)));
//        scene.getGeometries().add(
//                //new Sphere(new Point(0,50,0),4).setEmission(new Color(BLUE)),
//                new Cylinder(new Ray(new Point(0,0,0),new Vector(0,1,0)),5,-0d)
//                        .setEmission(new Color(BLUE))
//                        .setMaterial(new Material().setnShininess(30).setKd(0.5).setKs(0.5)),
//                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -50), new Point(75, 75, -150)) //
//                        .setMaterial(new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setnShininess(60)),
//                new Cube(new Point(0d,0d,0d),5))
//
//                ;
//
//        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
//                .setkL(4E-5).setkQ(2E-7));
//
//        ImageWriter imageWriter = new ImageWriter("myPicture", 600, 600);
//        camera.setImageWriter(imageWriter) //
//                .setRayTracer(new RayTracerBasic(scene)) //
//                .renderImage() //
//                .writeToImage();
   }













//
//    private Intersectable sphere = new Sphere(new Point(0, 0, -200), 60d) //
//            .setEmission(new Color(BLUE)) //
//            .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(30));
//    private Material trMaterial = new Material().setKd(0.5).setKs(0.5).setnShininess(30);
//
//    private Scene scene =  new Scene.SceneBuilder("Test scene").build();
//    private Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//            .setVPSize(200, 200).setVPDistance(1000) //
//            .setRayTracer(new RayTracerBasic(scene));
//
//    /**
//     * Helper function for the tests in this module
//     */
//    void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
//        scene.getGeometries().add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
//        scene.getLights().add( //
//                new SpotLight(new Color(400, 240, 0), spotLocation, new Vector(1, 1, -3)) //
//                        .setkL(1E-5).setkQ(1.5E-7));
//        camera.setImageWriter(new ImageWriter(pictName, 400, 400)) //
//                .renderImage() //
//                .writeToImage();
//    }
//
////    camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
////            .renderImage() //
////				.writeToImage();

