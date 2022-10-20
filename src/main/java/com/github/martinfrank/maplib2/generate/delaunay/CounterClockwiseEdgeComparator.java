package com.github.martinfrank.maplib2.generate.delaunay;



import java.util.Comparator;

//https://en.wikipedia.org/wiki/Polar_coordinate_system#Converting_between_polar_and_Cartesian_coordinates
public class CounterClockwiseEdgeComparator implements Comparator<DelaunayEdge> {

    private final DelaunayNode center;
    private final CounterClockwiseNodeComparator nodeComparator;

    public CounterClockwiseEdgeComparator(DelaunayNode center) {
        this.center = center;
        this.nodeComparator = new CounterClockwiseNodeComparator(center);
    }

    public CounterClockwiseEdgeComparator() {
        this(new DelaunayNode(0, 0));
    }

    @Override
    public int compare(DelaunayEdge e1, DelaunayEdge e2) {
        DelaunayNode distant1 = e1.getA().equals(center) ? e1.getB() : e1.getA();
        DelaunayNode distant2 = e2.getA().equals(center) ? e2.getB() : e2.getA();
        return nodeComparator.compare(distant1, distant2);
    }
}
