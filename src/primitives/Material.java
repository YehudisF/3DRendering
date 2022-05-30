package primitives;

/**
 * PDS class
 * no funcitons only public fields
 */
public class Material {
    // all the fieds have a default value of 0, there is a default constructor
    public Double3 kD = Double3.ZERO;// diffuse
    public Double3 kS = Double3.ZERO;// specular
    public Double3 kT = Double3.ZERO; // transparancy attenuation factor
    public Double3 kR = Double3.ZERO; // reflection attenuation factor
    public double kG  = 1.0;// glossy
    public int nShininess = 0;

    /**
     * setter according to the builder pattern
     *
     * @param kD
     * @return
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kd
     * @return
     */
    public Material setKd(Double kd) {
        kD = new Double3(kd);
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kt
     * @return
     */
    public Material setKt(Double3 kt) {
        kT = kt;
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kt
     * @return
     */
    public Material setKt(Double kt) {
        kT = new Double3(kt);
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kr
     * @return
     */
    public Material setKr(Double3 kr) {
        kR = kr;
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kr
     * @return
     */
    public Material setKr(Double kr) {
        kR = new Double3(kr);
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param kS
     * @return
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param ks
     * @return
     */
    public Material setKs(Double ks) {
        kS = new Double3(ks);
        return this;
    }

    /**
     * setter according to the builder pattern
     *
     * @param nShininess
     * @return
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Chaining method for setting the glossiness of the material.
     * @param kG the glossiness to set, value in range [0,1]
     * @return the current material
     */
    public Material setkG(double kG) {
        this.kG = Math.pow(kG, 0.5);
        return this;
    }
}
