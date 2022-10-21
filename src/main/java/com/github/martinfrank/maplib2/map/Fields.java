package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.FloatingPoint;
import com.github.martinfrank.maplib2.geo.Line;
import com.github.martinfrank.maplib2.geo.DiscreetPoint;

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

    public F getField(int x, int y) {
        DiscreetPoint position = new DiscreetPoint(x, y);
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

    public F getRandomFieldWithinBorders() {
        List<F> candidates = new ArrayList<>(internalFields);
        candidates.removeAll(getBorders());
        Collections.shuffle(candidates);
        return candidates.get(0);
    }

    public List<F> getAll() {
        return internalFields;
    }

    public F getFieldOnScreen(FloatingPoint point, double scale, double catchRadius) {
        for(F field: internalFields){
            FloatingPoint center = field.center.polygon.getScaled(scale).get(0);
            if(Line.distance(point,center) < catchRadius){
                return field;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public E getEdgeOnScreen(FloatingPoint point, double scale, int catchRadius) {
        for(F field: internalFields){
            for(Object edgeObject: field.edges) {
                E edge = (E) edgeObject;
                FloatingPoint center = edge.center.polygon.getScaled(scale).get(0);
                if (Line.distance(point, center) < catchRadius) {
                    return edge;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public N getNodeOnScreen(FloatingPoint point, double scale, int catchRadius) {
        for(F field: internalFields){
            for(Object nodeObject: field.nodes) {
                N node = (N) nodeObject;
                FloatingPoint center = node.polygon.getScaled(scale).get(0);
                if (Line.distance(point, center) < catchRadius) {
                    return node;
                }
            }
        }
        return null;
    }

    public double getWidth() {
        double width = 0;
        for(F field: internalFields){
            for(Object nodeObject: field.nodes) {
                N node = (N) nodeObject;
                if (node.x > width){
                    width = node.x;
                }
            }
        }
        return width;
    }

    public double getHeight() {
        double height = 0;
        for(F field: internalFields){
            for(Object nodeObject: field.nodes) {
                N node = (N) nodeObject;
                if (node.y > height){
                    height = node.y;
                }
            }
        }
        return height;
    }
}
