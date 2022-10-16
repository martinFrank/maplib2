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
import java.util.stream.Collectors;

public class SquareMapGenerator<F extends Field, E extends Edge, N extends Node> {

    public Map<F, E, N> generate(MapGenerationParameter parameter, MapPartFactory<F, E, N> factory) {
        List<F> fields = new ArrayList<>();
        List<E> edgePool = new ArrayList<>();
        List<N> nodes = new ArrayList<>();
        for (Point position : MapGeneratorUtils.mapSizeToPoints(parameter)) {
            fields.add(createSquareField(position, edgePool, nodes, factory));
        }
        setFieldsToNodes(fields, nodes);
        return BypassHiddenConstructorUtil.createMapViaReflection(new Fields<>(fields));
    }

    private void setFieldsToNodes(List<F> fields, List<N> nodes) {
        for (N node: nodes){
            List<F> nbgs = fields.stream().filter(f -> f.nodes.contains(node)).collect(Collectors.toList());
            BypassFinalFieldsUtil.setNodeFields(node, nbgs);
        }
    }

    private F createSquareField(Point position, List<E> edgePool, List<N> pointPool, MapPartFactory<F, E, N> factory) {

        N center = factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection((2 * position.x) + 1, (2 * position.y) + 1));

        N a = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 1, center.y - 1, factory);
        N b = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 1, center.y - 1, factory);
        N c = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 1, center.y + 1, factory);
        N d = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 1, center.y + 1, factory);
        List<N> nodes = Arrays.asList(a, b, c, d);


        E ab = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, a, b, factory);
        E bc = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, b, c, factory);
        E cd = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, c, d, factory);
        E da = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, d, a, factory);
        List<E> edges = Arrays.asList(ab, bc, cd, da);

        Field<E, N> field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }

}
