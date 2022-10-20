package com.github.martinfrank.maplib2.generate.delaunay;

public class DoubleApproximity {

    private static final double defaultEpsilon = 0.00001;

    public static boolean isApproximatelyEqual(double a, double b) {
        return isApproximatelyEqual(a, b, defaultEpsilon);
    }

    public static boolean isApproximatelyEqual(double a, double b, double defaultEpsilon) {
        return Math.abs(a - b) < Math.abs(a * defaultEpsilon);
    }
}
