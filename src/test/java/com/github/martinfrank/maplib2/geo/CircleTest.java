package com.github.martinfrank.maplib2.geo;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CircleTest {

    @Test
    public void circleTest() {
        DiscreetPoint center = new DiscreetPoint(0,0);
        List<DiscreetPoint> circle = Circle.getCircle(center, 2);

        for (DiscreetPoint point : circle) {
            System.out.println(point);
        }

        Assert.assertTrue(circle.contains(new DiscreetPoint(0, 2)));
        Assert.assertTrue(circle.contains(new DiscreetPoint(2, 0)));
        Assert.assertTrue(circle.contains(new DiscreetPoint(0, -2)));
        Assert.assertTrue(circle.contains(new DiscreetPoint(-2, 0)));

    }
}
