package com.github.martinfrank.maplib.maplib2.astar;

import com.github.martinfrank.maplib.maplib2.geo.Line;
import com.github.martinfrank.maplib.maplib2.map.Field;
import com.github.martinfrank.maplib.maplib2.map.Map;

import java.util.ArrayList;
import java.util.List;

public class AStarPathFinder {

    private AStarPathFinder() {
    }

    public static Path findPath(Walker walker, Field start, Field target, Map map) {
        if (start == null || target == null || map == null) {
            return Path.emptyPath();
        }

        List<AStarFieldWrapper> openList = new ArrayList<>();
        List<AStarFieldWrapper> closedList = new ArrayList<>();

        AStarFieldWrapper startWrapper = new AStarFieldWrapper(start);
        AStarFieldWrapper targetWrapper = new AStarFieldWrapper(target);
        openList.add(startWrapper);

        while (true) {
            AStarFieldWrapper current = getLeastF(openList);
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

        return new Path(targetWrapper);
    }
 
    private static void expandNode(AStarFieldWrapper current, Walker walker, AStarFieldWrapper end, List<AStarFieldWrapper> openList, List<AStarFieldWrapper> closedList ) {
        List<AStarFieldWrapper> nodeList = getNeighbors(walker, current);
        for (AStarFieldWrapper node : nodeList) {
            double distance = walker.getEnterCosts(current.field, node.field);
            addIfRequired(current, node, end, distance, openList, closedList);
        }
    }

    private static void addIfRequired(AStarFieldWrapper current, AStarFieldWrapper nNode, AStarFieldWrapper end, double distance, List<AStarFieldWrapper> openList, List<AStarFieldWrapper> closedList) {
        if (!closedList.contains(nNode)) {
            if (openList.contains(nNode)) {
                AStarFieldWrapper can = findInList(openList, nNode);
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

    private static AStarFieldWrapper findInList(List<AStarFieldWrapper> list, AStarFieldWrapper sample) {
        return list.stream().filter(asw -> asw.equals(sample) ).findAny().orElseThrow();
    }

    private static List<AStarFieldWrapper> getNeighbors(Walker walker, AStarFieldWrapper current) {
        List<AStarFieldWrapper> neighbors = new ArrayList<>();
        for(Field field: current.field.fields){
            if(walker.canEnter(current.field, field)){
                neighbors.add(new AStarFieldWrapper(field));
            }
        }
        return neighbors;
    }

    private static AStarFieldWrapper getLeastF(List<AStarFieldWrapper> list) {
        double currentF = 1000000;
        AStarFieldWrapper ret = null;
        for (AStarFieldWrapper n : list) {
            if (n.getF() < currentF) {
                currentF = n.getF();
                ret = n;
            }
        }
        return ret;
    }

    private static boolean noWayFound(AStarFieldWrapper current, double maxPathLength) {
        return current == null || current.getG() > maxPathLength * 10;
    }
}
