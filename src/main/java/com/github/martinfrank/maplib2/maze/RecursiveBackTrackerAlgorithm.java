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

public class RecursiveBackTrackerAlgorithm<F extends Field<F, E, N>, E extends Edge<F, E, N>, N extends Node<F,E>> {

    private final boolean isFieldForm;

    @SuppressWarnings("rawtypes")
    public static RecursiveBackTrackerAlgorithm FIELDS = new RecursiveBackTrackerAlgorithm<>(true);

    @SuppressWarnings("rawtypes")
    public static RecursiveBackTrackerAlgorithm EDGES = new RecursiveBackTrackerAlgorithm<>(false);

    private RecursiveBackTrackerAlgorithm(boolean isFieldForm){
        this.isFieldForm = isFieldForm;
    }

    public void createMaze(Map<F, E, N> map) {
        Deque<F> backTrackerStack = new ArrayDeque<>();
        Set<F> closed = new HashSet<>();
        closeAllPassage(map);
        if(isFieldForm){
            closeMapBorders(map, closed);
        }

        F current = map.fields.getRandomStart();
        current.setPassable(true);

        do {
            List<F> nbgs = getCarvingCandidates(current, closed);
            if (nbgs.isEmpty()) {
                current = backTrackerStack.pop();
            } else {
                F next = nbgs.get(0);
                carveInto(current, next, closed);
                backTrackerStack.push(current);
                current = next;
                closed.add(current);
            }
        } while (!backTrackerStack.isEmpty());
    }

    private List<F> getCarvingCandidates(F field, Set<F> closed) {
        List<F> candidates = field.fields.stream().filter(f -> field.isPassable() ).filter(f -> !closed.contains(f)).collect(Collectors.toList());
        if(isFieldForm){
            List<F> unqualified = new ArrayList<>();
            for(F candidate: candidates){
                int openFieldNbgs = countOpenFields(candidate);
                if (openFieldNbgs > 1){
                    unqualified.add(candidate);
                }
            }
            candidates.removeAll(unqualified);
        }

//        closed.addAll(unqualified);

        Collections.shuffle(candidates);
        return candidates;
    }

    private int countOpenFields(F field) {
        int numberOfOpen = 0;
        for(E edge: field.edges){
            Field opposite = field.getField(edge);
            if(opposite.isPassable()){
                numberOfOpen = numberOfOpen+1;
            }
        }
        return numberOfOpen;
    }

    private void carveInto(F current, F next, Set<F> closed) {
        E edge = current.getEdge(next);
        edge.setPassable(true);
        next.setPassable(true);
    }

    private void closeAllPassage(Map<F, E, N> map ) {
        map.fields.stream().forEach(this::closePassage);
    }

    private void closePassage(F field) {
        field.setPassable(false);
        field.edges.forEach(this::closePassage);
    }

    private void closePassage(E edge) {
        edge.setPassable(false);
    }

    private void closeMapBorders(Map<F, E, N> map, Set<F> closed) {
        List<F> borderFields = map.fields.getBorders();
        addToClosed(borderFields, closed);
        borderFields.forEach(b -> b.setPassable(false));
    }

    private void addToClosed(List<F> fields, Set<F> closed) {
        closed.addAll(fields);
    }
}
