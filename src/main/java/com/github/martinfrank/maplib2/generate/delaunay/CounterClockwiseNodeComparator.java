package com.github.martinfrank.maplib2.generate.delaunay;



import java.util.Comparator;
//https://en.wikipedia.org/wiki/Polar_coordinate_system#Converting_between_polar_and_Cartesian_coordinates
public class CounterClockwiseNodeComparator implements Comparator<DelaunayNode> {

    private final DelaunayNode center;

    public CounterClockwiseNodeComparator(DelaunayNode a, DelaunayNode b, DelaunayNode c) {
        this(new DelaunayNode((a.getX() + b.getX() + c.getX()) / 3d, (a.getY() + b.getY() + c.getY()) / 3d));
    }

    public CounterClockwiseNodeComparator(DelaunayNode center) {
        this.center = center;
    }

    public CounterClockwiseNodeComparator() {
        this(new DelaunayNode(0, 0));
    }

    @Override
    public int compare(DelaunayNode o1, DelaunayNode o2) {
        double theta1 = Math.atan2(center.getY() - o1.getY(), center.getX() - o1.getX());
        double theta2 = Math.atan2(center.getY() - o2.getY(), center.getX() - o2.getX());
        return Double.compare(theta1, theta2);
    }
}
