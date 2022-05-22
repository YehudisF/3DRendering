package lighting;
import static java.awt.Color.*;

import org.junit.jupiter.api.Test;
import renderer.ImageWriter;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

public class ProjTest {
    private Scene scene= new Scene.SceneBuilder("Test Scene").build();
    @Test
    public void projTest()
    {

        Camera camera = new Camera(new Point(0, 1000, 1000), new Vector(0, -1, -1), new Vector(0, 1, -1)) //
                .setVPSize(150, 150).setVPDistance(1000);
        Scene scene= new Scene.SceneBuilder("Test Scene").setBackground(new Color(BLACK)).build();
//        scene.getLights().add( //
//                new SpotLight(new Color(PINK), new Point(-50, -50, 200), new Vector(1, 1, -3)) //
//                        .setkL(1E-5).setkQ(1.5E-7));
        Material trMaterial = new Material().setKd(0.5).setKs(0.5).setnShininess(30);
        scene.setAmbientLight(new AmbientLight(new Color(PINK),new Double3(0.25)));
        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(0, 70, 100), new Vector(0, 1, -1)) //
                .setkL(4E-5).setkQ(2E-7));
        scene.getLights().add(new DirectionalLight(new Color(700, 400, 400), new Vector(0,-0.5,-1)));
        scene.getGeometries().add(
//                new Cylinder(new Ray(new Point(0,0,0),new Vector(0,1,0)),5,-0d)
//                        .setEmission(new Color(BLUE))
//                        .setMaterial(new Material().setnShininess(30).setKd(0.5).setKs(0.5)),
//                new Polygon(new Point(-20,20,0),new Point(20,20,0)
//                        ,new Point(20,60,0),new Point(-20,60,0))
//                        .setEmission(new Color(WHITE)).setMaterial(trMaterial),
                //.setMaterial(new Material().setnShininess(30).setKd(0.5).setKs(0.5)),
              //  new Plane(new Point(1,0,1),new Vector(0,1,0)).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
        //new Sphere(new Point(-0,0,0),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
                new Polygon(new Point(-50,0,-50),new Point(50,0,-50),new Point(50,0,50),new Point(-50,0,50)).setEmission(new Color(MAGENTA)).setMaterial(trMaterial)
//                new Sphere (new Point(0,20,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//                new Sphere (new Point(0,-30,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                new Sphere (new Point(0,-10,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//                new Sphere (new Point(0,10,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                new Sphere (new Point(0,30,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//                new Sphere (new Point(0,50,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                new Sphere (new Point(0,70,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial)
              //  new Tube(new Ray(new Point(0,0,0),new Vector(0,1,0)),20).setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.0).setKr(0.1).setnShininess(30))
//                new Sphere (new Point(0,50,0),3).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                new Sphere (new Point(0,50,0),3).setEmission(new Color(DARK_GRAY)).setMaterial(trMaterial),
//                new Sphere (new Point(0,50,0),3).setEmission(new Color(YELLOW)).setMaterial(trMaterial),




        //new Polygon(new Point(-5,-70,0),new Point(5,-70,0),new Point(5,20,0),new Point(-5,20,0)).setEmission(new Color(WHITE))
        );

        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(0, 70, 100), new Vector(0, 1, -1)) //
                .setkL(4E-5).setkQ(2E-7));
        scene.getLights().add( //
                new SpotLight(new Color(700, 400, 400), new Point(0, 10,0 ), new Vector(0, -1, 0)) //
                        .setkL(4E-4).setkQ(2E-5));


        ImageWriter imageWriter = new ImageWriter("myPicture1", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}










