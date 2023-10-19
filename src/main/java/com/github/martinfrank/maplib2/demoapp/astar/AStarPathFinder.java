package com.github.martinfrank.maplib2.demoapp.astar;

import com.github.martinfrank.maplib2.demoapp.geo.Line;
import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Field;
import com.github.martinfrank.maplib2.demoapp.map.Map;
import com.github.martinfrank.maplib2.demoapp.map.Node;

import java.util.ArrayList;
import java.util.List;

public class AStarPathFinder {

    private AStarPathFinder() {
    }

    public static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F, E>> Path<F> findPath(Walker<F,E> walker, F start, F target, Map<F, E, N> map) {
        if (start == null || target == null || map == null) {
            return Path.emptyPath();
        }

        List<AStarFieldWrapper<F>> openList = new ArrayList<>();
        List<AStarFieldWrapper<F>> closedList = new ArrayList<>();

        AStarFieldWrapper<F> startWrapper = new AStarFieldWrapper<>(start);
        AStarFieldWrapper<F> targetWrapper = new AStarFieldWrapper<>(target);
        openList.add(startWrapper);

        while (true) {
            AStarFieldWrapper<F> current = getLeastF(openList);
            if (noWayFound(current, walker.getMaximumSearchDepth())) {
                return Path.emptyPath();
            }
            if (current.field.equals(target)) {
                targetWrapper.setFrom(current);
                break;
            }
            openList.remove(current);
            closedList.add(current);
            expandNode(current, walker, targetWrapper, openList, closedList);
        }

        return new Path<>(targetWrapper);
    }

    @SuppressWarnings("rawtypes")
    private static <F extends Field<F, E, ?>, E extends Edge> void expandNode(AStarFieldWrapper<F> current, Walker<F,E> walker, AStarFieldWrapper<F> end, List<AStarFieldWrapper<F>> openList, List<AStarFieldWrapper<F>> closedList ) {
        List<AStarFieldWrapper<F>> nodeList = getNeighbors(walker, current);
        for (AStarFieldWrapper<F> node : nodeList) {
            double distance = walker.getEnterCosts(current.field, node.field);
            addIfRequired(current, node, end, distance, openList, closedList);
        }
    }

    private static <F extends Field<F, ?, ?>> void addIfRequired(AStarFieldWrapper<F> current, AStarFieldWrapper<F> nNode, AStarFieldWrapper<F> end, double distance, List<AStarFieldWrapper<F>> openList, List<AStarFieldWrapper<F>> closedList) {
        if (!closedList.contains(nNode)) {
            if (openList.contains(nNode)) {
                AStarFieldWrapper<F> can = findInList(openList, nNode);
                if (can.getG() < nNode.getG()) {
                    can.setFrom(current);
                    can.setG(current.getG() + distance);
                }
            } else {
                nNode.setFrom(current);
                double h = Line.distance(nNode.field.center, end.field.center);
                nNode.setH(10d*h);
                nNode.setG( current.getG() + distance);
                openList.add(nNode);
            }
        }
    }

    private static <F extends Field<F, ?, ?>> AStarFieldWrapper<F> findInList(List<AStarFieldWrapper<F>> list, AStarFieldWrapper<F> sample) {
        return list.stream().filter(asw -> asw.equals(sample) ).findAny().get();
    }

    @SuppressWarnings("rawtypes")
    private static <F extends Field<F, E, ?>, E extends Edge> List<AStarFieldWrapper<F>> getNeighbors(Walker<F, E> walker, AStarFieldWrapper<F> current) {
        List<AStarFieldWrapper<F>> neighbors = new ArrayList<>();
        for(F field: current.field.fields){
            if(walker.canEnter(current.field, field)){
                neighbors.add(new AStarFieldWrapper<>(field));
            }
        }
        return neighbors;
    }

    private static <F extends Field<F, ?, ?>> AStarFieldWrapper<F> getLeastF(List<AStarFieldWrapper<F>> list) {
        double currentF = 1000000;
        AStarFieldWrapper<F> ret = null;
        for (AStarFieldWrapper<F> n : list) {
            if (n.getF() < currentF) {
                currentF = n.getF();
                ret = n;
            }
        }
        return ret;
    }

    @SuppressWarnings("rawtypes")
    private static <F extends Field> boolean noWayFound(AStarFieldWrapper<F> current, double maxPathLength) {
        return current == null || current.getG() > maxPathLength * 10;
    }
}
