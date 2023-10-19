package com.github.martinfrank.maplib.maplib2.map;

import com.github.martinfrank.maplib.maplib2.geo.Polygon;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public class Edge {

    public final Node nodeA;
    public final Node nodeB;

    public final Node center;

    public final Field fieldA = null;
    public final Field fieldB = null;

    public final List<Edge> edges = null;

    private boolean isPassable;

    public final Polygon polygon;

    private Edge(Node nodeA, Node nodeB, Node center) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.center = center;
        polygon = new Polygon(Collections.unmodifiableList(Arrays.asList(nodeA, nodeB)));
    }

    @SuppressWarnings("CopyConstructorMissesField")//positiveFalse: it DOES copy all fields!
    public Edge(Edge edge) {
        this(edge.nodeA, edge.nodeB, edge.center);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        boolean ab = Objects.equals(nodeA, edge.nodeA) && Objects.equals(nodeB, edge.nodeB);
        boolean ba = Objects.equals(nodeA, edge.nodeB) && Objects.equals(nodeB, edge.nodeA);
        return ab || ba;
    }

    public void setPassable(boolean isPassable) {
        this.isPassable = isPassable;
    }

    public boolean isPassable(){
        return isPassable;
    }
    @Override
    public int hashCode() {
        return Objects.hash(nodeA, nodeB);
    }
}
