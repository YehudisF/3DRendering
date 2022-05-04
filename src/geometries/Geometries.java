package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {
    protected List<Intersectable> _intersectablesList;

    public Geometries() {
        _intersectablesList = new LinkedList<>();
    }

    public Geometries(Intersectable... intersectables) {
        _intersectablesList = new LinkedList<>();
        Collections.addAll(_intersectablesList, intersectables);

    }

    public void add(Intersectable... intersectables) {
        Collections.addAll(_intersectablesList, intersectables);
    }

//    public void remove(Intersectable... intersectables)
//    {
//        _intersectablesList.removeAll(List.of(intersectables))
//    }



    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : _intersectablesList) {
            var geoIntersections = geometry.findGeoIntersections(ray);
            if (geoIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geoIntersections);
            }
        }
        return intersections;
    }


}
