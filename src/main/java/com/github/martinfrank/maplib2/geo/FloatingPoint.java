package com.github.martinfrank.maplib2.geo;

import java.util.Objects;

public class FloatingPoint {

    public final double x;
    public final double y;

    public FloatingPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public FloatingPoint(FloatingPoint sample) {
        this(sample.x, sample.y);
    }
    public FloatingPoint(DiscreetPoint sample) {
        this(sample.x, sample.y);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloatingPoint point = (FloatingPoint) o;
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

    public boolean isNear(FloatingPoint p, double distance){
        if (p == null || distance < 0) {
            return false;
        }
        double dx = p.x - x;
        double dy = p.y - y;
        double pythagoras = Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return pythagoras < distance;
    }

}
