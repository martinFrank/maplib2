package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;

import java.util.Collections;
import java.util.List;

public class Fields<F extends Field<E, N>, E extends Edge<N>, N extends Node> {

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

    public List<F> getAll() {
        return internalFields;
    }
}
