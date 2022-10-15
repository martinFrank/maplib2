package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class BypassHiddenConstructorUtil {

    static Node createNodeViaReflection(double x, double y) {
        Class<Node> fieldClass = Node.class;
        try {
            Constructor<Node> constructor = fieldClass.getDeclaredConstructor(double.class, double.class);
            constructor.setAccessible(true);
            return constructor.newInstance(x,y);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <E extends Edge<N>, N extends Node> Field<E,N> createFieldViaReflection(Point position, List<N> nodes, List<E> edges, N center) {
        Class<Field> fieldClass = Field.class;
        try {
            Constructor<Field> constructor = fieldClass.getDeclaredConstructor(Point.class, List.class, List.class, Node.class);
            constructor.setAccessible(true);
            return constructor.newInstance(position, nodes, edges, center);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <N extends Node> Edge<N> createEdgeViaReflection(Node a, Node b) {
        Class<Edge> fieldClass = Edge.class;
        try {
            Constructor<Edge> constructor = fieldClass.getDeclaredConstructor(Node.class, Node.class);
            constructor.setAccessible(true);
            return constructor.newInstance(a, b);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
