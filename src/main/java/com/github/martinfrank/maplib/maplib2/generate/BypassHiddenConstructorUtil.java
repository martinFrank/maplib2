package com.github.martinfrank.maplib.maplib2.generate;

import com.github.martinfrank.maplib.maplib2.geo.DiscreetPoint;
import com.github.martinfrank.maplib.maplib2.map.Edge;
import com.github.martinfrank.maplib.maplib2.map.Field;
import com.github.martinfrank.maplib.maplib2.map.Fields;
import com.github.martinfrank.maplib.maplib2.map.Map;
import com.github.martinfrank.maplib.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib.maplib2.map.Node;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

class BypassHiddenConstructorUtil {

    private BypassHiddenConstructorUtil(){

    }

    static  Node createNodeViaReflection(double x, double y) {
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

    static Field createFieldViaReflection(DiscreetPoint position, List<Node> nodes, List<Edge> edges, Node center) {
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

    static Edge createEdgeViaReflection(Node a, Node b) {
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

    static Map createMapViaReflection(Fields fields){
        Class<Map> mapClass = Map.class;
        try {
            Constructor<Map> constructor = mapClass.getDeclaredConstructor(Fields.class);
            constructor.setAccessible(true);
            Map map = constructor.newInstance(fields);
            constructor.setAccessible(false);
            return map;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    static Node createIfNotExists(List<Node> pointPool, double x, double y, MapPartFactory factory) {
        Node node = BypassHiddenConstructorUtil.createNodeViaReflection(x,y);
        Node newNode = factory.createNode(node);
        Optional<Node> existing = pointPool.stream().filter(newNode::equals).findAny();
        if (existing.isPresent()) {
            return existing.get();
        } else {
            pointPool.add(newNode);
            return newNode;
        }
    }

    static Edge createIfNotExists(List<Edge> edgePool, Node a, Node b, MapPartFactory factory) {
        Edge newEdge = BypassHiddenConstructorUtil.createEdgeViaReflection(a, b);
        Edge edge = factory.createEdge(newEdge);
        Optional<Edge> existing = edgePool.stream().filter(edge::equals).findAny();
        if (existing.isPresent()) {
            return existing.get();
        } else {
            edgePool.add(edge);
            return edge;
        }
    }
}
