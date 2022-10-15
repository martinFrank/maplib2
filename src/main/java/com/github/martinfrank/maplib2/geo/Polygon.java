package com.github.martinfrank.maplib2.geo;

import com.github.martinfrank.maplib2.map.Node;

import java.util.List;

public class Polygon {

    public double width;
    public double height;

    public final double scale;


    public Polygon( double scale, List<? extends Node> nodes) {
        this.scale = scale;
    }
}
