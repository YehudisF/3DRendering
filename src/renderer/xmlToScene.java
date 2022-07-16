//package renderer;
//
//import org.w3c.dom.*;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.*;
//import java.io.*;
//
//import geometries.*;
//import lighting.AmbientLight;
//import primitives.*;
//import renderer.*;
//import scene.*;
//
//public class xmlToScene {
//    String fileFullPath;
//    Element root;
//
//    public xmlToScene(String name) {
//        fileFullPath = name;
//        root = readFromFile();
//    }
//
//    public AmbientLight readAmbientLight() {
//        Node firstSon = root.getElementsByTagName("ambient-light").item(0);
//        return new AmbientLight(new Color(tagToDouble3("ambient-light", firstSon)), new Double3())
//    }
//
//    /**
//     * @param root
//     * @param name
//     * @return the wanted Double3 converted from text format as read
//     */
//    Double3 tagToDouble3(Element root, String name) {
//        String color = root.getAttribute(name);
//        String[] doubeArr = color.split(" ");
//        return new Double3(Double.parseDouble(doubeArr[0]), Double.parseDouble(doubeArr[1]), Double.parseDouble(doubeArr[2]));
//    }
//
//    /**
//     * @param name
//     * @param root
//     * @return a Double3 describing the wanted colour
//     */
//    Double3 tagToDouble3(String name, Node root) {
//        String colour = root.getAttributes().getNamedItem(name).getTextContent();
//        String[] colourarr = colour.split(" ");
//        return new Double3(Double.parseDouble(colourarr[0]), Double.parseDouble(colourarr[1]), Double.parseDouble(colourarr[2]));
//    }
//
//    /**
//     * reads from the xml file - deserializes into a root node with all the following nodes from all sub tags
//     *
//     * @return
//     */
//    Element readFromFile() {
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        try {
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            // final String FOLDER_PATH = System.getProperty("user.dir") + "/xmlDB";
//            // File file = new File(FOLDER_PATH + '/' + fullFilepath + ".xml");
//            File file = new File(fileFullPath);
//            try {
//                Document document = builder.parse(file);
//                document.getDocumentElement().normalize();
//                return document.getDocumentElement();
//            } catch (IOException e) {
//                throw new IllegalStateException("I/O error - may be missing directory " + fileFullPath, e);
//            } catch (SAXException e) {
//                throw new IllegalStateException("I/O error - may be missing directory " + fileFullPath, e);
//            }
//        } catch (ParserConfigurationException e) {
//            System.out.println("Huh??");
//        }
//        return null;
//
//    }
//
//}
//
//
//}


//package renderer;
//
//import org.w3c.dom.*;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.*;
//import java.io.*;
//
//import geometries.*;
//import lighting.AmbientLight;
//import primitives.*;
//import renderer.*;
//import scene.*;
//
//
//public   class   xmlToScene  {
//    String   fullFilepath  ;
//    Element   root  ;
//
//    public   Color   getBG  (){
//        return   new   Color ( xmlAtDouble3 ( "background-color" , root ));
//    }
//
//    public   AmbientLight   getAmbient (){
//        Node   sonAB  =  root . getElementsByTagName ( "ambient-light" ). item ( 0 );
//        return   new   AmbientLight ( new   Color ( xmlAtDouble3 ( "color" ,  sonAB )), xmlAtDouble3 ( "dx" , sonAB ) );
//    }
//
//    public   Geometries   getGeometries (){
//
//        Geometries   g  =  new   Geometries ();
//        Node   geo  =  root . getElementsByTagName ( "geometries" ). item ( 0 );
//        for ( int   i  = 0   ;  i  <   geo . getChildNodes (). getLength () ;  i ++ ){
//            Node   x  =  geo . getChildNodes (). item ( i );
//            if ( x . getNodeName () ==  "triangle" ){
//                g . add ( new   Triangle ( new   Point ( xmlAtDouble3 ( "p0" , x )),  new   Point ( xmlAtDouble3 ( "p1" , x )) ,  new   Point ( xmlAtDouble3 ( "p2" , x ))));
//            }
//            if ( x . getNodeName () ==  "sphere" ){
//                g . add ( new   Sphere (  new   Point ( xmlAtDouble3 ( "center" , x )) ,  Double . parseDouble ( x . getAttributes (). getNamedItem ( "radius" ). getTextContent ())));
//            }
//            if ( x . getNodeName () ==  "geometries" ){
//                g . add ( getGeometries ( x ));
//            }
//        }
//        return   g  ;
//    }
//
//    public   Geometries   getGeometries ( Node   geo ){
//        Geometries   g  =  new   Geometries ();
//        for ( int   i  = 0   ;  i  <   geo . getChildNodes (). getLength () ;  i ++ ){
//            Node   x  =  geo . getChildNodes (). item ( i );
//            if ( x . getNodeName () ==  "triangle" ){
//                g . add ( new   Triangle ( new   Point ( xmlAtDouble3 ( "p0" , x )),  new   Point ( xmlAtDouble3 ( "p1" , x )) ,  new   Point ( xmlAtDouble3 ( "p2" , x ))));
//            }
//            if ( x . getNodeName () ==  "sphere" ){
//                g . add ( new   Sphere (  new   Point ( xmlAtDouble3 ( "center" , x )) ,  Double . parseDouble ( x . getAttributes (). getNamedItem ( "radius" ). getTextContent ())));
//            }
//            if ( x . getNodeName () ==  "geometries" ){
//                g . add ();
//            }
//        }
//        return   g  ;
//    }
//
//    public   xmlToScene ( String   name ){
//        this . fullFilepath   =  name  ;
//        root  = getRoot ();
//    }
//
//    Element   getRoot (){
//
//        DocumentBuilderFactory   factory  =  DocumentBuilderFactory . newInstance ();
//        try  {
//            DocumentBuilder   builder  =  factory . newDocumentBuilder ();
//           // final   String   FOLDER_PATH  =  System . getProperty ( "user.dir" ) +  "/xmlDB" ;
//           // File   file  =  new   File ( FOLDER_PATH  +  '/'  +  fullFilepath  +  ".xml" );
//           File file = new File(fullFilepath);
//            try {
//                Document   document  =  builder . parse ( file );
//                document . getDocumentElement (). normalize ();
//                return   document . getDocumentElement ();
//            }
//            catch ( IOException   e ){
//                throw   new   IllegalStateException ( "I/O error - may be missing directory "  +  fullFilepath ,  e );
//            }
//            catch ( SAXException   e ){
//                throw   new   IllegalStateException ( "I/O error - may be missing directory "  + fullFilepath,  e );
//            }
//        }
//        catch  ( ParserConfigurationException   e ) {
//            System . out . println ( "I dont realy care" );
//        }
//        return   null  ;
//
//    }
//
//    Double3   xmlAtDouble3 ( String   name , Element   root ){
//        String   color   =  root . getAttribute ( name );
//        String   []  colorarr =  color . split ( " " );
//        return    new   Double3 ( Double . parseDouble ( colorarr [ 0 ]), Double . parseDouble ( colorarr [ 1 ]), Double . parseDouble ( colorarr [ 2 ]));
//    }
//
//    Double3   xmlAtDouble3 ( String   name , Node   root ){
//        String   color   =  root . getAttributes (). getNamedItem ( name ). getTextContent ();
//        String   []  colorarr =  color . split ( " " );
//        return    new   Double3 ( Double . parseDouble ( colorarr [ 0 ]), Double . parseDouble ( colorarr [ 1 ]), Double . parseDouble ( colorarr [ 2 ]));
//    }
//
//
//
//
//
//
//}


package renderer;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;

import geometries.*;
import lighting.AmbientLight;
import primitives.*;
import renderer.*;
import scene.*;

public class xmlToScene {
    String fileName;
    Element root;

    public Color getBG()
    {
        return new Color(xmlAtDouble3("background-color", root));
    }

    public xmlToScene(String name)
    {
        this.fileName = name;
        root = getRoot();
    }

    public AmbientLight getAmbient() {
        Node sonAB = root.getElementsByTagName("ambient-light").item(0);
        AmbientLight temp = new AmbientLight(new Color(xmlAtDouble3("color", sonAB)), xmlAtDouble3("dx", sonAB));
        return temp;
    }

    public Geometries getGeometries() {

        Geometries g = new Geometries();
        Node geo = root.getElementsByTagName("geometries").item(0);
        for (int i = 0; i < geo.getChildNodes().getLength(); i++) {
            Node x = geo.getChildNodes().item(i);
            if (x.getNodeName() == "triangle") {
                g.add(new Triangle(
                        new Point(xmlAtDouble3("p0", x)),
                        new Point(xmlAtDouble3("p1", x)),
                        new Point(xmlAtDouble3("p2", x))));
            }
            if (x.getNodeName() == "sphere") {
                g.add(new Sphere(new Point(xmlAtDouble3("center", x)), Double.parseDouble(x.getAttributes().getNamedItem("radius").getTextContent())));
            }
            if (x.getNodeName() == "geometries") {
                g.add(getGeometries(x));
            }
        }
        return g;
    }

    public Geometries getGeometries(Node geo) {
        Geometries g = new Geometries();
        for (int i = 0; i < geo.getChildNodes().getLength(); i++) {
            Node x = geo.getChildNodes().item(i);
            if (x.getNodeName() == "triangle") {
                g.add(new Triangle(new Point(xmlAtDouble3("p0", x)), new Point(xmlAtDouble3("p1", x)), new Point(xmlAtDouble3("p2", x))));
            }
            if (x.getNodeName() == "sphere") {
                g.add(new Sphere(new Point(xmlAtDouble3("center", x)), Double.parseDouble(x.getAttributes().getNamedItem("radius").getTextContent())));
            }
            if (x.getNodeName() == "geometries") {
                g.add();
            }
        }
        return g;
    }



    Element getRoot() {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            // final   String   FOLDER_PATH  =  System . getProperty ( "user.dir" ) +  "\\xmlDB" ;
            final String FOLDER_PATH = "C:\\Users\\Hudis\\Documents\\MiniProj\\ISE5782_6996_9033\\xmlDB";
            File file = new File(FOLDER_PATH + "\\" + fileName + ".xml");
            try {
                Document document = builder.parse(file);
                document.getDocumentElement().normalize();
                return document.getDocumentElement();
            } catch (IOException e) {
                throw new IllegalStateException("I/O error - may be missing directory " + FOLDER_PATH, e);
            } catch (SAXException e) {
                throw new IllegalStateException("I/O error - may be missing directory " + FOLDER_PATH, e);
            }
        } catch (ParserConfigurationException e) {
            System.out.println("I dont realy care");
        }
        return null;

    }

    Double3 xmlAtDouble3(String name, Element root) {
        String color = root.getAttribute(name);
        String[] colorarr = color.split(" ");
        return new Double3(Double.parseDouble(colorarr[0]), Double.parseDouble(colorarr[1]), Double.parseDouble(colorarr[2]));
    }

    Double3 xmlAtDouble3(String name, Node root) {
        String color = root.getAttributes().getNamedItem(name).getTextContent();
        String[] colorarr = color.split(" ");
        return new Double3(Double.parseDouble(colorarr[0]), Double.parseDouble(colorarr[1]), Double.parseDouble(colorarr[2]));
    }

}