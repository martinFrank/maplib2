package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.geo.Polygon;

import java.util.Collections;

public class Node extends Point {

    public final Polygon polygon;

    private Node(double x,double y) {
        super(x,y);
        polygon = new Polygon(Collections.singletonList(this));
    }

    @SuppressWarnings("CopyConstructorMissesField")//positiveFalse: it DOES copy all fields!
    public Node(Node node) {
        this(node.x, node.y);
    }

}
