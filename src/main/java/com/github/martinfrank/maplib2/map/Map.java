package com.github.martinfrank.maplib2.map;

public class Map<F extends Field, E extends Edge, N extends Node> {

    public final Fields<F, E, N> fields;

    private Map(Fields<F, E, N> fields){
        this.fields = fields;
    }

    public Map(Map<F,E,N> sample){
        this(sample.fields);
    }


}
