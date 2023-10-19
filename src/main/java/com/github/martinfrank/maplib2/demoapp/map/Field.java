package com.github.martinfrank.maplib2.demoapp.map;

import com.github.martinfrank.maplib2.demoapp.geo.DiscreetPoint;
import com.github.martinfrank.maplib2.demoapp.geo.Polygon;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("rawtypes")
public class Field<THIS, E extends Edge, N extends Node> {

    public final DiscreetPoint position;
    public final List<N> nodes;
    public final List<E> edges;
    public final List<THIS> fields = null;
    public final N center;
    public final Polygon polygon;

    private boolean isPassable;


    private Field(DiscreetPoint position, List<N> nodes, List<E> edges, N center) {
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
        return edges.stream().filter(e -> equalFields(this, other, e)).findAny().orElse(null);

    }

    private static <F extends Field> boolean equalFields(F that, F other, Edge edge){
        if (edge.fieldA == null || edge.fieldB == null){//false positive, I know better
            return false;
        }
        return edge.fieldA.equals(that) && edge.fieldB.equals(other) || edge.fieldB.equals(that) && edge.fieldA.equals(other);
    }

    @SuppressWarnings("unchecked")
    public THIS getField(E edge) {
        if(this.equals(edge.fieldA)){//false positive, I know better
            return (THIS) edge.fieldB;
        }
        if(this.equals(edge.fieldB)){//false positive, I know better
            return (THIS) edge.fieldA;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Node.center="+position.toString();
    }
}
