package primitives;

/**
 * PDS class
 * no funcitons only public fields
 */
public class Material
{
    // all the fieds have a default value of 0, there is a default constructor
    public Double3 kD=Double3.ZERO;
    public Double3 kS= Double3.ZERO;
    public Double3 kT = Double3.ZERO; // transparancy attenuation factor
    public Double3 kR = Double3.ZERO; // reflection attenuation factor
    public int nShininess=0;

    /**
     * setter according to the builder pattern
     * @param kD
     * @return
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * setter according to the builder pattern
     * @param kd
     * @return
     */
    public Material setkD(Double kd) {
        kD = new Double3(kd);
        return this;
    }

    /**
     * setter according to the builder pattern
     * @param kt
     * @return
     */
    public Material setkT(Double3 kt) {
        kT = kt;
        return this;
    }
    /**
     * setter according to the builder pattern
     * @param kt
     * @return
     */
    public Material setkT(Double kt) {
        kT = new Double3(kt);
        return this;
    }
    /**
     * setter according to the builder pattern
     * @param kr
     * @return
     */
    public Material setkR(Double3 kr) {
        kR = kr;
        return this;
    }
    /**
     * setter according to the builder pattern
     * @param kr
     * @return
     */
    public Material setkR(Double kr) {
        kR = new Double3(kr);
        return this;
    }

    /**
     * setter according to the builder pattern
     * @param kS
     * @return
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * setter according to the builder pattern
     * @param ks
     * @return
     */
    public Material setkS(Double ks) {
        kS = new Double3(ks);
        return this;
    }

    /**
     * setter according to the builder pattern
     * @param nShininess
     * @return
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
