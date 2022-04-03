package renderer;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {


    @Test
    void testWriteToImage() {
        int nX = 800;
        int nY = 500;

        ImageWriter imageWriter = new ImageWriter("yellowred", nX, nY);
        Color yellowColor = new Color(255d, 255d, 0d);
        Color redColor = new Color(255,0d, 0d);

        for( int j = 0; j<nX; j++)
        {
            //grid 16 X 10
            //makesa yellow image with a red grid
            for(int i = 0; i< nY; i++)
            {
                if(j % 50 ==0 || i% 50 ==0){
                    imageWriter.writePixel(j, i, redColor);
                }
                else{
                    imageWriter.writePixel(j, i, yellowColor);
                }
            }
        }
        imageWriter.writeToImage();
    }
}