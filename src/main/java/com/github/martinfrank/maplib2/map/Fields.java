package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@SuppressWarnings("rawtypes")
public class Fields<F extends Field, E extends Edge, N extends Node> {

    private final List<F> internalFields;

    public Fields(List<F> fields) {
        internalFields = Collections.unmodifiableList(fields);
    }

    public F getField(double x, double y) {
        Point position = new Point(x, y);
        return internalFields.stream().filter(f -> f.position.equals(position)).findAny().orElse(null);
    }

    public int size() {
        return internalFields.size();
    }

    public F get(int i) {
        return internalFields.get(i);
    }

    public Stream<F> stream(){
        return internalFields.stream();
    }

    @SuppressWarnings("unchecked")
    public List<F> getBorders() {
        List<F> borderFields = new ArrayList<>();
        for(F f: internalFields){
            List<E> edges = f.edges;
            for(E edge: edges){
                if (edge.fieldA == null || edge.fieldB == null){//I know better!!!
                    borderFields.add(f);
                    break;
                }
            }
        }
        return borderFields;
    }

    public F getRandomStart() {
        List<F> candidates = new ArrayList<>(internalFields);
        candidates.removeAll(getBorders());
        Collections.shuffle(candidates);
        return candidates.get(0);
    }

    public List<F> getAll() {
        return internalFields;
    }
}
