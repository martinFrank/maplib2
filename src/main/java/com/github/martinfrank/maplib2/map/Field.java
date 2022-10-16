package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Point;
import com.github.martinfrank.maplib2.geo.Polygon;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("rawtypes")
public class Field<THIS, E extends Edge, N extends Node> {

    public final Point position;
    public final List<N> nodes;
    public final List<E> edges;
    public final List<THIS> fields = null;
    public final N center;
    public Polygon polygon;

    private boolean isPassable;


    private Field(Point position, List<N> nodes, List<E> edges, N center) {
        this.position = position;
        this.nodes = Collections.unmodifiableList(nodes);
        this.edges = Collections.unmodifiableList(edges);
        this.center = center;
        this.polygon = new Polygon(nodes);
    }

    @SuppressWarnings("CopyConstructorMissesField")//positiveFalse: it DOES copy all fields!
    public Field(Field<THIS, E, N> template) {
        this(template.position, template.nodes, template.edges, template.center);
    }


    public void setPassable(boolean isPassable) {
        this.isPassable = isPassable;
    }

    public boolean isPassable() {
        return isPassable;
    }


    public E getEdge(Field other) {
//        Optional candidate = edges.stream().filter(e -> e.fieldA.equals(this) && e.fieldB.equals(other) || e.fieldB.equals(this) && e.fieldA.equals(other)).findAny();
//        if(candidate.isPresent()){
//            return (E)candidate.get();
//        }
//        return null;
        return edges.stream().filter(e -> equalFields(this, other, e)).findAny().orElse(null);

    }

    private static <F extends Field> boolean equalFields(F that, F other, Edge edge){
        if (edge.fieldA == null || edge.fieldB == null){
            return false;
        }
        return edge.fieldA.equals(that) && edge.fieldB.equals(other) || edge.fieldB.equals(that) && edge.fieldA.equals(other);
    }
}
