package com.github.martinfrank.maplib2.generate.delaunay;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DelaunayEdge {

    private final DelaunayNode a;
    private final DelaunayNode b;
    private final double length;

    public DelaunayEdge(DelaunayNode a, DelaunayNode b) {
        this.a = a;
        this.b = b;
        length = Math.sqrt(Math.pow((b.getX() - a.getX()), 2) + Math.pow((b.getY() - a.getY()), 2));
    }


    public DelaunayNode getA() {
        return a;
    }

    public DelaunayNode getB() {
        return b;
    }

    public double getLength() {
        return length;
    }

    @Override
    public String toString() {
        return a + " -> " + b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DelaunayEdge that = (DelaunayEdge) o;
        return Objects.equals(a, that.a) && Objects.equals(b, that.b) ||
                Objects.equals(a, that.b) && Objects.equals(b, that.a);
    }

    @Override
    public int hashCode() {
        List<DelaunayNode> nodes = Arrays.asList(a, b);
        nodes.sort(new CounterClockwiseNodeComparator(a, b, new DelaunayNode(0, 0)));
        return Objects.hash(nodes.get(0), nodes.get(1));
    }
}
