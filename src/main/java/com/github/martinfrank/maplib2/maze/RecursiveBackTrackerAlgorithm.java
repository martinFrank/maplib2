package com.github.martinfrank.maplib2.maze;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecursiveBackTrackerAlgorithm<F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F, E>> {

    private final boolean isFieldForm; //verses edge form

    @SuppressWarnings("rawtypes")
    public static RecursiveBackTrackerAlgorithm FIELDS = new RecursiveBackTrackerAlgorithm<>(true);

    @SuppressWarnings("rawtypes")
    public static RecursiveBackTrackerAlgorithm EDGES = new RecursiveBackTrackerAlgorithm<>(false);

    private RecursiveBackTrackerAlgorithm(boolean isFieldForm) {
        this.isFieldForm = isFieldForm;
    }

    public void createMaze(Map<F, E, N> map) {
        Deque<F> backTrackerStack = new ArrayDeque<>();
        Set<F> closed = new HashSet<>();
        closeAllPassage(map);
        List<F> borderFields = map.getBorders();
        if (isFieldForm) {
            closeMapBorders(borderFields, closed);
        }

        F current = map.getRandomFieldWithinBorders();
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

    private List<F> getCarvingCandidates(F field, Set<F> closed) {
        List<F> candidates = field.fields.stream() //no NPE here, I know better
                .filter(f -> !closed.contains(f)).collect(Collectors.toList());
        if (isFieldForm) {
            removeUnqualified(field, candidates);
        }
        Collections.shuffle(candidates);
        return candidates;
    }

    private void removeUnqualified(F start, List<F> candidates) {
        List<F> unqualified = candidates.stream()
                .filter(can -> hasPassableNeighbours(can, start))
                .collect(Collectors.toList());
        candidates.removeAll(unqualified);
    }

    private boolean hasPassableNeighbours(F candidate, F start) {
        List<F> neighbours = new ArrayList<>(candidate.fields);//no NPE here, I know better
        neighbours.remove(start);
        return neighbours.stream().anyMatch(Field::isPassable);
    }

    private void carveInto(F current, F next) {
        E edge = current.getEdge(next);
        edge.setPassable(true);
        next.setPassable(true);
    }

    //visible for testing
    List<F> getFieldsByNode(F field) {
        return field.fields.stream().flatMap(f -> f.fields.stream()).distinct().collect(Collectors.toList());//no NPE here, I know better
    }

    private void closeAllPassage(Map<F, E, N> map) {
        map.fields().forEach(this::closePassage);
    }

    private void closePassage(F field) {
        field.setPassable(false);
        field.edges.forEach(this::closePassage);
    }

    private void closePassage(E edge) {
        edge.setPassable(false);
    }

    private void closeMapBorders(List<F> borderFields, Set<F> closed) {
        addToClosed(borderFields, closed);
        borderFields.forEach(b -> b.setPassable(false));
    }

    private void addToClosed(List<F> fields, Set<F> closed) {
        closed.addAll(fields);
    }
}
