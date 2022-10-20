package com.github.martinfrank.maplib2.generate.delaunay;

import java.util.ArrayList;
import java.util.List;

public class CircumferenceConditionChecker {

    private CircumferenceConditionChecker() {

    }

    public static boolean isViolated(DelaunayTriangle t1, DelaunayTriangle t2) {
        List<DelaunayNode> xNodes = new ArrayList<>(t2.getNodes());
        xNodes.removeAll(t1.getNodes());
        DelaunayNode x = xNodes.get(0);

        List<DelaunayNode> yNodes = new ArrayList<>(t1.getNodes());
        yNodes.removeAll(t2.getNodes());
        DelaunayNode y = yNodes.get(0);
        return t1.isInCircumference(x) || t2.isInCircumference(y);
    }
}
