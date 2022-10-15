package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Fields;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib2.map.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SquareMapGenerator<F extends Field<E,N>, E extends Edge<N>, N extends Node> {

    public Map<F, E, N> generate(MapGenerationParameter parameter, MapPartFactory<F, E, N> factory) {
        List<F> fields = new ArrayList<>();
        List<E> edgePool = new ArrayList<>();
        List<N> pointPool = new ArrayList<>();
        for (Point position : mapSizeToPoints(parameter)) {
            fields.add(createSquareField(position, edgePool, pointPool, factory));
        }
        Map<F, E, N> map = new Map<>(new Fields<>(fields));
        return map;
    }

    private F createSquareField(Point position, List<E> edgePool, List<N> pointPool, MapPartFactory<F, E, N> factory) {

        N center = factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection((2 * position.x) + 1, (2 * position.y) + 1));

        N a = createIfNotExists(pointPool, center.x - 1, center.y - 1, factory);
        N b = createIfNotExists(pointPool, center.x + 1, center.y - 1, factory);
        N c = createIfNotExists(pointPool, center.x + 1, center.y + 1, factory);
        N d = createIfNotExists(pointPool, center.x - 1, center.y + 1, factory);
        List<N> nodes = Arrays.asList(a, b, c, d);


        E ab = createIfNotExists(edgePool, a, b, factory);
        E bc = createIfNotExists(edgePool, b, c, factory);
        E cd = createIfNotExists(edgePool, c, d, factory);
        E da = createIfNotExists(edgePool, d, a, factory) ;
        List<E> edges = Arrays.asList(ab, bc, cd, da);

        Field<E, N> field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }

    private N createIfNotExists(List<N> pointPool, double x, double y, MapPartFactory<F, E, N> factory) {
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

    private E createIfNotExists(List<E> edgePool, Node a, Node b, MapPartFactory<F, E, N> factory) {
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


    public List<Point> mapSizeToPoints(MapGenerationParameter parameter) {
        List<Point> points = new ArrayList<>();
        for (int dy = 0; dy < parameter.height; dy++) {
            for (int dx = 0; dx < parameter.width; dx++) {
                points.add(new Point(dx, dy));
            }
        }
        return points;
    }

}
