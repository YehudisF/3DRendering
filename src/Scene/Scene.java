package Scene;

import geometries.Geometries;
import primitives.Color;

public class Scene {
    private  final  String name;
    private final Color background;
    private  final AmbienLight _ambienLight;
    private final Geometries _geometries;

    private Scene(SceneBuilder builder){
        name = builder.name;
        background = builder.background;
        _ambienLight = builder.ambientLight;
        _geometries = builder.geometries;
    }

}
