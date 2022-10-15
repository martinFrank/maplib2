package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib2.map.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DefaultMapPartFactory implements MapPartFactory<Field<Edge<Node>,Node>, Edge<Node>, Node> {

    @Override
    public Field<Edge<Node> ,Node> createField(Field<Edge<Node>,Node> field) {
        return field;
    }

    @Override
    public Edge<Node> createEdge(Edge<Node> edge) {
        return edge;
    }

    @Override
    public Node createNode(Node node) {
        return node;
    }


}
