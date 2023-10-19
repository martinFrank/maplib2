package com.github.martinfrank.maplib2.demoapp.generate;

import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Field;
import com.github.martinfrank.maplib2.demoapp.map.MapPartFactory;
import com.github.martinfrank.maplib2.demoapp.map.Node;

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
