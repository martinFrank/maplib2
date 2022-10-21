package com.github.martinfrank.maplib2.astar;

import java.util.Objects;

public class AStarFieldWrapper <F> {

    public final F field;
    private double g;
    private double h;

    private AStarFieldWrapper<F> from;

    public AStarFieldWrapper(F field){
        this.field = field;
    }

    public double getF() {
        return g + h;
    }



    public double getG() {
        return g;
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

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public AStarFieldWrapper<F> getFrom(){
        return from;
    }
}
