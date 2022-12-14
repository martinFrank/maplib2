package com.github.martinfrank.maplib2.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Polygon {

    public double width;
    public double height;

    private final List<FloatingPoint> originalPoints;

    public Polygon(List<? extends FloatingPoint> nodes) {
        double minx = nodes.stream().mapToDouble(n-> n.x).min().orElse(0);
        double miny = nodes.stream().mapToDouble(n-> n.y).min().orElse(0);
        double maxx = nodes.stream().mapToDouble(n-> n.x).max().orElse(0);
        double maxy = nodes.stream().mapToDouble(n-> n.y).max().orElse(0);
        width = maxx - minx;
        height = maxy- miny;
        this.originalPoints = new ArrayList<>(nodes);
    }


    public List<FloatingPoint> getScaled(double scale) {
        return originalPoints.stream().map(p -> new FloatingPoint(p.x*scale, p.y*scale)).collect(Collectors.toList());
    }

    public double getScaledWidth(double scale){
        return width * scale;
    }

    public double getScaledHeight(double scale){
        return height * scale;
    }
}
