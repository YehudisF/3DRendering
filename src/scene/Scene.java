package scene;

import geometries.Geometries;
import primitives.Color;
import lighting.*;

/**
 * class scene holds all scene elements class is a PDS and therefore there aer no getters and all fields are public
 */
public class Scene {
    public final String name;
    public final Color background;
    public final AmbientLight ambientLight;
    public final Geometries geometries;

    private Scene(SceneBuilder builder) {
        name = builder.name;
        background = builder.background;
        ambientLight = builder.ambientLight;
        geometries = builder.geometries;
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

    public Geometries getGeometries() {
        return geometries;
    }

    public static class SceneBuilder {
        private final String name;
        private Color background = Color.BLACK;
        private AmbientLight ambientLight = new AmbientLight();
        private Geometries geometries = new Geometries();

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
