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

public class HexMapGenerator<F extends Field<E, N>, E extends Edge<N>, N extends Node> {

    public Map<F, E, N> generate(MapGenerationParameter parameter, MapPartFactory<F, E, N> factory) {
        List<F> fields = new ArrayList<>();
        List<E> edgePool = new ArrayList<>();
        List<N> pointPool = new ArrayList<>();
        for (Point position : MapGeneratorUtils.mapSizeToPoints(parameter)) {
            if (parameter.orientation == MapStyle.Orientation.VERTICAL) {
                fields.add(createVerticalHexField(position, edgePool, pointPool, factory));
            }
            if (parameter.orientation == MapStyle.Orientation.HORIZONTAL) {
                fields.add(createHorizontalHexField(position, edgePool, pointPool, factory));
            }
        }
        return BypassHiddenConstructorUtil.createMapViaReflection(new Fields<>(fields));
    }

    private F createHorizontalHexField(Point position, List<E> edgePool, List<N> pointPool, MapPartFactory<F, E, N> factory) {

        double cx = 2 + 3 * position.x;
        double cy = isEvenColumn(position) ? 2 + 4 * position.y : 4 + 4 * position.y;

        N center = factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection(cx, cy));

        N a = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 2, center.y, factory);
        N b = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 1, center.y - 2, factory);
        N c = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 1, center.y - 2, factory);
        N d = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 2, center.y, factory);
        N e = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x + 1, center.y + 2, factory);
        N f = BypassHiddenConstructorUtil.createIfNotExists(pointPool, center.x - 1, center.y + 2, factory);

        List<N> nodes = Arrays.asList(a, b, c, d, e, f);

        E ab = BypassHiddenConstructorUtil.createIfNotExists(edgePool, a, b, factory);
        E bc = BypassHiddenConstructorUtil.createIfNotExists(edgePool, b, c, factory);
        E cd = BypassHiddenConstructorUtil.createIfNotExists(edgePool, c, d, factory);
        E de = BypassHiddenConstructorUtil.createIfNotExists(edgePool, d, e, factory);
        E ef = BypassHiddenConstructorUtil.createIfNotExists(edgePool, e, f, factory);
        E fa = BypassHiddenConstructorUtil.createIfNotExists(edgePool, f, a, factory);
        List<E> edges = Arrays.asList(ab, bc, cd, de, ef, fa);

        Field<E, N> field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }


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

        E ab = BypassHiddenConstructorUtil.createIfNotExists(edgePool, a, b, factory);
        E bc = BypassHiddenConstructorUtil.createIfNotExists(edgePool, b, c, factory);
        E cd = BypassHiddenConstructorUtil.createIfNotExists(edgePool, c, d, factory);
        E de = BypassHiddenConstructorUtil.createIfNotExists(edgePool, d, e, factory);
        E ef = BypassHiddenConstructorUtil.createIfNotExists(edgePool, e, f, factory);
        E fa = BypassHiddenConstructorUtil.createIfNotExists(edgePool, f, a, factory);
        List<E> edges = Arrays.asList(ab, bc, cd, de, ef, fa);

        Field<E, N> field = BypassHiddenConstructorUtil.createFieldViaReflection(position, nodes, edges, center);
        return factory.createField(field);
    }

    private boolean isEvenRow(Point p) {
        return ((int) p.y) % 2 == 0;
    }

    private boolean isEvenColumn(Point p) {
        return ((int) p.x) % 2 == 0;
    }

}
