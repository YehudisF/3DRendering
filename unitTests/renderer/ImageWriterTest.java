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

        for( int j = 0; j<nY; j++)
        {
            for(int i = 0; i< nX; i++)
                imageWriter.writePixel(j,i,yellowColor);
        }
    }
}