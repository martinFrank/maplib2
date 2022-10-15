package com.github.martinfrank.maplib2.map;

public interface MapPartFactory<F extends Field<E,N>, E extends Edge<N>, N extends Node> {
    F createField(Field<E,N> field);
    E createEdge(Edge<N> edge);

    N createNode(Node node);
}
