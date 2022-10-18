package com.github.martinfrank.maplib2.astar;

import java.util.Objects;

public class AStarFieldWrapper <F> {

    public final F field;

//    private int f;
    private int goDistance;
    private int heuristicDistance;

    private AStarFieldWrapper<F> from;

    public AStarFieldWrapper(F field){
        this.field = field;
    }

    public int getEstimate() {
        return goDistance + heuristicDistance;
    }

    public int getGoDistance() {
        return goDistance;
    }

    public void setFrom(AStarFieldWrapper<F> from){
        this.from = from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AStarFieldWrapper<?> that = (AStarFieldWrapper<?>) o;
        return field.equals(that.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }
}
