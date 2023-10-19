package com.github.martinfrank.maplib2.demoapp.generate;

import com.github.martinfrank.maplib2.demoapp.geo.DiscreetPoint;
import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Field;
import com.github.martinfrank.maplib2.demoapp.map.Fields;
import com.github.martinfrank.maplib2.demoapp.map.Map;
import com.github.martinfrank.maplib2.demoapp.map.MapPartFactory;
import com.github.martinfrank.maplib2.demoapp.map.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public class SquareMapGenerator<F extends Field, E extends Edge, N extends Node> {

    @SuppressWarnings("unchecked")
    public Map<F, E, N> generate(MapGenerationParameter parameter, MapPartFactory<F, E, N> factory) {
        List<F> fields = new ArrayList<>();
        List<E> edges = new ArrayList<>();
        List<N> nodes = new ArrayList<>();
        for (DiscreetPoint position : parameter.getPositions()) {
            fields.add(createSquareField(position, edges, nodes, factory));
        }
        BypassFinalFieldsUtil.setFieldsToNodes(fields, nodes);
        BypassFinalFieldsUtil.setEdgesToNodes(edges, nodes);
        BypassFinalFieldsUtil.setEdgesToEdge(edges);
        BypassFinalFieldsUtil.setFieldsToEdge(fields, edges);
        BypassFinalFieldsUtil.setFieldsToField(fields);
        return BypassHiddenConstructorUtil.createMapViaReflection(new Fields<>(fields));
    }



    @SuppressWarnings("unchecked")
    private F createSquareField(DiscreetPoint position, List<E> edgePool, List<N> pointPool, MapPartFactory<F, E, N> factory) {

        N center = factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection((2d * position.x) + 1d, (2d * position.y) + 1d));

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

        Field<F, E, N> field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }

}
