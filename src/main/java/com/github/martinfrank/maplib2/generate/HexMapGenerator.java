package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Fields;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib2.map.MapStyle;
import com.github.martinfrank.maplib2.map.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("rawtypes")
public class HexMapGenerator<F extends Field, E extends Edge, N extends Node> {

    @SuppressWarnings("unchecked")
    public Map<F, E, N> generate(MapGenerationParameter parameter, MapPartFactory<F, E, N> factory) {
        List<F> fields = new ArrayList<>();
        List<E> edges = new ArrayList<>();
        List<N> nodes = new ArrayList<>();
        for (Point position : MapGeneratorUtils.mapSizeToPoints(parameter)) {
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
        return BypassHiddenConstructorUtil.createMapViaReflection(new Fields<>(fields));
    }

    @SuppressWarnings("unchecked")
    private F createHorizontalHexField(Point position, List<E> edgePool, List<N> nodePools, MapPartFactory<F, E, N> factory) {

        double cx = 2 + 3 * position.x;
        double cy = isEvenColumn(position) ? 2 + 4 * position.y : 4 + 4 * position.y;

        N center = factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection(cx, cy));

        N a = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x - 2, center.y, factory);
        N b = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x - 1, center.y - 2, factory);
        N c = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x + 1, center.y - 2, factory);
        N d = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x + 2, center.y, factory);
        N e = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x + 1, center.y + 2, factory);
        N f = BypassHiddenConstructorUtil.createIfNotExists(nodePools, center.x - 1, center.y + 2, factory);
        List<N> nodes = Arrays.asList(a, b, c, d, e, f);

        E ab = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, a, b, factory);
        E bc = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, b, c, factory);
        E cd = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, c, d, factory);
        E de = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, d, e, factory);
        E ef = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, e, f, factory);
        E fa = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, f, a, factory);
        List<E> edges = Arrays.asList(ab, bc, cd, de, ef, fa);

        Field<F, E, N> field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }


    @SuppressWarnings("unchecked")
    private F createVerticalHexField(Point position, List<E> edgePool, List<N> pointPool, MapPartFactory<F, E, N> factory) {

        double cx = isEvenRow(position) ? 2 + 4 * position.x : 4 + 4 * position.x;
        double cy = 2 + 3 * position.y;

        N center = factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection(cx, cy));

        N a = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 2, center.y - 1, factory);
        N b = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x, center.y - 2, factory);
        N c = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 2, center.y - 1, factory);
        N d = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 2, center.y + 1, factory);
        N e = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x, center.y + 2, factory);
        N f = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 2, center.y + 1, factory);
        List<N> nodes = Arrays.asList(a, b, c, d, e, f);

        E ab = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, a, b, factory);
        E bc = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, b, c, factory);
        E cd = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, c, d, factory);
        E de = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, d, e, factory);
        E ef = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, e, f, factory);
        E fa = (E) BypassHiddenConstructorUtil.createIfNotExists(edgePool, f, a, factory);
        List<E> edges = Arrays.asList(ab, bc, cd, de, ef, fa);

        Field<F, E, N> field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }

    private boolean isEvenRow(Point p) {
        return ((int) p.y) % 2 == 0;
    }

    private boolean isEvenColumn(Point p) {
        return ((int) p.x) % 2 == 0;
    }

}
