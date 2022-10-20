package com.github.martinfrank.maplib2.generate.delaunay;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Flipper {

    private Flipper() {

    }

    public static List<DelaunayTriangle> flip(DelaunayTriangle t1, DelaunayTriangle t2, DelaunayEdge edge) {

        List<DelaunayNode> withoutA = getNodes(t1, t2);
        withoutA.remove(edge.getA());
        DelaunayTriangle t3 = new DelaunayTriangle(withoutA.get(0), withoutA.get(1), withoutA.get(2));

        List<DelaunayNode> withoutB = getNodes(t1, t2);
        withoutB.remove(edge.getB());
        DelaunayTriangle t4 = new DelaunayTriangle(withoutB.get(0), withoutB.get(1), withoutB.get(2));

        return Arrays.asList(t3, t4);
    }

    private static List<DelaunayNode> getNodes(DelaunayTriangle t1, DelaunayTriangle t2) {
        Set<DelaunayNode> nodes = new HashSet<>(t1.getNodes());
        nodes.addAll(t2.getNodes());
        return new ArrayList<>(nodes);
    }
}
