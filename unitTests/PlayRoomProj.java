import geometries.*;
import lighting.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;

public class PlayRoomProj {
    private Scene scene= new Scene.SceneBuilder("Test Scene").setAmbientLight(new AmbientLight(new Color(pink), new Double3(0.15))) //
            .setBackground(new Color(cyan).reduce(1.1)).build();
    private Material material = new Material().setKd(new Double3(0.7)).setKs(new Double3(0.5)).setnShininess(300).setKr(new Double3(0.1)).setkG(1.0);
    Color c;

    //---------------------------------different materials for the shapes----------------------------------------------------------
    Material materialf=new Material().setnShininess(30).setKr(new Double3(0.7)).setkG(0.9).setkG(0.7).setKd(0.4);
    Material materialBear=new Material().setnShininess(40).setKd(0.5).setKs(0.5).setKr(new Double3(0.1));
    Material materialTable=new Material().setnShininess(100).setKd(0.3).setKs(0.7).setKr(new Double3(0.5));
    Material materialBlocks=new Material().setnShininess(10).setkG(0.2).setKs(0.1).setKr(new Double3(0.0)).setKt(new Double3(0.0));
    Material materialTrain=new Material().setnShininess(200).setkG(1.0).setKs(1.0).setKr(new Double3(0.0)).setKd(1.0).setKt(new Double3(0.2));
    Material materialTrain2=new Material().setnShininess(200).setkG(1.0).setKs(1.0).setKr(new Double3(0.0)).setKd(1.0).setKt(new Double3(0.5));
    Material cloudMaterial = new Material().setKd(0.5).setKs(0.5).setKr(new Double3(0.0)).setnShininess(50);

    Camera camera = new Camera(new Point(0, 150, 600), new Vector(0, -0.1, -0.5), new Vector(0, 0.5, -0.1)) //
            .setVPSize(150, 150).setVPDistance(1000)
            .move(new Double3(360d,0d,-60d)).rotate(0,33,0d);//.setNumRays(1);

    Geometry floor= new Polygon(new Point(-50, 0.0, -150), new Point(70, 0.0,-150), new Point(70,0.0 ,90), new Point(-50, 0.0, 90)).setEmission(new Color(BLACK)).setMaterial(materialBear);
    //-------------------------------------bear----------------------------------
    Geometries bear = new Geometries(                //teddy bear
            new Sphere (new Point(-25,8,-25),8).setEmission(new Color(78,53,36)).setMaterial(materialBear),//stomach
            new Sphere(new Point(-25,21.5,-25),6).setEmission(new Color(78,53,36)).setMaterial(materialBear),//head
            new Sphere(new Point(-26,24,-17),0.7).setEmission(new Color(BLACK)).setMaterial(materialBear),//eyes
            new Sphere(new Point(-21,24,-17),0.7).setEmission(new Color(BLACK)).setMaterial(materialBear),//eyes
            new Sphere(new Point(-25,20,-20),2).setEmission(new Color(188,158,130)).setMaterial(materialBear),// mouth
            new Triangle(new Point(-25,16,-21),new Point(-20,14,-21),new Point(-20,18,-21)).setEmission(new Color(0,35,102)).setMaterial(materialBear),//bow right
            new Triangle(new Point(-25,16,-21),new Point(-30,14,-21),new Point(-30,18,-21)).setEmission(new Color(0,35,102)).setMaterial(materialBear),//bow left
            new Sphere (new Point(-25,16,-21),0.5).setEmission(new Color(0,35,102)).setMaterial(materialBear),//bow middle sphere
            new Sphere(new Point(-27,28,-22),2).setEmission(new Color(78,53,36)).setMaterial(materialBear),//ears
            new Sphere(new Point(-21,28,-22),2).setEmission(new Color(78,53,36)).setMaterial(materialBear),//ears
            new Sphere(new Point(-31,14,-20),2.5).setEmission(new Color(78,53,36)).setMaterial(materialBear),//arms
            new Sphere(new Point(-17,14,-20),2.5).setEmission(new Color(78,53,36)).setMaterial(materialBear),//arms
            new Sphere(new Point(-34,3,-20),4).setEmission(new Color(78,53,36)).setMaterial(materialBear),//legs
            new Sphere(new Point(-16,3,-20),4).setEmission(new Color(78,53,36)).setMaterial(materialBear));//legs)
    //--------------------------------------------table--------------------------------
    Geometries table=new Geometries(
            new Cylinder (new Ray( new Point(25,20,-25),new Vector(0,-1,0)),20,4).setEmission(new Color(gray).reduce(1.7)).setMaterial(materialTable), // leg of table
            new Cylinder (new Ray( new Point(25,23,-25),new Vector(0,-1,0)),3,20).setEmission(new Color(gray).reduce(1.7)).setMaterial(materialTable), //top part of table
            new Rectangle(new Point(17,23,-25),5,5,5,cyan,1.1,materialBlocks),
            new Rectangle(new Point(27,23,-25),5,5,5,pink,1.7,materialBlocks),
            new Rectangle(new Point(37,23,-25),5,5,5,orange,1.7,materialBlocks),
            new Rectangle(new Point(23,28,-25),5,5,5,pink,1.7,materialBlocks),
            new Rectangle(new Point(33,28,-25),5,5,5,orange,1.7,materialBlocks),
            new Rectangle(new Point(28,33,-25),5,5,5,cyan,1.1,materialBlocks));

