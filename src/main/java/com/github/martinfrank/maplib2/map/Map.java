package com.github.martinfrank.maplib2.map;

import com.github.martinfrank.maplib2.geo.Polygon;

public class Map<F extends Field<E,N>, E extends Edge<N>, N extends Node> {
    public final Fields<F, E, N> fields;

//    public final Polygon polygon;

    private Map(Fields<F, E, N> fields){
        this.fields = fields;
//        polygon = new Polygon(fields.);
    }

    public Map(Map<F,E,N> sample){
        this(sample.fields);
    }


}
