package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.geo.Polygon;

import java.util.Arrays;

public class Map<F extends Field, E extends Edge, N extends Node> {

    public final Fields<F, E, N> fields;

    public final Polygon polygon;

    private Map(Fields<F, E, N> fields){
        this.fields = fields;
        double w = fields.getWidth();
        double h = fields.getHeight();
        polygon = new Polygon(Arrays.asList(
                new Point(0,0),
                new Point(0,h),
                new Point(w,h),
                new Point(w,0)
        ));
    }

    public Map(Map<F,E,N> sample){
        this(sample.fields);
    }


    public F getFieldOnScreen(double x, double y, double scale, double catchRadius) {
        return fields.getFieldOnScreen( new Point(x,y),  scale, catchRadius);
    }

    public E getEdgeOnScreen(int x, int y, double scale, int catchRadius) {
        return fields.getEdgeOnScreen( new Point(x,y),  scale, catchRadius);
    }

    public N getNodeOnScreen(int x, int y, double scale, int catchRadius) {
        return fields.getNodeOnScreen( new Point(x,y),  scale, catchRadius);
    }
}
