package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.generate.delaunay.CircumferenceConditionChecker;
import com.github.martinfrank.maplib2.generate.delaunay.DelaunayEdge;
import com.github.martinfrank.maplib2.generate.delaunay.DelaunayMap;
import com.github.martinfrank.maplib2.generate.delaunay.DelaunayNode;
import com.github.martinfrank.maplib2.generate.delaunay.DelaunayPolygon;
import com.github.martinfrank.maplib2.generate.delaunay.DelaunayTriangle;
import com.github.martinfrank.maplib2.generate.delaunay.Flipper;
import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Fields;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib2.map.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("rawtypes")
public class DelaunayMapGenerator<F extends Field, E extends Edge, N extends Node> {

    public Map<F, E, N> generate(MapGenerationParameter parameter, MapPartFactory<F, E, N> factory) {
        DelaunayTriangulation delaunayTriangulation = new DelaunayTriangulation(new DelaunayNode(-10, -10), new DelaunayNode(20, 20));
        java.util.Map<DelaunayNode, Point> positionMap = new HashMap<>();
        for (Point position : MapGeneratorUtils.mapSizeToPoints(parameter)) {
//            fields.add(createSquareField(position, edges, nodes, factory));
            double dx = -1 + (2*Math.random());
            double dy = -1 + (2*Math.random());
            DelaunayNode delaunayNode = new DelaunayNode(position.x+dx, position.y+dy);
            delaunayTriangulation.insertNode(new DelaunayNode(position.x+dx, position.y+dy));
            positionMap.put(delaunayNode, position);
            System.out.println("insert: "+position);
        }
        System.out.println("done");
        Map<F,E,N> map = convertToMap(delaunayTriangulation, factory, positionMap);
        return map;
    }

    private Map<F,E,N> convertToMap(DelaunayTriangulation delaunayTriangulation, MapPartFactory<F, E, N> factory, java.util.Map<DelaunayNode, Point> positionMap) {
        List<F> fields = new ArrayList<>();
        List<E> edges = new ArrayList<>();
        List<N> nodes = new ArrayList<>();

        List<DelaunayNode> delaunayNodes = new ArrayList<>(delaunayTriangulation.map.getNodes());
        for (DelaunayNode delaunayNode: delaunayNodes){
            N node = convertNode(delaunayNode, factory);
            nodes.add(node);
        }
//        List<DelaunayEdge> delaunayEdges = delaunayTriangulation.map.getEdges();
//        for(DelaunayEdge delaunayEdge: delaunayEdges){
//            N aSample =  convertNode(delaunayEdge.getA(), factory);
//            N bSample = convertNode(delaunayEdge.getB(), factory);
//            N a = findInNodes(aSample, nodes);
//            N b = findInNodes(bSample, nodes);
//            E edge = factory.createEdge(BypassHiddenConstructorUtil.createEdgeViaReflection(a,b));
//            edges.add(edge);
//        }

//        List<DelaunayTriangle> delaunayTriangles = delaunayTriangulation.map.getTriangles();
//        for(DelaunayTriangle delaunayTriangle: delaunayTriangles){
//            N aSample =  convertNode(delaunayTriangle.getA(), factory);
//            N bSample = convertNode(delaunayTriangle.getB(), factory);
//            N cSample = convertNode(delaunayTriangle.getC(), factory);
//            N centerSample = convertNode(delaunayTriangle.getCenter(), factory);
//            N a = BypassHiddenConstructorUtil.createIfNotExists(nodes, aSample.x, aSample.y, factory);
//            N b = BypassHiddenConstructorUtil.createIfNotExists(nodes, bSample.x, bSample.y, factory);
//            N c = BypassHiddenConstructorUtil.createIfNotExists(nodes, cSample.x, cSample.y, factory);
//            List<N> triaPoints = Arrays.asList(a,b,c);
//
//            E ab = (E) BypassHiddenConstructorUtil.createIfNotExists(edges, a, b, factory);
//            E bc = (E) BypassHiddenConstructorUtil.createIfNotExists(edges, b, c, factory);
//            E ca = (E) BypassHiddenConstructorUtil.createIfNotExists(edges, c, a, factory);
//            List<E> triaEdges = Arrays.asList(ab,bc,ca);
//
//            Point center = new Point(Math.random(), Math.random());
//
//            F field = (F) factory.createField(BypassHiddenConstructorUtil.createFieldViaReflection(center, triaPoints, triaEdges, centerSample));
//            fields.add(field);
//        }

        List<DelaunayPolygon> delaunayPolygons = new ArrayList<>(delaunayTriangulation.map.getPolygons());
        for(DelaunayPolygon delaunayPolygon: delaunayPolygons){
            List<E> polgonEdges = new ArrayList<>();
            List<N> polgonNodes = new ArrayList<>();
            for(DelaunayEdge delaunayEdge : delaunayPolygon.getEdges()) {
                N aSample = convertNode(delaunayEdge.getA(), factory);
                N bSample = convertNode(delaunayEdge.getB(), factory);
                N a = BypassHiddenConstructorUtil.createIfNotExists(nodes, aSample.x, aSample.y, factory);
                N b = BypassHiddenConstructorUtil.createIfNotExists(nodes, bSample.x, bSample.y, factory);
                polgonNodes.add(a);
                E edge = (E) BypassHiddenConstructorUtil.createIfNotExists(edges, a, b, factory);
                polgonEdges.add(edge);
            }

            N centerSample = convertNode(delaunayPolygon.getCenter(), factory);
//            N center = BypassHiddenConstructorUtil.createIfNotExists(nodes, centerSample.x, centerSample.y, factory);
            Point center = new Point(Math.random(), Math.random());

            F field = (F) factory.createField(BypassHiddenConstructorUtil.createFieldViaReflection(center, polgonNodes, polgonEdges, centerSample));
            fields.add(field);
        }

        BypassFinalFieldsUtil.setFieldsToNodes(fields, nodes);
        BypassFinalFieldsUtil.setEdgesToNodes(edges, nodes);
        BypassFinalFieldsUtil.setEdgesToEdge(edges);
        BypassFinalFieldsUtil.setFieldsToEdge(fields, edges);
        BypassFinalFieldsUtil.setFieldsToField(fields);

        return BypassHiddenConstructorUtil.createMapViaReflection(new Fields<>(fields));
    }

