package com.github.martinfrank.maplib2.generate.delaunay;

import java.text.DecimalFormat;

public class DoubleFormat {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");

    public static String format(double d) {
        return DECIMAL_FORMAT.format(d);
    }
}
