package com.github.martinfrank.maplib.maplib2.geo;
 

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CircleTest {

    @Test
    public void circleTest() {
        DiscreetPoint center = new DiscreetPoint(0,0);
        List<DiscreetPoint> circle = Circle.getCircle(center, 2);

        for (DiscreetPoint point : circle) {
            System.out.println(point);
        }

        assertTrue(circle.contains(new DiscreetPoint(0, 2)));
        assertTrue(circle.contains(new DiscreetPoint(2, 0)));
        assertTrue(circle.contains(new DiscreetPoint(0, -2)));
        assertTrue(circle.contains(new DiscreetPoint(-2, 0)));

    }
}
