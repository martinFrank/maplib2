package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.geo.Polygon;

import java.util.Collections;
import java.util.List;

public class Field<E extends Edge<N>, N extends Node> {

    public final Point position;
    public final List<N> nodes; //fixme make it unmodifiable
    public final List<E> edges;
    public final N center;


    private Field(Point position, List<N> nodes, List<E> edges, N center) {
        this.position = position;
        this.nodes = nodes;
        this.edges = edges;
        this.center = center;
    }

    @SuppressWarnings("CopyConstructorMissesField")//positiveFalse: it DOES copy all fields!
    public Field(Field<E, N> template) {
        this(template.position, Collections.unmodifiableList(template.nodes), Collections.unmodifiableList(template.edges), template.center);
    }

    public Polygon getPolygon(double scale) {
        Polygon polygon = new Polygon(scale, nodes);
        return polygon;
    }

}
