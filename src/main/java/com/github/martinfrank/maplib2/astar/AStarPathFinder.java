package com.github.martinfrank.maplib2.astar;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AStarPathFinder {

    public static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F, E>> List<F> findPath(Walker walker, F start, F target, Map<F, E, N> map) {
        if (start == null || target == null || map == null) {
            return Collections.emptyList();
        }

        List<AStarFieldWrapper<F>> openList = new ArrayList<>();
        List<AStarFieldWrapper<F>> closedList = new ArrayList<>();

        AStarFieldWrapper startWrapper = new AStarFieldWrapper(start);
        AStarFieldWrapper targetWrapper = new AStarFieldWrapper(target);
        openList.add(startWrapper);

        while (true) {
            AStarFieldWrapper<F> current = getLeastF(openList);
            if (noWayFound(current, 500)) {
                return Collections.emptyList();
            }
            if (current.field.equals(target)) {
                targetWrapper.setFrom(current);
                break;
            }
            openList.remove(current);
            closedList.add(current);
            expandNode(current, map, walker, targetWrapper, openList, closedList);
        }

//        return buildPath(end);

        return Collections.emptyList();
    }

    private static <F extends Field<F, ?, ?>> void expandNode(AStarFieldWrapper<F> current, Map<F, ?, ?> map, Walker walker, AStarFieldWrapper<F> end, List<AStarFieldWrapper<F>> openList, List<AStarFieldWrapper<F>> closedList ) {

        List<AStarFieldWrapper<F>> nodeList = getNeighbors(walker, current);
        for (AStarFieldWrapper<F> node : nodeList) {
            int distance = walker.getEnterCosts(node);
            addIfRequired(node, current, end, distance, openList, closedList);
//            if (checkIsPassable(center, n, walker, map)) {
//                F to = map.getField(n.x, n.y);
//                if (to != null) {
//                    int distance = walker.getEnterCosts(center, to);
//                    addIfRequired(n, current, end, distance);
//                }
//            }
        }

    }

    private static <F extends Field<F, ?, ?>> void addIfRequired(AStarFieldWrapper<F> nNode, AStarFieldWrapper<F> current, AStarFieldWrapper<F> end, int distance, List<AStarFieldWrapper<F>> openList, List<AStarFieldWrapper<F>> closedList) {
//        if (!isPosInList(nNode, cList)) {
        if (!closedList.contains(nNode)) {
//            if (openList.contains(nNode)) {
//                AStarFieldWrapper<F> can = openList.get(nNode);
//                if (nNode.getGoDistance() < nNode.g) {
//                    can.from = current;
//                    can.g = current.g + distance;
//                    can.f = can.h + can.g;
//                }
//            } else {
//                nNode.from = current;
//                nNode.h = 10 * (Math.abs(end.x - nNode.x) + Math.abs(end.y - nNode.y));
//                nNode.g = current.g + distance;
//                nNode.f = nNode.h + nNode.g;
//                oList.add(nNode);
//            }
        }
    }

    private static <F extends Field<F, ?, ?>> List<AStarFieldWrapper<F>> getNeighbors(Walker walker, AStarFieldWrapper<F> current) {
        List<AStarFieldWrapper<F>> neighbors = new ArrayList<>();
        for(F field: current.field.fields){
            if(walker.canEnter(field)){
                neighbors.add(new AStarFieldWrapper<>(field));
            }
        }
        return neighbors;
    }

    private static <F extends Field<F, ?, ?>> AStarFieldWrapper<F> getLeastF(List<AStarFieldWrapper<F>> list) {
        int cf = Integer.MAX_VALUE;
        AStarFieldWrapper<F> ret = null;
        for (AStarFieldWrapper<F> n : list) {
            if (n.getEstimate() < cf) {
                cf = n.getEstimate();
                ret = n;
            }
        }
        return ret;
    }

    private static boolean noWayFound(AStarFieldWrapper current, int maxPathLength) {
        return current == null || current.getGoDistance() > maxPathLength * 10;
    }
}