    private N findInNodes(N sample, List<N> nodes) {
        Optional<N> candidate = nodes.stream().filter(n -> n.equals(sample)).findAny();
        if(candidate.isPresent()){
            return candidate.get();
        }
        nodes.add(sample);
        return sample;
    }

    private N convertNode(DelaunayNode delaunayNode, MapPartFactory<F, E, N> factory) {
        double x = delaunayNode.getX();
        double y = delaunayNode.getY();
        return factory.createNode(BypassHiddenConstructorUtil.createNodeViaReflection(x,y));
    }


    private static class DelaunayTriangulation {
        private final DelaunayMap map;

        private DelaunayTriangulation() {
            this(new DelaunayNode(-10, -10), new DelaunayNode(10, 10));
        }

        private DelaunayTriangulation(DelaunayNode min, DelaunayNode max) {
            map = new DelaunayMap(min, max);
        }

        public void insertNode(DelaunayNode node) {
            map.insert(node);
            while (map.getEdges().stream().anyMatch(this::flipIfRequired)) ;
            map.updateVoronoi();
        }

        /**
         * @param edge to check
         * @return true if flip was performed.
         */
        private boolean flipIfRequired(DelaunayEdge edge) {
            boolean isFlipNeeded = false;
            List<DelaunayTriangle> neighbors = map.getTriangles(edge);
            if (neighbors.size() == 2) {
                DelaunayTriangle t1 = neighbors.get(0);
                DelaunayTriangle t2 = neighbors.get(1);
                if (CircumferenceConditionChecker.isViolated(t1, t2)) {
                    isFlipNeeded = true;
                    List<DelaunayTriangle> flipped = Flipper.flip(t1, t2, edge);
                    DelaunayTriangle t3 = flipped.get(0);
                    DelaunayTriangle t4 = flipped.get(1);
                    map.remove(t1);
                    map.remove(t2);
                    map.add(t3);
                    map.add(t4);
                }
            }
            return isFlipNeeded;
        }
    }
}
