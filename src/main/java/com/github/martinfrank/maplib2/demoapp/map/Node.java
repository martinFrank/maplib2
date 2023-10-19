package com.github.martinfrank.maplib2.demoapp.map;

import com.github.martinfrank.maplib2.demoapp.geo.FloatingPoint;
import com.github.martinfrank.maplib2.demoapp.geo.Polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Node<F extends Field, E extends Edge> extends FloatingPoint {

    public final Polygon polygon;

    public final List<F> fields = new ArrayList<>();
    public final List<E> edges = new ArrayList<>();

    private Node(double x,double y) {
        super(x,y);
        polygon = new Polygon(Collections.singletonList(this));
    }

    @SuppressWarnings("CopyConstructorMissesField")//positiveFalse: it DOES copy all fields!
    public Node(Node<F,E> node) {
        this(node.x, node.y);
    }

}
