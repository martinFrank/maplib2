package com.github.martinfrank.maplib2.demoapp.geo;


import java.util.Objects;

public class DiscreetPoint {

    public final int x;
    public final int y;

    public DiscreetPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public DiscreetPoint(DiscreetPoint sample) {
        this(sample.x, sample.y);
    }
    public DiscreetPoint(FloatingPoint sample) {
        this((int) sample.x, (int) sample.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscreetPoint point = (DiscreetPoint) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }


}
