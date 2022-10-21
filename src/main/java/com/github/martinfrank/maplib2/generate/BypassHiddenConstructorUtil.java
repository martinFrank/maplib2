package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.geo.DiscreetPoint;
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

    @SuppressWarnings({"unchecked", "rawtypes", "unused"})
    static <F extends Field, E extends Edge, N extends Node> Node<F,E> createNodeViaReflection(double x, double y) {
        Class<Node> fieldClass = Node.class;
        try {
            Constructor<Node> constructor = fieldClass.getDeclaredConstructor(double.class, double.class);
            constructor.setAccessible(true);
            Node node = constructor.newInstance(x,y);
            constructor.setAccessible(false);
            return node;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <F extends Field, E extends Edge, N extends Node> Field<F, E,N> createFieldViaReflection(DiscreetPoint position, List<N> nodes, List<E> edges, N center) {
        Class<Field> fieldClass = Field.class;
        try {
            Constructor<Field> constructor = fieldClass.getDeclaredConstructor(DiscreetPoint.class, List.class, List.class, Node.class);
            constructor.setAccessible(true);
            Field field = constructor.newInstance(position, nodes, edges, center);
            constructor.setAccessible(false);
            return field;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <F extends Field, E extends Edge, N extends Node> Edge<F,E, N> createEdgeViaReflection(Node a, Node b) {
        Class<Edge> edgeClass = Edge.class;
        double xc = (a.x + b.x) / 2d;
        double yc = (a.y + b.y) / 2d;
        Node center = createNodeViaReflection(xc, yc);
        try {
            Constructor<Edge> constructor = edgeClass.getDeclaredConstructor(Node.class, Node.class, Node.class);
            constructor.setAccessible(true);
            Edge edge = constructor.newInstance(a, b, center);
            constructor.setAccessible(false);
            return edge;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <F extends Field<F,E,N>, E extends Edge, N extends Node> Map<F,E,N> createMapViaReflection(Fields fields){
        Class<Map> mapClass = Map.class;
        try {
            Constructor<Map> constructor = mapClass.getDeclaredConstructor(Fields.class);
            constructor.setAccessible(true);
            Map<F,E,N> map = constructor.newInstance(fields);
            constructor.setAccessible(false);
            return map;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings("rawtypes")
    static <F extends Field, E extends Edge, N extends Node> N createIfNotExists(List<N> pointPool, double x, double y, MapPartFactory<F, E, N> factory) {
        Node<F,E> node = BypassHiddenConstructorUtil.createNodeViaReflection(x,y);
        N newNode = factory.createNode(node);
        Optional<N> existing = pointPool.stream().filter(newNode::equals).findAny();
        if (existing.isPresent()) {
            return existing.get();
        } else {
            pointPool.add(newNode);
            return newNode;
        }
    }

    @SuppressWarnings("rawtypes")
    static <F extends Field, E extends Edge, N extends Node> E createIfNotExists(List<E> edgePool, Node<F,E> a, Node<F,E> b, MapPartFactory<F, E, N> factory) {
        Edge<F,E, N> newEdge = BypassHiddenConstructorUtil.createEdgeViaReflection(a, b);
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
