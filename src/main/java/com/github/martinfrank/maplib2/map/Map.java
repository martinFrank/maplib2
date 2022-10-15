package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Polygon;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Map<F extends Field<E,N>, E extends Edge<N>, N extends Node> {
    private final Fields<F, E, N> fields;

    public Map(Fields<F, E, N> fields){
        this.fields = fields;
    }

    public Fields<F, E, N> getFields(){
        return fields;
    }

    public F getField(double x, double y) {
        return fields.getField(x,y);
    }

    public Polygon getPolygon(double scale) {
        List<Node> nodes = new ArrayList<>();
        Polygon polygon = new Polygon( scale, nodes);
        return polygon;
    }
}
