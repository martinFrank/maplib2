package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Fields;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib2.map.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

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
        Class<Edge> edgeClass = Edge.class;
        try {
            Constructor<Edge> constructor = edgeClass.getDeclaredConstructor(Node.class, Node.class);
            constructor.setAccessible(true);
            return constructor.newInstance(a, b);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <F extends Field<E,N>, E extends Edge<N>, N extends Node> Map<F,E,N> createMapViaReflection(Fields fields){
        Class<Map> mapClass = Map.class;
        try {
            Constructor<Map> constructor = mapClass.getDeclaredConstructor(Fields.class);
            constructor.setAccessible(true);
            return constructor.newInstance(fields);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public static <N extends Node> N createIfNotExists(List<N> pointPool, double x, double y, MapPartFactory<?, ?, N> factory) {
        Node node = BypassHiddenConstructorUtil.createNodeViaReflection(x,y);
        N newNode = factory.createNode(node);
        Optional<N> existing = pointPool.stream().filter(newNode::equals).findAny();
        if (existing.isPresent()) {
            return existing.get();
        } else {
            pointPool.add(newNode);
            return newNode;
        }
    }

    public static <E extends Edge<N>, N extends Node> E createIfNotExists(List<E> edgePool, Node a, Node b, MapPartFactory<?, E, N> factory) {
        Edge<N> newEdge = BypassHiddenConstructorUtil.createEdgeViaReflection(a, b);
        E edge = factory.createEdge(newEdge);
        Optional<E> existing = edgePool.stream().filter(edge::equals).findAny();
        if (existing.isPresent()) {
            return existing.get();
        } else {
            edgePool.add(edge);
            return edge;
        }
    }
}
