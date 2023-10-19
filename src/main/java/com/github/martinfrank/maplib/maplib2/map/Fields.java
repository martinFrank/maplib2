package com.github.martinfrank.maplib.maplib2.map;

import com.github.martinfrank.maplib.maplib2.geo.FloatingPoint;
import com.github.martinfrank.maplib.maplib2.geo.Line;
import com.github.martinfrank.maplib.maplib2.geo.DiscreetPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Fields {

    private final List<Field> internalFields;

    public Fields(List<Field> fields) {
        internalFields = Collections.unmodifiableList(fields);
    }

    public Field getField(int x, int y) {
        DiscreetPoint position = new DiscreetPoint(x, y);
        return internalFields.stream().filter(f -> f.position.equals(position)).findAny().orElse(null);
    }

    public int size() {
        return internalFields.size();
    }

    public Field get(int i) {
        return internalFields.get(i);
    }

    public Stream<Field> stream(){
        return internalFields.stream();
    }

    public List<Field> getBorders() {
        List<Field> borderFields = new ArrayList<>();
        for(Field f: internalFields){
            List<Edge> edges = f.edges;
            for(Edge edge: edges){
                if (edge.fieldA == null || edge.fieldB == null){//I know better!!!
                    borderFields.add(f);
                    break;
                }
            }
        }
        return borderFields;
    }

    public Field getRandomFieldWithinBorders() {
        List<Field> candidates = new ArrayList<>(internalFields);
        candidates.removeAll(getBorders());
        Collections.shuffle(candidates);
        return candidates.get(0);
    }

    public List<Field> getAll() {
        return internalFields;
    }

    public Field getFieldOnScreen(FloatingPoint point, double scale, double catchRadius) {
        for(Field field: internalFields){
            FloatingPoint center = field.center.polygon.getScaled(scale).get(0);
            if(Line.distance(point,center) < catchRadius){
                return field;
            }
        }
        return null;
    }

    public Edge getEdgeOnScreen(FloatingPoint point, double scale, int catchRadius) {
        for(Field field: internalFields){
            for(Edge edge: field.edges) {
                FloatingPoint center = edge.center.polygon.getScaled(scale).get(0);
                if (Line.distance(point, center) < catchRadius) {
                    return edge;
                }
            }
        }
        return null;
    }

    public Node getNodeOnScreen(FloatingPoint point, double scale, int catchRadius) {
        for(Field field: internalFields){
            for(Node node: field.nodes) {
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
        for(Field field: internalFields){
            for(Node node: field.nodes) {
                if (node.x > width){
                    width = node.x;
                }
            }
        }
        return width;
    }

    public double getHeight() {
        double height = 0;
        for(Field field: internalFields){
            for(Node node: field.nodes) {
                if (node.y > height){
                    height = node.y;
                }
            }
        }
        return height;
    }
}
