package com.github.martinfrank.maplib2.demoapp.map;

@SuppressWarnings("rawtypes")
public interface MapPartFactory {
    Field createField(Field field);
    Edge createEdge(Edge edge);
    Node createNode(Node node);

}
