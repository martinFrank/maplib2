package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.FloatingPoint;
import com.github.martinfrank.maplib2.geo.Polygon;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("rawtypes")
public class Map<F extends Field, E extends Edge, N extends Node> {

    private final Fields<F, E, N> fields;

    public final Polygon polygon;

    private Map(Fields<F, E, N> fields){
        this.fields = fields;
        double w = fields.getWidth();
        double h = fields.getHeight();
        polygon = new Polygon(Arrays.asList(
                new FloatingPoint(0,0),
                new FloatingPoint(0,h),
                new FloatingPoint(w,h),
                new FloatingPoint(w,0)
        ));
    }

    public Map(Map<F,E,N> sample){
        this(sample.fields);
    }


    public F getFieldOnScreen(double x, double y, double scale, double catchRadius) {
        return fields.getFieldOnScreen( new FloatingPoint(x,y),  scale, catchRadius);
    }

    public E getEdgeOnScreen(int x, int y, double scale, int catchRadius) {
        return fields.getEdgeOnScreen( new FloatingPoint(x,y),  scale, catchRadius);
    }

    public N getNodeOnScreen(int x, int y, double scale, int catchRadius) {
        return fields.getNodeOnScreen( new FloatingPoint(x,y),  scale, catchRadius);
    }

    public List<F> getBorders() {
        return fields.getBorders();
    }

    public F getRandomFieldWithinBorders() {
        return fields.getRandomFieldWithinBorders();
    }

    public Stream<F> fields() {
        return fields.stream();
    }

    public F getField(int x, int y) {
        return fields.getField(x,y);
    }

    public List<F> getFields() {
        return fields.getAll();
    }
}
