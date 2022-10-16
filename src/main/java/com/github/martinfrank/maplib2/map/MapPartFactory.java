package com.github.martinfrank.maplib2.map;

public interface MapPartFactory<F extends Field, E extends Edge, N extends Node> {
    F createField(Field<E,N> field);
    E createEdge(Edge<F,N> edge);

    N createNode(Node<F,E> node);

}
