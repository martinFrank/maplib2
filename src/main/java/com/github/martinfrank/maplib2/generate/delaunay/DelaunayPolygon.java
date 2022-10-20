package com.github.martinfrank.maplib2.generate.delaunay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DelaunayPolygon {

    private final List<DelaunayNode> nodes;
    private final List<DelaunayEdge> edges = new ArrayList<>();
    private final DelaunayNode center;

    public DelaunayPolygon(Collection<DelaunayNode> nodes, DelaunayNode center) {
        this.nodes = new ArrayList<>(nodes);
        this.nodes.sort(new CounterClockwiseNodeComparator(center));
        this.center = center;
        createEdges();

    }

    private void createEdges() {
        for (int i = 0; i < nodes.size(); i++) {
            DelaunayNode a = nodes.get(i);
            DelaunayNode b = nodes.get(i == 0 ? nodes.size() - 1 : i - 1);
            edges.add(new DelaunayEdge(a, b));
        }
    }

    public List<DelaunayEdge> getEdges() {
        return edges;
    }

    public DelaunayNode getCenter() {
        return center;
    }

    public boolean surrounds(DelaunayNode n) {
        //winding rule
        //https://algorithmtutor.com/Computational-Geometry/Check-if-a-point-is-inside-a-polygon/
        for (int i = 0; i < nodes.size(); i++) {
            DelaunayNode a = nodes.get(i);
            DelaunayNode b = nodes.get(i == 0 ? nodes.size() - 1 : i - 1);

            double bigA = -1 * (b.getY() - a.getY());
            double bigB = b.getX() - a.getX();
            double bigC = -1 * (bigA * a.getX() + bigB * a.getY());

            double d = bigA * n.getX() + bigB * n.getY() + bigC;

            if (d > 0) {
                return false;
            }
        }
        return true;
    }
}
