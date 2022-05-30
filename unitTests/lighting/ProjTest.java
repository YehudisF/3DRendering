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
    private Material material = new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setnShininess(300);
    @Test
    public void projTest()
    {

        Camera camera = new Camera(new Point(0, 150, 600), new Vector(0, -0.1, -0.5), new Vector(0, 0.5, -0.1)) //
                .setVPSize(150, 150).setVPDistance(1000)
                .move(new Double3(80d,0d,0d)).rotate(0,7,0d).setNumRays(81);
        Scene scene= new Scene.SceneBuilder("Test Scene").setBackground(new Color(BLACK)).build();
//        scene.getLights().add( //
//                new SpotLight(new Color(PINK), new Point(-50, -50, 200), new Vector(1, 1, -3)) //
//                        .setkL(1E-5).setkQ(1.5E-7));
        Material trMaterial = new Material().setKd(0.5).setKs(0.5).setKr(1d).setnShininess(300);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE),new Double3(0.25)));
//        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(0, 500, 500), new Vector(0, -0.9, -1)) //
//                .setkL(4E-5).setkQ(2E-7));
//        scene.getLights().add(new DirectionalLight(new Color(700, 400, 400), new Vector(0,0.2,-1)));
//        scene.getLights().add(new SpotLight(new Color(700, 400, 400),new Point(0,50,0),new Vector(0,-1,0)).setkL(4E-5).setkQ(2E-7));
     //   scene.getLights().add(new SpotLight(new Color(700, 400, 400),new Point(5,400,0),new Vector(0,-0.9,-0.02)).setkL(4E-5).setkQ(2E-7));
        scene.getLights().add(new SpotLight(new Color(700, 400, 400),new Point(5,200,0),new Vector(0,-0.2,0.8)).setkL(4E-5).setkQ(2E-7));


        //
//        scene.getLights().add(new SpotLight(new Color(700, 400, 400),new Point(0,50,-50),new Vector(0,-1,-1)).setkL(4E-5).setkQ(2E-7));
//        scene.getLights().add(new SpotLight(new Color(700, 400, 400),new Point(2,500,500),new Vector(-0.01,-0.4,0.5)));
        Color c;

        for (int i=-50;i<50;i+=30)
            for(int j=-150;j<90;j+=30)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(WHITE);
                else
                    c=new Color(BLACK);

                scene.getGeometries().add( new Polygon(new Point(i+0.0, 0.0, j+0.0), new Point(i + 30, 0.0,j+0.0 ), new Point(i + 30,0.0 ,j + 30 ), new Point(i+0.0, 0.0, j + 30)).setEmission(c)
                );
            }
        // the wall on the side
        for (int i=0;i<60;i+=10)
            for(int j=-150;j<90;j+=10)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(WHITE);
                else
                    c=new Color(GRAY);

                scene.getGeometries().add( new Polygon(new Point(-50, i+0.0, j+0.0), new Point(-50, i+0.0,j+10 ), new Point(-50,i+10 ,j + 10 ), new Point(-50, i+10, j + 0.0)).setEmission(c)
                );
            }
        // the back wall
        for (int i=-50;i<80;i+=10)
            for(int j=0;j<60;j+=10)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(WHITE);
                else
                    c=new Color(GRAY);

                scene.getGeometries().add( new Polygon(new Point(i+0.0, j+0.0, -150), new Point(i+10, j+0.0,-150 ), new Point(i+10,j+10 ,-150 ), new Point(i+0.0, j+10, -150)).setEmission(c)
                );
            }









//        for (int i=-30;i<50;i+=30)
//            for(int j=-150;j<90;j+=30)
//            {
//                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
//                    c=new Color(WHITE);
//                else
//                    c=new Color(BLACK);
//
//                scene.getGeometries().add( new Polygon(new Point(i+0.0, 0.0, j+0.0), new Point(i + 30, 0.0,j+0.0 ), new Point(i + 30,0.0 ,j + 30 ), new Point(i+0.0, 0.0, j + 30)).setEmission(c)
//                );
//            }



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
              //  new Plane(new Point(-100000,0,-100000),new Point(100000,0,-100000),new Point(100000,0,100000)).setMaterial(material),
       //       new Triangle(new Point(-50,0,-50),new Point(50,0,-50),new Point(50,0,50)).setMaterial(material),
         //     new Triangle(new Point(-50,0,-50),new Point(50,0,50),new Point(-50,0,50)).setMaterial(material),
              //  new Polygon(new Point(-50,0,-50),new Point(50,0,-50),new Point(50,0,50),new Point(-50,0,50)).setMaterial(material),
                //            new Polygon(new Point(-100,50,-101),new Point(100,50,-101),new Point(100,-50,-101),new Point(-100,-50,-101)).setEmission(new Color(WHITE)).setMaterial(trMaterial),
//                new Sphere (new Point(0,20,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//                new Sphere (new Point(0,-30,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                new Sphere (new Point(0,-10,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//                new Sphere (new Point(0,10,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                new Sphere (new Point(0,30,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//                new Sphere (new Point(0,50,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                new Sphere (new Point(0,70,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial)
              //  new Tube(new Ray(new Point(0,0,0),new Vector(0,1,0)),20).setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.0).setKr(0.1).setnShininess(30))

              //  new Sphere (new Point(0,50,0),8).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
                new Cylinder (new Ray( new Point(0,8,0),new Vector(0,-1,0)),3,8).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),

                new Sphere (new Point(10,20,0),3).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
                new Sphere (new Point(30,20,5),10).setEmission(new Color(BLUE)).setMaterial(trMaterial), // big sphere
                new Sphere (new Point(42,20,-2),4).setEmission(new Color(GREEN)).setMaterial(trMaterial)
             //   new Sphere (new Point(0,50,0),3).setEmission(new Color(YELLOW)).setMaterial(trMaterial)




        //new Polygon(new Point(-5,-70,0),new Point(5,-70,0),new Point(5,20,0),new Point(-5,20,0)).setEmission(new Color(WHITE))
        );

//        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(0, 70, 100), new Vector(0, 1, -1)) //
//                .setkL(4E-5).setkQ(2E-7));
//        scene.getLights().add( //
//                new SpotLight(new Color(700, 400, 400), new Point(0, 10,0 ), new Vector(0, -1, 0)) //
//                        .setkL(4E-4).setkQ(2E-5));
//

        ImageWriter imageWriter = new ImageWriter("myPicture1", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}










