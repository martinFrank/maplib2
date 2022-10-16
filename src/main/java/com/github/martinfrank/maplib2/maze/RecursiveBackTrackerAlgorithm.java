package com.github.martinfrank.maplib2.maze;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecursiveBackTrackerAlgorithm {
    public static <F extends Field<E, N>, E extends Edge<F, N>, N extends Node> void createMaze(Map<F,E,N> map) {
        Deque<F> backTrackerStack = new ArrayDeque<>();
        Set<F> closed = new HashSet<>();
        closeMapBorders(map, closed);

//        int counter = 0;
//        F current = getMapAccessor().getRandomStartInBounds(2);
//        getCarver().carveInto(current);
//        current.getData().setCounter(counter);
//        counter++;
//        do {
//            List<F> nbgs = getCarver().getNeighborsForWallCarving(current, closed);
//            if (nbgs.isEmpty()) {
//                current = backTrackerStack.pop();
//            } else {
//                F next = nbgs.get(0);
//                getCarver().carveInto(current, next);
//                backTrackerStack.push(current);
//                current = next;
//
//                current.getData().setCounter(counter);
//                counter++;
//
//                closed.add(current);
//            }
//        } while (!backTrackerStack.isEmpty());
    }

    private static <F extends Field, E extends Edge, N extends Node> void closeMapBorders(Map<F,E,N> map, Set<F> closed) {
//        for (int dx = 0; dx < map.columns; dx++) {
//            addToClosed(dx, 0, closed);
//            addToClosed(dx, getMap().getRows() - 1, closed);
//            if (fillAdditionalRows()) {
//                addToClosed(dx, 1, closed);
//                addToClosed(dx, getMap().getRows() - 2, closed);
//            }
//        }
//        for (int dy = 0; dy < getMap().getRows(); dy++) {
//            addToClosed(0, dy, closed);
//            addToClosed(getMap().getColumns() - 1, dy, closed);
//            if (fillAdditionalColumns()) {
//                addToClosed(1, dy, closed);
//                addToClosed(getMap().getColumns() - 2, dy, closed);
//            }
//        }
//        for (int dx = 0; dx < getMap().getColumns(); dx++) {
//            for (int dy = 0; dy < getMap().getRows(); dy++) {
//                setBlocked(dx, dy);
//            }
//        }
    }
}
