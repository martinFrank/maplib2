package com.github.martinfrank.maplib.maplib2.astar;

import com.github.martinfrank.maplib.maplib2.map.Edge;
import com.github.martinfrank.maplib.maplib2.map.Field;

public class Walker {

    public boolean canEnter(Field from, Field to) {
        Edge edge = from.getEdge(to);
        return to.isPassable() && edge.isPassable();
    }

    public double getEnterCosts(Field from, Field to) {
        return 10;
    }

    public double getMaximumSearchDepth(){
        return 500d;
    }
}
