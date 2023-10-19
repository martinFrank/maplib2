package com.github.martinfrank.maplib.maplib2.generate;

import com.github.martinfrank.maplib.maplib2.map.Edge;
import com.github.martinfrank.maplib.maplib2.map.Field;
import com.github.martinfrank.maplib.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib.maplib2.map.Node;

class DefaultMapPartFactory implements MapPartFactory  {

    public Field createField(Field field) {
        return field;
    }

    public Edge createEdge(Edge edge) {
        return edge;
    }

    public Node createNode(Node node) {
        return node;
    }


}