    //--------------------------------------------------train-----------------------
    Geometries train=new Geometries(

            new Rectangle(new Point(0,2,40),15,2,6,RED,1.5,materialTrain),
            new Rectangle(new Point(2,4,40),8,3,6,BLUE,1.5,materialTrain),
            new Rectangle(new Point(10,4,40),5,5,6,GREEN,1.5,materialTrain),
            new Cylinder(new Ray(new Point(3,1,42),new Vector(0,0,1)),6.5,1).setEmission(new Color(BLACK)).setMaterial(materialBear),
            new Cylinder(new Ray(new Point(6,1,42),new Vector(0,0,1)),6.5,1).setEmission(new Color(BLACK)).setMaterial(materialBear),
            new Cylinder(new Ray(new Point(11,1.5,42),new Vector(0,0,1)),6.5,2).setEmission(new Color(BLACK)).setMaterial(materialBear),
            new Rectangle(new Point(9,9,40),7,1,6,YELLOW,1.5,materialTrain),
            new Cylinder (new Ray( new Point(4,10,40),new Vector(0,-1,0)),3,1).setEmission(new Color(BLUE)).setMaterial(materialBear),
            new Rectangle(new Point(20,4,40),5,5,6,GREEN,1.5,materialTrain),
            new Rectangle(new Point(20,2,40),5,2,6,RED,1.5,materialTrain),
            new Rectangle(new Point(30,4,40),5,5,6,GREEN,1.5,materialTrain),
            new Rectangle(new Point(30,2,40),5,2,6,RED,1.5,materialTrain),
            new Cylinder(new Ray(new Point(23,1,42),new Vector(0,0,1)),6.5,1.3).setEmission(new Color(BLACK)).setMaterial(materialBear),
            new Cylinder(new Ray(new Point(33,1,42),new Vector(0,0,1)),6.5,1.3).setEmission(new Color(BLACK)).setMaterial(materialBear),
            new Cylinder(new Ray(new Point(15,3,42),new Vector(1,0,0)),10,0.7).setEmission(new Color(BLACK)).setMaterial(materialBear),
            new Cylinder(new Ray(new Point(25,3,42),new Vector(1,0,0)),10,0.7).setEmission(new Color(BLACK)).setMaterial(materialBear));

    //--------------------------------------------------clouds-----------------------------
    Geometries clouds=new Geometries(
            new Sphere (new Point(4,10.5,40),0.5).setEmission(new Color(GRAY)).setMaterial(cloudMaterial),
            new Sphere (new Point(4.2,11,40),0.7).setEmission(new Color(GRAY)).setMaterial(cloudMaterial),
            new Sphere (new Point(4.7,12,40),1).setEmission(new Color(GRAY)).setMaterial(cloudMaterial),
            new Sphere (new Point(6,13,40),1.5).setEmission(new Color(GRAY)).setMaterial(cloudMaterial),
            new Sphere (new Point(7,13,40),1).setEmission(new Color(GRAY)).setMaterial(cloudMaterial));


