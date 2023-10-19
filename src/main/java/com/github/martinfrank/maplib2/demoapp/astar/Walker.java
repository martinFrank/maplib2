package com.github.martinfrank.maplib2.demoapp.astar;

import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Field;

@SuppressWarnings("rawtypes")
public class Walker <F extends Field<F,E,?>, E extends Edge>{

    public boolean canEnter(F from, F to) {
        E edge = from.getEdge(to);
        return to.isPassable() && edge.isPassable();
    }

    public double getEnterCosts(F from, F to) {
        return 10;
    }

    public double getMaximumSearchDepth(){
        return 500d;
    }
}
