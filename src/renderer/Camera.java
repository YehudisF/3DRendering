package renderer;


import primitives.*;
import lighting.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Camera producing rays through a view plane
 */
public class Camera {

    private  Point p0;          // camera eye
    private  Vector vUp;        // vector pointing upwards : Y axis
    private  Vector vTo;        // vector pointing towards the scene
    private  Vector vRight;     // vector pointing towards the right : X axis

    private double distance;    // cameras distance from ViewPlane
    private double width;       // ViewPlane width
    private double height;      // ViewPlane height




    private boolean antiAliasing = false;
    private int numRays = 25
            ; // number of rays per pixel for supersampling

    private ImageWriter _imageWriter;
    private RayTracer _rayTracer;

    public void setP0(Point p0) {
        this.p0 = p0;
    }

    public void setvUp(Vector vUp) {
        this.vUp = vUp;
    }

    public void setvTo(Vector vTo) {
        this.vTo = vTo;
    }

    public void setvRight(Vector vRight) {
        this.vRight = vRight;
    }



    /**
     * constructor for camera ensuring the 3 vectors are orthogonal
     * @param p0 origin  point in 3D space
     * @param vUp vechu
     * @param vTo vechulei
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        if(!isZero(vUp.dotProduct(vTo))){
            throw  new IllegalArgumentException("vTo and vUp should be orthogonal");
        }

        this.p0 = p0;

        //normalizing the positional vectors
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();

        this.vRight = this.vTo.crossProduct(this.vUp);

    }

    // chaining methods

    /**
     * set distance between the camera and it's view plane
     * @param distance the  distance for the view plane
     * @return instance of Camera for chaining
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * setting View Plane size
     * @param width     "physical" width
     * @param height    "physical" height
     * @return instance of Camera for chaining
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Constructing a ray through a pixel
     *
     * @param Nx number of pixels widthwise
     * @param Ny number of pixels heightwise
     * @param j Y value of pixel wanted
     * @param i x value of pixel wanted
     * @return ray form the camera to Pixel[i,j]
     */
     public Ray constructRay(int Nx, int Ny, int j, int i) { //like construct ray thru pixel
        //Image center
        Point Pc = p0.add(vTo.scale(distance));

        //Ratio (pixel width & height)
        double Ry =height/ Ny;
        double Rx = width/Nx;



        //delta values for going to Pixel[i,j]  from Pc

        double yI =  -(i - (Ny -1)/2d)* Ry;
        double xJ =  (j - (Nx -1)/2d)* Rx;

        if (! isZero(xJ) ){
            Pc = Pc.add(vRight.scale(xJ));
        }
        if (! isZero(yI)) {
            Pc = Pc.add(vUp.scale(yI));
        }

        return new Ray(p0, Pc.subtract(p0));
    }



    /**
     * calls the original write to image to create an image from rendered scene
     */
    public void writeToImage() {
        if(_imageWriter == null)
            throw new MissingResourceException("missing imagewriter", "Camera", "in writeTorImage");
        _imageWriter.writeToImage();
    }

    /**
     * The actual rendering function , according to data received from the ray tracer - colours each pixel appropriately thus
     * rendering the image
     */
    public Camera renderImage() {
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (_rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracer.class.getName(), "");
            }

            //rendering the image
            int nX = _imageWriter.getNx();
            int nY = _imageWriter.getNy();
            if(antiAliasing) // if the antiAliasing imprvoment was added than
            {
                for (int i = 0; i < nY; i++) {
                    for (int j = 0; j < nX; j++) {
                        List<Ray> rays = constructGridRaysThroughPixel(nX, nY, j, i);
                        Ray middleRay = constructRay(nX, nY, j, i);
                        Color pixelColor = _rayTracer.averageColor(rays, middleRay);
                        _imageWriter.writePixel(j, i, pixelColor);
                    }
                }
            }
            else
            {
                for (int i = 0; i < nY; i++) {
                    for (int j = 0; j < nX; j++)
                    {
                        Ray ray = constructRay(nX, nY, j, i);
                        Color pixelColor = _rayTracer.traceRay(ray,numRays);
                        _imageWriter.writePixel(j, i, pixelColor);
                    }
                }
            }

        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
        return this;
    }


    public Camera setSoftShadow(boolean isSoft){
        _rayTracer.setSoftshadows(isSoft);
        return this;
    }
    public Camera setRadiusBeam(double radiusBeam){
        _rayTracer.setBeamRadius(radiusBeam);
        return this;
    }

    /**
     *
     * @return wheter or not antialiasing will be performed
     */
    public boolean isAntiAliasing() {
        return antiAliasing;
    }

    /**
     * sets users choice whether to add antialiasing
     * @param antiAliasing
     * @return the camera
     */
    public Camera setAntiAliasing(boolean antiAliasing) {
        this.antiAliasing = antiAliasing;
        return this;
    }



    /**
     * paints the image as a grid according to the wanted interval and color of grid lines
     * @param interval length of wanted interval
     * @param color wanted color for grid lines
     */
    public void printGrid(int interval, Color color) {
        if(_imageWriter == null)
            throw new MissingResourceException("missing imageawriter", "Camera", "in print Grid");
        for (int j = 0; j< _imageWriter.getNx();j++){
            for (int i = 0; i< _imageWriter.getNy();i++){
                //grid 16 X 10
                if(j% interval == 0 || i% interval ==0){
                    _imageWriter.writePixel(j, i, color);
                }
            }
        }

    }

    public int getNumRays() {
        return numRays;
    }

    public Camera setNumRays(int numRays) {

        this.numRays = numRays;
        return this;
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this._imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracer rayTracer) {
        this._rayTracer = rayTracer;
        return this;
    }


