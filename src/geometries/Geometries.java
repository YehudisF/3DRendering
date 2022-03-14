package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable{
   protected List<Intersectable>_intersectablesList;

    public Geometries() {
        _intersectablesList = new LinkedList<>();
    }

    public Geometries(Intersectable... intersectables)
    {
        _intersectablesList = new LinkedList<>();
        Collections.addAll(_intersectablesList, intersectables);

    }

    public void add(Intersectable...intersectables){
        Collections.addAll(_intersectablesList,intersectables);
    }

//    public void remove(Intersectable... intersectables)
//    {
//        _intersectablesList.removeAll(List.of(intersectables))
//    }


    @Override
    public List<Point> findIntersections(Ray ray)
    {
        List<Point> result=null;
        for (var item: _intersectablesList) {
            List<Point> itemList=item.findIntersections(ray);
            if(itemList!=null)
            {
                if(result==null)
                {
                    result=new LinkedList<>();
                }
                result.addAll(itemList);
            }
//            if(result != null)
//                result.addAll(item.findIntersections(ray));

        }
            return result;
    }
}
