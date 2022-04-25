package lighting;
import primitives.*;

/**
 * Ambient light for all the 3D objects
 */
public class AmbientLight extends Light{


    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * consturctor
     * @param Ia illumination color for light
     * @param Ka attenuation factor
     */
    public AmbientLight(Color Ia, Double3 Ka)
    {
      super(Ia.scale(Ka));
    }


}
