package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib2.map.Node;

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
