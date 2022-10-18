package com.github.martinfrank.maplib2.astar;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Node;

public class Walker <F extends Field, E extends Edge, N extends Node>{

    public boolean canEnter(F field) {
        return field.isPassable();
    }

    public <F extends Field<F, ?, ?>> int getEnterCosts(AStarFieldWrapper<F> node) {
        return 10;
    }
}
