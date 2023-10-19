package com.github.martinfrank.maplib2.demoapp.generate;

import com.github.martinfrank.maplib2.demoapp.geo.DiscreetPoint;
import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Field;
import com.github.martinfrank.maplib2.demoapp.map.Fields;
import com.github.martinfrank.maplib2.demoapp.map.Map;
import com.github.martinfrank.maplib2.demoapp.map.MapPartFactory;
import com.github.martinfrank.maplib2.demoapp.map.MapStyle;
import com.github.martinfrank.maplib2.demoapp.map.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HexMapGenerator {

    public Map generate(MapGenerationParameter parameter, MapPartFactory factory) {
        List<Field> fields = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        List<Node> nodes = new ArrayList<>();
        for (DiscreetPoint position : parameter.getPositions()) {
            if (parameter.orientation == MapStyle.Orientation.VERTICAL) {
                fields.add(createVerticalHexField(position, edges, nodes, factory));
            }
            if (parameter.orientation == MapStyle.Orientation.HORIZONTAL) {
                fields.add(createHorizontalHexField(position, edges, nodes, factory));
            }
        }
        BypassFinalFieldsUtil.setFieldsToNodes(fields, nodes);
        BypassFinalFieldsUtil.setEdgesToNodes(edges, nodes);
        BypassFinalFieldsUtil.setEdgesToEdge(edges);
        BypassFinalFieldsUtil.setFieldsToEdge(fields, edges);
        BypassFinalFieldsUtil.setFieldsToField(fields);
        return BypassHiddenConstructorUtil.createMapViaReflection(new Fields(fields));
    }

    private Field createHorizontalHexField(DiscreetPoint position, List<Edge> edgePool, List<Node> nodePools, MapPartFactory factory) {

        int cx = 2 + 3 * position.x;
        double cy = isEvenColumn(position) ? 2 + 4 * position.y : 4 + 4 * position.y;

        Node center = factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection(cx, cy));

        Node a = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x - 2, center.y, factory);
        Node b = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x - 1, center.y - 2, factory);
        Node c = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x + 1, center.y - 2, factory);
        Node d = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x + 2, center.y, factory);
        Node e = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x + 1, center.y + 2, factory);
        Node f = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x - 1, center.y + 2, factory);
        List<Node> nodes = Arrays.asList(a, b, c, d, e, f);

        Edge ab = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, a, b, factory);
        Edge bc = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, b, c, factory);
        Edge cd = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, c, d, factory);
        Edge de = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, d, e, factory);
        Edge ef = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, e, f, factory);
        Edge fa = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, f, a, factory);
        List<Edge> edges = Arrays.asList(ab, bc, cd, de, ef, fa);

        Field field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }

    private Field createVerticalHexField(DiscreetPoint position, List<Edge> edgePool, List<Node> pointPool, MapPartFactory factory) {

        double cx = isEvenRow(position) ? 2 + 4 * position.x : 4 + 4 * position.x;
        int cy = 2 + 3 * position.y;

        Node center = factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection(cx, cy));

        Node a = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 2, center.y - 1, factory);
        Node b = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x, center.y - 2, factory);
        Node c = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 2, center.y - 1, factory);
        Node d = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 2, center.y + 1, factory);
        Node e = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x, center.y + 2, factory);
        Node f = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 2, center.y + 1, factory);
        List<Node> nodes = Arrays.asList(a, b, c, d, e, f);

        Edge ab = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, a, b, factory);
        Edge bc = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, b, c, factory);
        Edge cd = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, c, d, factory);
        Edge de = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, d, e, factory);
        Edge ef = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, e, f, factory);
        Edge fa = (Edge) BypassHiddenConstructorUtil.createIfNotExists(edgePool, f, a, factory);
        List<Edge> edges = Arrays.asList(ab, bc, cd, de, ef, fa);

        Field field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }

    private boolean isEvenRow(DiscreetPoint p) {
        return p.y % 2 == 0;
    }

    private boolean isEvenColumn(DiscreetPoint p) {
        return p.x % 2 == 0;
    }

}
