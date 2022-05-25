
package lighting;

import org.junit.jupiter.api.Test;

import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

import static java.awt.Color.*;

public class tryagain {

    private Scene scene= new Scene.SceneBuilder("Test Scene").build();
    private Material material = new Material().setKd(new Double3(0.5)).setKs(new Double3(0.5)).setnShininess(300);
    @Test
    public void projTest()
    {

        Camera camera = new Camera(new Point(0, 300, 300), new Vector(-0.5, -0.25, -0.5), new Vector(0, 0.5, -0.1)) //
                .setVPSize(150, 150).setVPDistance(1000);
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
        for (int i=-230;i<150;i+=30)
            for(int j=-250;j<190;j+=30)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(WHITE);
                else
                    c=new Color(BLACK);

                scene.getGeometries().add( new Polygon(new Point(i+0.0, 0.0, j+0.0), new Point(i + 30, 0.0,j+0.0 ), new Point(i + 30,0.0 ,j + 30 ), new Point(i+0.0, 0.0, j + 30)).setEmission(c)
                );
            }


        ImageWriter imageWriter = new ImageWriter("myPicture2", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }








    @Test
    public void test_image1() {
        Camera camera = new Camera(new Point(0, -2000, 500), new Vector(0, 1, 0), new Vector(0, 0, 1))
                .setVPSize(600, 600).setVPDistance(650);

        Scene scene = new Scene.SceneBuilder("room").build();

        scene.getGeometries().add(
                //left wall
                new Polygon(new Point(-575, 0, 100), new Point(-575, 0, 1150), new Point(-500, -2000, 850), new Point(-500, -2000, -150))
                        .setEmission(new Color(255, 50, 50))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setnShininess(300)),

                //right wall
                new Polygon(new Point(575, 0, 100), new Point(575, 0, 1150), new Point(500, -2000, 850), new Point(500, -2000, -150))
                        .setEmission(new Color(119, 205, 90))
                        .setMaterial(new Material().setKd(0.5).setKs(0.2).setnShininess(300)),
                //back wall
                new Polygon(new Point(-575, 0, 100), new Point(-575, 0, 1150), new Point(575, 0, 1150), new Point(575, 0, 100))
                        .setEmission(new Color(255, 204, 238))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(300)),
                //ceiling
                new Polygon(new Point(-575, 0, 1150), new Point(-500, -2000, 850), new Point(500, -2000, 850), new Point(575, 0, 1150))
                        .setEmission(new Color(255, 204, 238))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(300)),
                //floor
                new Polygon(new Point(-575, 0, 100), new Point(-500, -2000, -150), new Point(500, -2000, -150), new Point(575, 0, 100))
                        .setEmission(new Color(255, 204, 238))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setnShininess(300)),
                //spheres
                new Sphere(new Point(-295, -550, 250), 150)
                        .setEmission(new Color(120, 120, 120)) //
                        .setMaterial(new Material().setKd(0.8).setKs(0.7).setnShininess(300)),
                new Sphere(new Point(150, -1000, 255), 180)
                        .setEmission(new Color(120, 120, 120)) //
                        .setMaterial(new Material().setKd(0.8).setKs(0.7).setnShininess(300))

        );

        // scene.lights.add(new SpotLight(new Color(white), new Point(0, -950, 1000), new Vector(0, 0, -1)));
        // scene.lights.add(new PointLight(new Color(white), new Point(0, -950, 1000)));


        // scene.lights.add(new SpotLight(new Color(white).reduce(10), new Point(0, -2000, 500), new Vector(0, 1, 0)));

        /*  for (int i = -140; i < 140; i += 5) {
            for (int j = -1000; j < -500; j += 10) {
                scene.lights.add(new PointLight(new Color(200, 200, 0), new Point(i, j, 950)));
            }
        }*/

        //  scene.lights.add(new DirectionalLight(new Color(200, 200, 0), new Vector(0.75, -0.017, -0.656)));

        // scene.lights.add(new SpotLight(new Color(255, 255, 90), new Point(0, -1000, 950), new Vector(-0.22, 0.195, -0.953)));
        //scene.lights.add(new SpotLight(new Color(255, 255, 90), new Point(0, -1000, 950), new Vector(0.15, 0.7, -0.675)));
        //   scene.lights.add(new PointLight(new Color(25, 255, 0), new Point(-350, -350, 798)));
        // scene.lights.add(new PointLight(new Color(200, 200, 0), new Point(-900, -300, 450)));


        ImageWriter imageWriter = new ImageWriter("room", 1000, 1000);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }
}
