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
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RecursiveBackTrackerAlgorithm {

    @SuppressWarnings("rawtypes")
    public static <F extends Field<E, N>, E extends Edge<F, N>, N extends Node> void createMaze(Map<F, E, N> map) {
        Deque<F> backTrackerStack = new ArrayDeque<>();
        Set<F> closed = new HashSet<>();
        setAllBocked(map);
        closeMapBorders(map, closed);

        F current = map.fields.getRandomStart();
        current.setPassable(true);

//        do {
//            List<F> nbgs = current.fields;
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

    @SuppressWarnings("rawtypes")
    private static <F extends Field<E, N>, E extends Edge<F, N>, N extends Node> void setAllBocked(Map<F, E, N> map) {
        map.fields.stream().forEach(RecursiveBackTrackerAlgorithm::setBlocked);
    }

    @SuppressWarnings("rawtypes")
    private static <F extends Field<E, N>, E extends Edge<F, N>, N extends Node> void setBlocked(F field) {
        field.setPassable(false);
        field.edges.forEach(RecursiveBackTrackerAlgorithm::setBlocked);
    }

    @SuppressWarnings("rawtypes")
    private static <F extends Field<E, N>, E extends Edge<F, N>, N extends Node> void setBlocked(E edge) {
        edge.setPassable(false);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    private static <F extends Field<E, N>, E extends Edge, N extends Node> void closeMapBorders(Map<F, E, N> map, Set<F> closed) {
        List<F> borderFields = map.fields.getBorders();
        System.out.println("size of borderFields: " + borderFields.size());
        addToClosed(borderFields, closed);
    }

    @SuppressWarnings("rawtypes")
    private static <F extends Field<E, N>, E extends Edge<F, N>, N extends Node> void addToClosed(List<F> fields, Set<F> closed) {
        closed.addAll(fields);
    }
}
