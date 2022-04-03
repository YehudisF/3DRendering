package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    /**
     * setter for material according to builder design pattern
     * @param material
     * @return
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * getter for material
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    private Material material=new Material();

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * calculates and returns the normal vector from the shape
     * @param point {@link Point} external to the shape
     * @return normal vector {@link Vector}
     */

    public abstract Vector getNormal(Point point);
}
