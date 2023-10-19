package com.github.martinfrank.maplib2.demoapp.maze;

import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Field;
import com.github.martinfrank.maplib2.demoapp.map.Map;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RecursiveBackTrackerAlgorithm {

    private final boolean isFieldForm; //verses edge form

    public static final RecursiveBackTrackerAlgorithm FIELDS = new RecursiveBackTrackerAlgorithm(true);

    public static final RecursiveBackTrackerAlgorithm EDGES = new RecursiveBackTrackerAlgorithm(false);

    private RecursiveBackTrackerAlgorithm(boolean isFieldForm) {
        this.isFieldForm = isFieldForm;
    }

    public void createMaze(Map map) {
        Deque<Field> backTrackerStack = new ArrayDeque<>();
        Set<Field> closed = new HashSet<>();
        closeAllPassage(map);
        List<Field> borderFields = map.getBorders();
        if (isFieldForm) {
            closeMapBorders(borderFields, closed);
        }

        Field current = map.getRandomFieldWithinBorders();
        current.setPassable(true);

        do {
            List<Field> nbgs = getCarvingCandidates(current, closed);
            if (nbgs.isEmpty()) {
                current = backTrackerStack.pop();
            } else {
                Field next = nbgs.get(0);
                carveInto(current, next);
                backTrackerStack.push(current);
                current = next;
                closed.add(current);
            }
        } while (!backTrackerStack.isEmpty());
    }

    private List<Field> getCarvingCandidates(Field field, Set<Field> closed) {
        List<Field> candidates = field.fields.stream() //no NPE here, I know better
                .filter(f -> !closed.contains(f)).collect(Collectors.toList());
        if (isFieldForm) {
            removeUnqualified(field, candidates);
        }
        Collections.shuffle(candidates);
        return candidates;
    }

    private void removeUnqualified(Field start, List<Field> candidates) {
        List<Field> unqualified = candidates.stream()
                .filter(can -> hasPassableNeighbours(can, start))
                .toList();
        candidates.removeAll(unqualified);
    }

    private boolean hasPassableNeighbours(Field candidate, Field start) {
        List<Field> neighbours = new ArrayList<>(candidate.fields);//no NPE here, I know better
        neighbours.remove(start);
        return neighbours.stream().anyMatch(Field::isPassable);
    }

    private void carveInto(Field current, Field next) {
        Edge edge = current.getEdge(next);
        edge.setPassable(true);
        next.setPassable(true);
    }

    //visible for testing
    List<Field> getFieldsByNode(Field field) {
        return field.fields.stream().flatMap(f -> f.fields.stream()).distinct().toList();//no NPE here, I know better
    }

    private void closeAllPassage(Map map) {
        map.fields().forEach(this::closePassage);
    }

    private void closePassage(Field field) {
        field.setPassable(false);
        field.edges.forEach(this::closePassage);
    }

    private void closePassage(Edge edge) {
        edge.setPassable(false);
    }

    private void closeMapBorders(List<Field> borderFields, Set<Field> closed) {
        addToClosed(borderFields, closed);
        borderFields.forEach(b -> b.setPassable(false));
    }

    private void addToClosed(List<Field> fields, Set<Field> closed) {
        closed.addAll(fields);
    }
}
