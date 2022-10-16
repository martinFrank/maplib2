package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.geo.Polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node<F extends Field, E extends Edge> extends Point {

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
