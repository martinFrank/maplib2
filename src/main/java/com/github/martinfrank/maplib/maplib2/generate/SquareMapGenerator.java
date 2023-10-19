package com.github.martinfrank.maplib.maplib2.generate;

import com.github.martinfrank.maplib.maplib2.geo.DiscreetPoint;
import com.github.martinfrank.maplib.maplib2.map.Edge;
import com.github.martinfrank.maplib.maplib2.map.Field;
import com.github.martinfrank.maplib.maplib2.map.Fields;
import com.github.martinfrank.maplib.maplib2.map.Map;
import com.github.martinfrank.maplib.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib.maplib2.map.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SquareMapGenerator {

    public Map generate(MapGenerationParameter parameter, MapPartFactory factory) {
        List<Field> fields = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        for (DiscreetPoint position : parameter.getPositions()) {
            fields.add(createSquareField(position, edges, nodes, factory));
        }
        BypassFinalFieldsUtil.setFieldsToNodes(fields, nodes);
        BypassFinalFieldsUtil.setEdgesToNodes(edges, nodes);
        BypassFinalFieldsUtil.setEdgesToEdge(edges);
        BypassFinalFieldsUtil.setFieldsToEdge(fields, edges);
        BypassFinalFieldsUtil.setFieldsToField(fields);
        return BypassHiddenConstructorUtil.createMapViaReflection(new Fields(fields));
    }



    @SuppressWarnings("unchecked")
    private Field createSquareField(DiscreetPoint position, List<Edge> edgePool, List<Node> pointPool, MapPartFactory factory) {

        Node center = factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection((2d * position.x) + 1d, (2d * position.y) + 1d));

        Node a = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 1, center.y - 1, factory);
        Node b = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 1, center.y - 1, factory);
        Node c = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 1, center.y + 1, factory);
        Node d = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 1, center.y + 1, factory);
        List<Node> nodes = Arrays.asList(a, b, c, d);


        Edge ab = BypassHiddenConstructorUtil.createIfNotExists(edgePool, a, b, factory);
        Edge bc = BypassHiddenConstructorUtil.createIfNotExists(edgePool, b, c, factory);
        Edge cd = BypassHiddenConstructorUtil.createIfNotExists(edgePool, c, d, factory);
        Edge da = BypassHiddenConstructorUtil.createIfNotExists(edgePool, d, a, factory);
        List<Edge> edges = Arrays.asList(ab, bc, cd, da);

        Field field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }

}
