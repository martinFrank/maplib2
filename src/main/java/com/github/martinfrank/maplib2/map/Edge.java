package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Polygon;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public class Edge<F extends Field, THIS, N extends Node> {

    public final N nodeA;
    public final N nodeB;

    public final F fieldA = null;
    public final F fieldB = null;

    public final List<THIS> edges = null;

    private boolean isPassable;

    public final Polygon polygon;

    private Edge(N nodeA, N nodeB) {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
        polygon = new Polygon(Collections.unmodifiableList(Arrays.asList(nodeA, nodeB)));
    }

    @SuppressWarnings("CopyConstructorMissesField")//positiveFalse: it DOES copy all fields!
    public Edge(Edge<F,THIS, N> edge) {
        this(edge.nodeA, edge.nodeB);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<F,THIS, N> edge = (Edge<F,THIS, N>) o;
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
