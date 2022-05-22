package scene;

import geometries.Geometries;
import primitives.Color;
import lighting.*;

import java.util.LinkedList;
import java.util.List;

/**
 * class scene holds all scene elements class is a PDS and therefore there aer no getters and all fields are public
 */
public class Scene {
    private final String name;
    private final Color background;
    private AmbientLight ambientLight;
    private final Geometries geometries;
    private final List<LightSource> lights;

    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    public List<LightSource> getLights() {
        return lights;
    }



    private Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
        lights =  builder.lights;
    }

    public String getName() {
        return name;
    }

    public Color getBackground() {
        return background;
    }

    public AmbientLight getAmbienLight() {
        return ambientLight;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight=ambientLight;
        return this;
    }

    public Geometries getGeometries() {
        return geometries;
    }

    public static class SceneBuilder {
        private final String name;
        public List<LightSource> lights = new LinkedList<>();
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();

        public SceneBuilder setLights(List<LightSource> lights) {
            this.lights = lights;
            return this;
        }

        public SceneBuilder(String name) {
            this.name = name;
        }

        ////chaining methods
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        public Scene build() {
            Scene scene = new Scene(this);
            ///
            return scene;
        }
    }
}
