package com.github.martinfrank.maplib2.demoapp.generate;

import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Field;
import com.github.martinfrank.maplib2.demoapp.map.MapPartFactory;
import com.github.martinfrank.maplib2.demoapp.map.Node;

@SuppressWarnings("rawtypes")
class DefaultMapPartFactory implements MapPartFactory<Field, Edge, Node> {

    @Override
    public Field createField(Field field) {
        return field;
    }

    @Override
    public Edge createEdge(Edge edge) {
        return edge;
    }

    @Override
    public Node createNode(Node node) {
        return node;
    }


}
