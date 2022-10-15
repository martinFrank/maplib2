package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;

import java.util.Objects;

public class Edge<N extends Node> {

    public final N a;
    public final N b;

    private Edge(N a, N b) {
        this.a = a;
        this.b = b;
    }

    public Edge(Edge<N> edge) {
        this(edge.a, edge.b);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge<?> edge = (Edge<?>) o;
        boolean ab = Objects.equals(a, edge.a) && Objects.equals(b, edge.b);
        boolean ba = Objects.equals(a, edge.b) && Objects.equals(b, edge.a);
        return ab || ba;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