    /**
     * test for the picture without an improvements
     */
    @Test
    public void tableSimple()
    {

        scene.getLights().addAll(List.of(
                new SpotLight(new Color(700, 500, 400), new Point(50, 200, 125), new Vector(-1, 1, -4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-50, -200, -125), new Vector(1, -1, 4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-300, -290, -125), new Vector(1, 5, 4)) //
                        .setkL(4E-3).setkQ(2E-4),
                new PointLight(new Color(135, 75, 100),new Point(90, -220, -50)), // lamp's light
                new DirectionalLight(new Color(pink).reduce(1.7), new Vector(2, 2, 4))));



        // the wall on the side
        for (int i=0;i<60;i+=50)
            for(int j=-150;j<90;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(cyan).reduce((1.5));
                else
                    c=new Color(pink).reduce(1.1);
                scene.getGeometries().add( new Polygon(new Point(-50, i+0.0, j+0.0), new Point(-50, i+0.0,j+50 ), new Point(-50,i+50 ,j + 50 ), new Point(-50, i+50, j + 0.0)).setEmission(c)
                );
            }
//        // the back wall
        for (int i=-50;i<80;i+=50)
            for(int j=0;j<60;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(pink).reduce(1.1);
                else
                    c=new Color(cyan).reduce(1.5);

                scene.getGeometries().add( new Polygon(new Point(i+0.0, j+0.0, -150), new Point(i+50, j+0.0,-150 ), new Point(i+50,j+50 ,-150 ), new Point(i+0.0, j+50, -150)).setEmission(c)
                );
            }


        scene.getGeometries().add(bear,table,train,clouds,floor);
        ImageWriter imageWriter = new ImageWriter("PlayRoomSimple", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * test the antialiasing
     */
    @Test
    public void tableAntialiasing()
    {


        scene.getGeometries().add(bear,table,train,clouds,floor);
        scene.getLights().addAll(List.of(
                new SpotLight(new Color(700, 500, 400), new Point(50, 200, 125), new Vector(-1, 1, -4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-50, -200, -125), new Vector(1, -1, 4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-300, -290, -125), new Vector(1, 5, 4)) //
                        .setkL(4E-3).setkQ(2E-4),
                new PointLight(new Color(135, 75, 100),new Point(90, -220, -50)), // lamp's light
                new DirectionalLight(new Color(pink).reduce(1.7), new Vector(2, 2, 4))));


        // the wall on the side
        for (int i=0;i<60;i+=50)
            for(int j=-150;j<90;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(cyan).reduce((1.5));
                else
                    c=new Color(pink).reduce(1.1);
                scene.getGeometries().add( new Polygon(new Point(-50, i+0.0, j+0.0), new Point(-50, i+0.0,j+50 ), new Point(-50,i+50 ,j + 50 ), new Point(-50, i+50, j + 0.0)).setEmission(c)
                );
            }
//        // the back wall
        for (int i=-50;i<80;i+=50)
            for(int j=0;j<60;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(pink).reduce(1.1);
                else
                    c=new Color(cyan).reduce(1.5);

                scene.getGeometries().add( new Polygon(new Point(i+0.0, j+0.0, -150), new Point(i+50, j+0.0,-150 ), new Point(i+50,j+50 ,-150 ), new Point(i+0.0, j+50, -150)).setEmission(c)
                );
            }


        ImageWriter imageWriter = new ImageWriter("playRoomAntialias", 600, 600);
        camera.setAntiAliasing(true).setAdaptive(true).setNumOfThreads(3);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }

    @Test
    public void RotationBonus()
    {

        scene.getGeometries().add(bear,table,train,clouds,floor);
        scene.getLights().addAll(List.of(
                new SpotLight(new Color(700, 500, 400), new Point(50, 200, 125), new Vector(-1, 1, -4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-50, -200, -125), new Vector(1, -1, 4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-300, -290, -125), new Vector(1, 5, 4)) //
                        .setkL(4E-3).setkQ(2E-4),
                new PointLight(new Color(135, 75, 100),new Point(90, -220, -50)), // lamp's light
                new DirectionalLight(new Color(pink).reduce(1.7), new Vector(2, 2, 4))));


        // the wall on the side
        for (int i=0;i<60;i+=50)
            for(int j=-150;j<90;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(cyan).reduce((1.5));
                else
                    c=new Color(pink).reduce(1.1);
                scene.getGeometries().add( new Polygon(new Point(-50, i+0.0, j+0.0), new Point(-50, i+0.0,j+50 ), new Point(-50,i+50 ,j + 50 ), new Point(-50, i+50, j + 0.0)).setEmission(c)
                );
            }
//        // the back wall
        for (int i=-50;i<80;i+=50)
            for(int j=0;j<60;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(pink).reduce(1.1);
                else
                    c=new Color(cyan).reduce(1.5);

                scene.getGeometries().add( new Polygon(new Point(i+0.0, j+0.0, -150), new Point(i+50, j+0.0,-150 ), new Point(i+50,j+50 ,-150 ), new Point(i+0.0, j+50, -150)).setEmission(c)
                );
            }
        Camera camera2 = new Camera(new Point(0, 0, -1000), new Vector(0, 0, -1), new Vector(0, -1, 0))
                .setVPSize(600, 600).setVPDistance(1000);
        int frames = 10;
        double angle = 360d / frames,
                angleRadians = 2 * Math.PI / frames,
                radius = camera2.getp0().subtract(Point.ZERO).length();
        Camera.Rotation(scene, camera2, frames, angle, angleRadians, radius);

    }

    /**
     * test for the softshadows
     */
    @Test
    public void SoftShadow()
    {
        scene.getGeometries().add(bear,table,train,clouds,floor);
        scene.getLights().addAll(List.of(
                new SpotLight(new Color(700, 500, 400), new Point(50, 200, 125), new Vector(-1, 1, -4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-50, -200, -125), new Vector(1, -1, 4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-300, -290, -125), new Vector(1, 5, 4)) //
                        .setkL(4E-3).setkQ(2E-4),
                new PointLight(new Color(135, 75, 100),new Point(90, -220, -50)), // lamp's light
                new DirectionalLight(new Color(pink).reduce(1.7), new Vector(2, 2, 4))));


        // the wall on the side
        for (int i=0;i<60;i+=50)
            for(int j=-150;j<90;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(cyan).reduce((1.5));
                else
                    c=new Color(pink).reduce(1.1);
                scene.getGeometries().add( new Polygon(new Point(-50, i+0.0, j+0.0), new Point(-50, i+0.0,j+50 ), new Point(-50,i+50 ,j + 50 ), new Point(-50, i+50, j + 0.0)).setEmission(c)
                );
            }
//        // the back wall
        for (int i=-50;i<80;i+=50)
            for(int j=0;j<60;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(pink).reduce(1.1);
                else
                    c=new Color(cyan).reduce(1.5);

                scene.getGeometries().add( new Polygon(new Point(i+0.0, j+0.0, -150), new Point(i+50, j+0.0,-150 ), new Point(i+50,j+50 ,-150 ), new Point(i+0.0, j+50, -150)).setEmission(c)
                );
            }


        ImageWriter imageWriter = new ImageWriter("playRoomsoftShadow", 600, 600);
        camera.setSoftShadow(true).setNumOfThreads(3).setNumRays(100).setAdaptive(true);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene).setSoftshadows(true)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * this is hte picture with the improvements
     */
    public void FinalPicture()
    {
        scene.getGeometries().add(bear,table,train,clouds,floor);
        scene.getLights().addAll(List.of(
                new SpotLight(new Color(700, 500, 400), new Point(50, 200, 125), new Vector(-1, 1, -4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-50, -200, -125), new Vector(1, -1, 4)) //
                        .setkL(4E-4).setkQ(2E-5),
                new SpotLight(new Color(600, 400, 300), new Point(-300, -290, -125), new Vector(1, 5, 4)) //
                        .setkL(4E-3).setkQ(2E-4),
                new PointLight(new Color(135, 75, 100),new Point(90, -220, -50)), // lamp's light
                new DirectionalLight(new Color(pink).reduce(1.7), new Vector(2, 2, 4))));


        // the wall on the side
        for (int i=0;i<60;i+=50)
            for(int j=-150;j<90;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(cyan).reduce((1.5));
                else
                    c=new Color(pink).reduce(1.1);
                scene.getGeometries().add( new Polygon(new Point(-50, i+0.0, j+0.0), new Point(-50, i+0.0,j+50 ), new Point(-50,i+50 ,j + 50 ), new Point(-50, i+50, j + 0.0)).setEmission(c)
                );
            }
//        // the back wall
        for (int i=-50;i<80;i+=50)
            for(int j=0;j<60;j+=50)
            {
                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
                    c=new Color(pink).reduce(1.1);
                else
                    c=new Color(cyan).reduce(1.5);

                scene.getGeometries().add( new Polygon(new Point(i+0.0, j+0.0, -150), new Point(i+50, j+0.0,-150 ), new Point(i+50,j+50 ,-150 ), new Point(i+0.0, j+50, -150)).setEmission(c)
                );
            }


        ImageWriter imageWriter = new ImageWriter("playRoomFinal", 600, 600);
        camera.setAdaptive(true).setNumOfThreads(3).setAntiAliasing(true);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }


}
