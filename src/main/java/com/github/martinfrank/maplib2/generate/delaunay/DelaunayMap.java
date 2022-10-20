package com.github.martinfrank.maplib2.generate.delaunay;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DelaunayMap {

    private Set<DelaunayTriangle> triangles = new HashSet<>();
    private Set<DelaunayPolygon> polygons = new HashSet<>();
    private Set<DelaunayNode> nodes = new HashSet<>();
    private final DelaunayNode min;
    private final DelaunayNode max;
    private final DelaunayTriangle surroundingTriangle;

    public DelaunayMap(DelaunayNode min, DelaunayNode max) {
        this.min = min;
        this.max = max;
        surroundingTriangle = new DelaunayTriangle(
                new DelaunayNode(min.getX() - 1, min.getY() - 1),
                new DelaunayNode((3 * max.getX()) + 1, min.getY()),
                new DelaunayNode(min.getX(), (3 * max.getY()) + 1));
    }


    public List<DelaunayTriangle> insert(DelaunayNode node) {
        if (nodes.contains(node)) {
            return Collections.emptyList();
        } else {
            nodes.add(node);
        }

        DelaunayTriangle surrounding = findSurroundingTriangle(node);
        triangles.remove(surrounding);
        DelaunayTriangle t1 = new DelaunayTriangle(surrounding.getA(), surrounding.getB(), node);
        DelaunayTriangle t2 = new DelaunayTriangle(surrounding.getA(), surrounding.getC(), node);
        DelaunayTriangle t3 = new DelaunayTriangle(surrounding.getB(), surrounding.getC(), node);
        triangles.add(t1);
        triangles.add(t2);
        triangles.add(t3);

        return Arrays.asList(t1, t2, t3);
    }

    private DelaunayTriangle findSurroundingTriangle(DelaunayNode node) {
        return triangles.stream().filter(t -> t.surrounds(node)).findAny().orElse(surroundingTriangle);
    }


    public List<DelaunayTriangle> getTriangles(DelaunayEdge e) {
        return triangles.stream().filter(t -> t.getEdges().contains(e)).collect(Collectors.toList());
    }

    public List<DelaunayEdge> getEdges() {
        Set<DelaunayEdge> edges = new HashSet<>();
        triangles.forEach(t -> edges.addAll(t.getEdges()));
        edges.addAll(surroundingTriangle.getEdges());
        return new ArrayList<>(edges);
    }

    public boolean isInBounds(double x, double y) {
        return x > min.getX() && x < max.getX() && y > min.getY() && y < max.getY();
    }


    public List<DelaunayTriangle> getTriangles() {
        return new ArrayList<>(triangles);
    }

    public void remove(DelaunayTriangle triangle) {
        triangles.remove(triangle);
    }

    public void add(DelaunayTriangle triangle) {
        triangles.add(triangle);
    }

    public boolean contains(DelaunayNode node) {
        return nodes.contains(node);
    }

    public void updateVoronoi() {
        polygons.clear();
        for (DelaunayNode node : nodes) {
            List<DelaunayEdge> edges = findEdges(node);
            boolean isSurrounded = !edges.isEmpty();
            for (DelaunayEdge e : edges) {
                if (getTriangles(e).size() != 2) {
                    isSurrounded = false;
                    break;
                }
            }
            if (isSurrounded) {
                Set<DelaunayNode> polygonNodes = new HashSet<>();
                edges.forEach(e -> getTriangles(e).forEach(t -> polygonNodes.add(t.getCenter())));
                polygons.add(new DelaunayPolygon(polygonNodes, node));
            }
        }
    }


    private List<DelaunayEdge> findEdges(DelaunayNode node) {
        return getEdges().stream()
                .filter(e -> e.getB().equals(node) || e.getA().equals(node))
                .collect(Collectors.toList());
    }

    public List<DelaunayEdge> getVoronoiEdges() {
        Set<DelaunayEdge> edges = new HashSet<>();
        polygons.forEach(p -> edges.addAll(p.getEdges()));
        return new ArrayList<>(edges);
    }

    public List<DelaunayEdge> getInnerEdges() {
        List<DelaunayEdge> edges = getEdges();
        return edges.stream()
                .filter(e -> !
                        (surroundingTriangle.getNodes().contains(e.getB())
                                || surroundingTriangle.getNodes().contains(e.getA())))
                .collect(Collectors.toList());
    }

    public DelaunayNode getMaxBounds() {
        return max;
    }

    public DelaunayNode getMinBounds() {
        return min;
    }

    public Set<DelaunayNode> getNodes(){
        return nodes;
    }

    public Set<DelaunayPolygon> getPolygons(){
        return polygons;
    }
}
