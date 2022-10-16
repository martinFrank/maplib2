package com.github.martinfrank.maplib2.map;

@SuppressWarnings("rawtypes")
public interface MapPartFactory<F extends Field, E extends Edge, N extends Node> {
    F createField(Field<F, E,N> field);
    E createEdge(Edge<F,E, N> edge);

    N createNode(Node<F,E> node);

}
