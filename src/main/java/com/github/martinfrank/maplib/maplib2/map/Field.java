package com.github.martinfrank.maplib.maplib2.map;

import com.github.martinfrank.maplib.maplib2.geo.DiscreetPoint;
import com.github.martinfrank.maplib.maplib2.geo.Polygon;

import java.util.Collections;
import java.util.List;

public class Field {

    public final DiscreetPoint position;
    public final List<Node> nodes;
    public final List<Edge> edges;
    public final List<Field> fields = null;
    public final Node center;
    public final Polygon polygon;

    private boolean isPassable;


    private Field(DiscreetPoint position, List<Node> nodes, List<Edge> edges, Node center) {
        this.position = position;
        this.nodes = Collections.unmodifiableList(nodes);
        this.edges = Collections.unmodifiableList(edges);
        this.center = center;
        this.polygon = new Polygon(nodes);
    }

    public Field(Field template) {
        this(template.position, template.nodes, template.edges, template.center);
    }

    public void setPassable(boolean isPassable) {
        this.isPassable = isPassable;
    }

    public boolean isPassable() {
        return isPassable;
    }


    public Edge getEdge(Field other) {
        return edges.stream().filter(e -> equalFields(this, other, e)).findAny().orElse(null);

    }

    private static <F extends Field> boolean equalFields(F that, F other, Edge edge){
        if (edge.fieldA == null || edge.fieldB == null){//false positive, I know better
            return false;
        }
        return edge.fieldA.equals(that) && edge.fieldB.equals(other) || edge.fieldB.equals(that) && edge.fieldA.equals(other);
    }

    @SuppressWarnings("unchecked")
    public Field getField(Edge edge) {
        if(this.equals(edge.fieldA)){//false positive, I know better
            return edge.fieldB;
        }
        if(this.equals(edge.fieldB)){//false positive, I know better
            return edge.fieldA;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Node.center="+position.toString();
    }
}
