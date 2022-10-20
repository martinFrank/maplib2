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

public class RecursiveBackTrackerAlgorithm<F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F, E>> {

    private final boolean isFieldForm;

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
        List<F> borderFields = map.fields.getBorders();
        if (isFieldForm) {
            closeMapBorders(borderFields, closed);
        }

        int count = 1;
        F current = map.fields.getRandomFieldWithinBorders();
        current.setPassable(true);
        current.count = count;

        do {
            List<F> nbgs = getCarvingCandidates(current, closed, borderFields);
            if (nbgs.isEmpty()) {
                current = backTrackerStack.pop();
            } else {
                F next = nbgs.get(0);
                carveInto(current, next);
                backTrackerStack.push(current);
                current = next;
                count++;
                current.count = count;
                closed.add(current);
            }
        } while (!backTrackerStack.isEmpty());
    }

    private List<F> getCarvingCandidates(F field, Set<F> closed, List<F> borderFields) {
        List<F> candidates = field.fields.stream()
                .filter(f -> !closed.contains(f)).collect(Collectors.toList());
        if (isFieldForm) {
            List<F> unqualified = findUnqualified(field, candidates, borderFields);
            candidates.removeAll(unqualified);
        }


        Collections.shuffle(candidates);
        return candidates;
    }

    private List<F> findUnqualified(F start, List<F> candidates,List<F> borders) {
        List<F> unqualified = new ArrayList<>();
        for(F candidate: candidates){
            List<F> nbgs = new ArrayList<>(candidate.fields);
            nbgs.remove(start);
            for(F nbg: nbgs){
                if (nbg.isPassable()){
                    unqualified.add(candidate);
                    break;
                }
            }
        }
        return unqualified;
    }

    //visible for testing
    List<F> getFieldsByNode(F field) {
        Set<F> fields = new HashSet<>();
        for (N node : field.nodes) {
            fields.addAll(node.fields);
        }
        return new ArrayList<>(fields);
    }

    private void carveInto(F current, F next) {
        E edge = current.getEdge(next);
        edge.setPassable(true);
        next.setPassable(true);
    }

    private void closeAllPassage(Map<F, E, N> map) {
        map.fields.stream().forEach(this::closePassage);
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
