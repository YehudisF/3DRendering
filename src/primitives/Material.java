package primitives;

/**
 * PDS class
 * no funcitons only public fields
 */
public class Material
{
    // all the fieds have a default value of 0, there is a default constructor
    public Double3 kD=new Double3(0);
    public Double3 kS=new Double3(0);
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
     * @param kS
     * @return
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
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
