package com.github.martinfrank.maplib.maplib2.map;

import com.github.martinfrank.maplib.maplib2.geo.FloatingPoint;
import com.github.martinfrank.maplib.maplib2.geo.Polygon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Node extends FloatingPoint {

    public final Polygon polygon;

    public final List<Field> fields = new ArrayList<>();
    public final List<Edge> edges = new ArrayList<>();

    private Node(double x,double y) {
        super(x,y);
        polygon = new Polygon(Collections.singletonList(this));
    }

    public Node(Node node) {
        this(node.x, node.y);
    }

}
