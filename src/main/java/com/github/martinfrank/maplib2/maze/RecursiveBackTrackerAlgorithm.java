package com.github.martinfrank.maplib2.maze;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecursiveBackTrackerAlgorithm {

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node> void createMaze(Map<F, E, N> map) {
        Deque<F> backTrackerStack = new ArrayDeque<>();
        Set<F> closed = new HashSet<>();
        closeAllPassage(map);
        closeMapBorders(map, closed);

        F current = map.fields.getRandomStart();
        current.setPassable(true);

        do {
            List<F> nbgs = getCarvingCandidates(current, closed);
            if (nbgs.isEmpty()) {
                current = backTrackerStack.pop();
            } else {
                F next = nbgs.get(0);
                carveInto(current, next);
                backTrackerStack.push(current);
                current = next;
                closed.add(current);
            }
        } while (!backTrackerStack.isEmpty());
    }

    private static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F,E>> List<F> getCarvingCandidates(F fields, Set<F> closed) {
        List<F> candidates = fields.fields.stream().filter(f -> fields.isPassable() ).filter(f -> !closed.contains(f)).collect(Collectors.toList());
//        candidates.removeAll(closed);
        Collections.shuffle(candidates);
        return candidates;
    }

    private static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F,E>> void carveInto(F current, F next) {
        E edge = current.getEdge(next);
        edge.setPassable(true);
        next.setPassable(true);
    }

    private static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F,E>> void closeAllPassage(Map<F, E, N> map ) {
        map.fields.stream().forEach(RecursiveBackTrackerAlgorithm::closePassage);
    }

    private static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F,E>> void closePassage(F field) {
        field.setPassable(false);
        field.edges.forEach(RecursiveBackTrackerAlgorithm::closePassage);
    }

    private static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F,E>> void closePassage(E edge) {
        edge.setPassable(false);
    }

    @SuppressWarnings({"unchecked","rawtypes"})
    private static <F extends Field<F, E, N>, E extends Edge, N extends Node<F,E>> void closeMapBorders(Map<F, E, N> map, Set<F> closed) {
        List<F> borderFields = map.fields.getBorders();
        System.out.println("size of borderFields: " + borderFields.size());
        addToClosed(borderFields, closed);
        borderFields.forEach(b -> b.setPassable(false));
    }

    private static <F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F,E>> void addToClosed(List<F> fields, Set<F> closed) {
        closed.addAll(fields);
    }
}
