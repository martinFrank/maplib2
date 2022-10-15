package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;

public class Node extends Point {

    private Node(double x,double y) {
        super(x,y);
    }

    public Node(Node node) {
        this(node.x, node.y);
    }

}
