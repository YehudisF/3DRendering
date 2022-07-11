//package lighting;
//import static java.awt.Color.*;
//
//import org.junit.jupiter.api.Test;
//import renderer.ImageWriter;
//import geometries.*;
//import primitives.*;
//import renderer.*;
//import scene.Scene;
//
//
//public class PlayRoom
//{
//
//
//
//        private Scene scene= new Scene.SceneBuilder("Test Scene").build();
//        private Material material = new Material().setKd(new Double3(0.7)).setKs(new Double3(0.5)).setnShininess(300).setKr(1.0).setkG(1.0);
//        @Test
//        public void table()
//        {
//
//            Camera camera = new Camera(new Point(0, 150, 600), new Vector(0, -0.1, -0.5), new Vector(0, 0.5, -0.1)) //
//                    .setVPSize(150, 150).setVPDistance(1000)
//                    .move(new Double3(80d,0d,0d)).rotate(0,7,0d)
//                    .setAntiAliasing(true);
//            Scene scene= new Scene.SceneBuilder("Test Scene").setBackground(new Color(BLACK)).build();
////        scene.getLights().add( //
////                new SpotLight(new Color(PINK), new Point(-50, -50, 200), new Vector(1, 1, -3)) //
////                        .setkL(1E-5).setkQ(1.5E-7));
//            Material trMaterial = new Material().setKd(0.5).setKs(0.5).setKr(1d).setnShininess(300);
//            //scene.setAmbientLight(new AmbientLight(new Color(WHITE),new Double3(0.25)));
////        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(0, 500, 500), new Vector(0, -0.9, -1)) //
////                .setkL(4E-5).setkQ(2E-7));
////        scene.getLights().add(new DirectionalLight(new Color(700, 400, 400), new Vector(0,0.2,-1)));
////        scene.getLights().add(new SpotLight(new Color(700, 400, 400),new Point(0,50,0),new Vector(0,-1,0)).setkL(4E-5).setkQ(2E-7));
//            //   scene.getLights().add(new SpotLight(new Color(700, 400, 400),new Point(5,400,0),new Vector(0,-0.9,-0.02)).setkL(4E-5).setkQ(2E-7));
//            scene.getLights().add(new SpotLight(new Color(YELLOW),new Point(5,200,0),new Vector(0,-0.2,0.8)).setkL(4E-5).setkQ(2E-7));
//            scene.getLights().add(new SpotLight(new Color(YELLOW),new Point(5,200,-70),new Vector(0,-0.2,0.8)).setkL(4E-5).setkQ(2E-7));
//            scene.getLights().add(new SpotLight(new Color(YELLOW),new Point(100,50,-25),new Vector(                                                                                        4,-0.2,0.8)).setkL(4E-5).setkQ(2E-7));
//
//            //
////        scene.getLights().add(new SpotLight(new Color(700, 400, 400),new Point(0,50,-50),new Vector(0,-1,-1)).setkL(4E-5).setkQ(2E-7));
////        scene.getLights().add(new SpotLight(new Color(700, 400, 400),new Point(2,500,500),new Vector(-0.01,-0.4,0.5)));
//            Color c;
//            Material materialf=new Material().setnShininess(30).setKr(0.7).setkG(0.9).setKs(0.7).setKd(0.4);
//
////        for (int i=-50;i<70;i+=50)
////            for(int j=-150;j<90;j+=50)
////            {
////                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
////                    c=new Color(192,192,192);
////                else
////                    c=new Color(BLACK);
////
////                scene.getGeometries().add( new Polygon(new Point(i+0.0, 0.0, j+0.0), new Point(i + 50, 0.0,j+0.0 ), new Point(i + 50,0.0 ,j + 50 ), new Point(i+0.0, 0.0, j + 50)).setEmission(c).setMaterial(materialf)
////                );
////            }
//
//            scene.getGeometries().add( new Polygon(new Point(-50, 0.0, -150), new Point(70, 0.0,-150), new Point(70,0.0 ,90), new Point(-50, 0.0, 90)).setEmission(new Color(BLACK)).setMaterial(materialf));
//
//
//
//
//            // the wall on the side
////            for (int i=0;i<60;i+=50)
////                for(int j=-150;j<90;j+=50)
////                {
////                    if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
////                        c=new Color(WHITE);
////                    else
////                        c=new Color(GRAY);
////
////                    scene.getGeometries().add( new Polygon(new Point(-50, i+0.0, j+0.0), new Point(-50, i+0.0,j+50 ), new Point(-50,i+50 ,j + 50 ), new Point(-50, i+50, j + 0.0)).setEmission(c)
////                    );
////                }
//            // the back wall
////            for (int i=-50;i<80;i+=50)
////                for(int j=0;j<60;j+=50)
////                {
////                    if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
////                        c=new Color(WHITE);
////                    else
////                        c=new Color(GRAY);
////
////                    scene.getGeometries().add( new Polygon(new Point(i+0.0, j+0.0, -150), new Point(i+50, j+0.0,-150 ), new Point(i+50,j+50 ,-150 ), new Point(i+0.0, j+50, -150)).setEmission(c)
////                    );
////                }
//
//
//
//
//
//
//
//
//
////        for (int i=-30;i<50;i+=30)
////            for(int j=-150;j<90;j+=30)
////            {
////                if(((i/10)%2==0 &&(j/10)%2==0)||(Math.abs((i/10)%2)==1&& Math.abs((j/10)%2)==1))
////                    c=new Color(WHITE);
////                else
////                    c=new Color(BLACK);
////
////                scene.getGeometries().add( new Polygon(new Point(i+0.0, 0.0, j+0.0), new Point(i + 30, 0.0,j+0.0 ), new Point(i + 30,0.0 ,j + 30 ), new Point(i+0.0, 0.0, j + 30)).setEmission(c)
////                );
////            }
//
//
//            Material cloudMaterial = new Material().setKd(0.5).setKs(0.5).setKr(0.0).setnShininess(50);
//            scene.getGeometries().add(
////                new Cylinder(new Ray(new Point(0,0,0),new Vector(0,1,0)),5,-0d)
////                        .setEmission(new Color(BLUE))
////                        .setMaterial(new Material().setnShininess(30).setKd(0.5).setKs(0.5)),
////                new Polygon(new Point(-20,20,0),new Point(20,20,0)
////                        ,new Point(20,60,0),new Point(-20,60,0))
////                        .setEmission(new Color(WHITE)).setMaterial(trMaterial),
//                    //.setMaterial(new Material().setnShininess(30).setKd(0.5).setKs(0.5)),
//                    //  new Plane(new Point(1,0,1),new Vector(0,1,0)).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                    //new Sphere(new Point(-0,0,0),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                    //  new Plane(new Point(-100000,0,-100000),new Point(100000,0,-100000),new Point(100000,0,100000)).setMaterial(material),
//                    //       new Triangle(new Point(-50,0,-50),new Point(50,0,-50),new Point(50,0,50)).setMaterial(material),
//                    //     new Triangle(new Point(-50,0,-50),new Point(50,0,50),new Point(-50,0,50)).setMaterial(material),
//                    //  new Polygon(new Point(-50,0,-50),new Point(50,0,-50),new Point(50,0,50),new Point(-50,0,50)).setMaterial(material),
//                    //            new Polygon(new Point(-100,50,-101),new Point(100,50,-101),new Point(100,-50,-101),new Point(-100,-50,-101)).setEmission(new Color(WHITE)).setMaterial(trMaterial),
////                new Sphere (new Point(0,20,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
////                new Sphere (new Point(0,-30,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
////                new Sphere (new Point(0,-10,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
////                new Sphere (new Point(0,10,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
////                new Sphere (new Point(0,30,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
////                new Sphere (new Point(0,50,50),5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
////                new Sphere (new Point(0,70,50),5).setEmission(new Color(BLUE)).setMaterial(trMaterial)
//                    //  new Tube(new Ray(new Point(0,0,0),new Vector(0,1,0)),20).setEmission(new Color(BLACK)).setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.0).setKr(0.1).setnShininess(30))
//
//                    //  new Sphere (new Point(0,50,0),8).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                    // new Cylinder (new Ray( new Point(25,0,25),new Vector(0,-1,0)),5,16).setEmission(new Color(ORANGE)).setMaterial(material),
//                    //  new Cylinder (new Ray( new Point(0,20,0),new Vector(0,-1,0)),3,20).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                    new Sphere (new Point(-25,4,25),4).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//                    //              new Sphere (new Point(-25,16,-75),16).setEmission(new Color(170,169,173)).setMaterial(material), // big sphere
//
//                    //teddy bear
//                    new Sphere (new Point(-25,8,-75),8).setEmission(new Color(78,53,36)).setMaterial(trMaterial),//stomach
//                    new Sphere(new Point(-25,22,-75),6).setEmission(new Color(78,53,36)).setMaterial(trMaterial),//head
//                    new Sphere(new Point(-25,20,-70),2).setEmission(new Color(78,53,36)).setMaterial(trMaterial),// mouth
//                    new Triangle(new Point(-25,16,-70),new Point(-20,14,-70),new Point(-20,18,-70)).setEmission(new Color(0,35,102)).setMaterial(trMaterial),//bow right
//                    new Triangle(new Point(-25,16,-70),new Point(-30,14,-70),new Point(-30,18,-70)).setEmission(new Color(0,35,102)).setMaterial(trMaterial),//bow left
//                    new Sphere (new Point(-25,16,-70),0.5).setEmission(new Color(0,35,102)).setMaterial(trMaterial),//bow middle sphere
//                    new Sphere(new Point(-29,29,-70),2).setEmission(new Color(78,53,36)).setMaterial(trMaterial),//ears
//                    new Sphere(new Point(-20,29,-70),2).setEmission(new Color(78,53,36)).setMaterial(trMaterial),//ears
//                    new Sphere(new Point(-32,14,-70),2).setEmission(new Color(78,53,36)).setMaterial(trMaterial),//arms
//                    new Sphere(new Point(-17,14,-70),2).setEmission(new Color(78,53,36)).setMaterial(trMaterial),//arms
//                    new Sphere(new Point(-34,3,-70),4).setEmission(new Color(78,53,36)).setMaterial(trMaterial),//legs
//                    new Sphere(new Point(-16,3,-70),4).setEmission(new Color(78,53,36)).setMaterial(trMaterial),//legs
//
////                    //blocks on the floor
////                    new Rectangle(new Point(20,0,40),5,5,5,RED),
////                    new Rectangle(new Point(28,0,40),5,5,5,BLUE),
////                    new Rectangle(new Point(36,0,40),5,5,5,GREEN),
////                    new Rectangle(new Point(23,5,40),5,5,5,RED),
////                    new Rectangle(new Point(31,5,40),5,5,5,BLUE),
////                    new Rectangle(new Point(26,10,40),5,5,5,GREEN),
//
//
//
//
//                    // balls on the floor
//                    new Sphere (new Point(0,4,0),4).setEmission(new Color(GREEN)).setMaterial(trMaterial),
//                    new Sphere (new Point(0,4,25),4).setEmission(new Color(YELLOW)).setMaterial(trMaterial),
//                    new Sphere (new Point(0,4,50),4).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//                    // new Sphere (new Point(25,4,-25),4).setEmission(new Color(yellow)).setMaterial(trMaterial)
//
//                    //table
//                    new Cylinder (new Ray( new Point(25,23,-25),new Vector(0,-1,0)),3,20).setEmission(new Color(MAGENTA)).setMaterial(trMaterial), //top part of table
//                    new Cylinder (new Ray( new Point(25,20,-25),new Vector(0,-1,0)),20,4).setEmission(new Color(MAGENTA)).setMaterial(trMaterial), // leg of table
////                new Sphere (new Point(18,25,-23),2).setEmission(new Color(BLACK)).setMaterial(trMaterial),
////                new Sphere (new Point(18,25,-27),2).setEmission(new Color(BLACK)).setMaterial(trMaterial),
////                new Sphere (new Point(32,25,-27),2).setEmission(new Color(BLACK)).setMaterial(trMaterial),
////                new Sphere (new Point(32,25,-23),2).setEmission(new Color(BLACK)).setMaterial(trMaterial),
//
//                    //train
//                    new Rectangle(new Point(19,28,-23),15,2,6,RED),
//                    new Rectangle(new Point(21,30,-23),8,3,6,BLUE),
//                    new Rectangle(new Point(29,30,-23),5,5,6,GREEN),
//                    new Cylinder(new Ray(new Point(22,27,-25),new Vector(0,0,1)),6.5,1).setEmission(new Color(BLACK)).setMaterial(trMaterial),
//                    new Cylinder(new Ray(new Point(25,27,-25),new Vector(0,0,1)),6.5,1).setEmission(new Color(BLACK)).setMaterial(trMaterial),
//                    new Cylinder(new Ray(new Point(30,27.5,-25),new Vector(0,0,1)),6.5,2).setEmission(new Color(BLACK)).setMaterial(trMaterial),
//                    new Rectangle(new Point(28,35,-23),7,1,6,YELLOW),
//                    new Cylinder (new Ray( new Point(23,36,-23),new Vector(0,-1,0)),3,1).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//
//                    //clouds
//                    new Sphere (new Point(23,36.5,-23),0.5).setEmission(new Color(GRAY)).setMaterial(cloudMaterial),
//                    new Sphere (new Point(23.2,37,-23),0.7).setEmission(new Color(GRAY)).setMaterial(cloudMaterial),
//                    new Sphere (new Point(23.7,38,-23),1).setEmission(new Color(GRAY)).setMaterial(cloudMaterial),
//                    new Sphere (new Point(25,39,-23),1.5).setEmission(new Color(GRAY)).setMaterial(cloudMaterial),
//                    new Sphere (new Point(26,39,-23),1).setEmission(new Color(GRAY)).setMaterial(cloudMaterial),
//
//
//                    new Polygon(new Point(30,31,-22),new Point(31,31,-22),new Point(31,34,-22),new Point(30,34,-22)).setEmission(new Color(RED)).setMaterial(trMaterial),
//                    new Polygon(new Point(31.5,33,-20),new Point(33.5,33,-20),new Point(33.5,35,-20),new Point(31.5,35,-20)).setEmission(new Color(RED)).setMaterial(trMaterial),
//                    //new Polygon(new Point())
//                    //new Cylinder(new Ray(new Point(18,37,-25),new Vector(0,-1,0)),3,1).setEmission(new Color(RED)).setMaterial(trMaterial),
//                    // new Rectangle(new Point(34,35,-28),18,10,6,RED,10,6,0),
//                    // new Cylinder (new Ray( new Point(25,25,-25),new Vector(0,-1,0)),9,5).setEmission(new Color(MAGENTA)).setMaterial(trMaterial),
//                    // new Cylinder (new Ray( new Point(25,30,-25),new Vector(0,-1,0)),6,5).setEmission(new Color(YELLOW)).setMaterial(trMaterial),
//                    //new Cylinder (new Ray( new Point(25,35,-25),new Vector(0,-1,0)),3,5).setEmission(new Color(BLUE)).setMaterial(trMaterial),
//                    //new Sphere (new Point(25,35,-25),3).setEmission(new Color(PINK)).setMaterial(trMaterial),
//
//                    new Sphere (new Point(0,4,50),4).setEmission(new Color(BLUE)).setMaterial(trMaterial)
//
//
//                    //   new Sphere (new Point(0,50,0),3).setEmission(new Color(YELLOW)).setMaterial(trMaterial)
//
//
//
//
//                    //new Polygon(new Point(-5,-70,0),new Point(5,-70,0),new Point(5,20,0),new Point(-5,20,0)).setEmission(new Color(WHITE))
//            );
//
////        scene.getLights().add(new SpotLight(new Color(700, 400, 400), new Point(0, 70, 100), new Vector(0, 1, -1)) //
////                .setkL(4E-5).setkQ(2E-7));
////        scene.getLights().add( //
////                new SpotLight(new Color(700, 400, 400), new Point(0, 10,0 ), new Vector(0, -1, 0)) //
////                        .setkL(4E-4).setkQ(2E-5));
////
//
//            ImageWriter imageWriter = new ImageWriter("Table", 600, 600);
//            camera.setImageWriter(imageWriter) //
//                    .setRayTracer(new RayTracerBasic(scene))
//                    .setSoftShadow(true)//
//                    .renderImage() //
//                    .writeToImage();
//        }
//    }
//
//
