package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.geo.Polygon;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Field<E extends Edge, N extends Node>{

    public final Point position;
    public final List<N> nodes;
    public final List<E> edges;
    public final N center;
    public Polygon polygon;


    private Field(Point position, List<N> nodes, List<E> edges, N center) {
        this.position = position;
        this.nodes = Collections.unmodifiableList(nodes);
        this.edges = Collections.unmodifiableList(edges);
        this.center = center;
        this.polygon = new Polygon(nodes);
    }

    @SuppressWarnings("CopyConstructorMissesField")//positiveFalse: it DOES copy all fields!
    public Field(Field<E, N> template) {
        this(template.position, template.nodes, template.edges, template.center);
    }


}