//    /**
//     *
//     * @param nX
//     * @param nY
//     * @param j
//     * @param i
//     * @return
//     */
//    private Point getPij(int nX, int nY, int j, int i) {
//
//        // calculates  the intersection point of the to vector to the screen after it was scaled by the distance
//        Point Pc = p0.add(vTo.scale(distance));
//
//        double Ry = height / nY; // how high a pixel is
//        double Rx = width / nX; // how wide a pixel is
//
//        double yi = ((i - nY / 2d) * Ry + Ry / 2d); // y coordinate
//        double xj = ((j - nX / 2d) * Rx + Rx / 2d); // x coordinate
//
//        Point Pij = Pc;
//
//        if (!isZero(xj)) {
//            Pij = Pij.add(vRight.scale(xj));
//        }
//        if (!isZero(yi)) {
//            Pij = Pij.subtract(vUp.scale(yi)); // Pij.add(_vUp.scale(-yi))
//        }
//        return Pij;
//    }
//

    /**
     *
     * @param nX number of pixels widthwith
     * @param nY number of pixels widthwith
     * @param j number of wanted ray multiplicand
     * @param i number of wanted ray multiplicand

     * @return
     */
    public List<Ray> constructGridRaysThroughPixel(int nX, int nY, int j, int i) {


        //Image center
        Point Pc = p0.add(vTo.scale(distance));

        //Ratio (pixel width & height)
        double Ry =height/ nX;
        double Rx = width/nY;

        //delta values for going to Pixel[i,j] from Pc
        double gapY =  -(i - (nY -1)/2)* Ry;
        double gapX =  (j - (nX -1)/2)* Rx;

        if (! isZero(gapX) )
        {
            Pc = Pc.add(vRight.scale(gapX));
        }

        if (! isZero(gapY))
        {
            Pc = Pc.add(vUp.scale(gapY));
        }
        List<Ray> rays=new ArrayList<>();

        /**
         * puts the pixel center in the first place on the list.
         */
        rays.add(new Ray(p0,Pc.subtract(p0)));

        /**
         * creating Ry*Rx rays for each pixel.
         */
        Point newPoint=new Point(Pc.getX()-Rx/2,Pc.getY()+Rx/2,Pc.getZ());
        for (double t = newPoint.getY(); t >newPoint.getY()-Ry; t-=0.01)
        {
            for (double k = newPoint.getX(); k < newPoint.getX()+Rx; k+=0.01)
            {
                rays.add(new Ray(p0,new Point(k,t,Pc.getZ()).subtract(p0)));
            }
        }

        return rays;




//        double Rx = width / nX;//the length of pixel in X axis
//        double Ry = height / nY;//the length of pixel in Y axis
//
//        Point Pij = getPij(nX, nY, j, i);
//        Point tmp;
//        //-----SuperSampling-----
//        List<Ray> rays = new LinkedList<>();//the return list, construct Rays Through Pixels
//
//
//        double n = Math.floor(Math.sqrt(numRays));
//        int delta = (int) (n / 2d);
//
//        double gapX = Rx / n;
//        double gapY = Ry / n;
//
//
//        for (int row = -delta; row <= delta; row++) {
//            for (int col = -delta; col <= delta; col++) {
//                tmp = new Point(Pij);
//                if (!isZero(row)) {
//                    tmp = tmp.add(vRight.scale(row * gapX));
//                }
//                if (!isZero(col)) {
//                    tmp = tmp.add(vRight.scale(col * gapY));
//                }
//                rays.add(new Ray(p0, tmp.subtract(p0).normalize()));
//            }
//        }
//        return rays;
    }



    /**
     * Adds the given amount to the camera's position
     *
     * @return the current camera
     */
    public Camera move(Double3 amount) {
        p0 = p0.add(new Vector(amount));
        return this;
    }

    /**
     * Rotates the camera around the axes with the given angles
     *
     * @param x angles to rotate around the x axis
     * @param y angles to rotate around the y axis
     * @param z angles to rotate around the z axis
     * @return the current camera
     */
    public Camera rotate(double x, double y, double z) {
        vTo = vTo.rotateX(x).rotateY(y).rotateZ(z);
        vUp = vUp.rotateX(x).rotateY(y).rotateZ(z);
        vRight = vTo.crossProduct(vUp);

        return this;
    }

}
