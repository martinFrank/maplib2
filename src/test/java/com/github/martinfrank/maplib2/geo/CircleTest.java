package com.github.martinfrank.maplib2.geo;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CircleTest {

    @Test
    public void circleTest() {
        Point center = new Point(0,0);
        List<Point> circle = Circle.getCircle(center, 2);

        for (Point point : circle) {
            System.out.println(point);
        }

        Assert.assertTrue(circle.contains(new Point(0, 2)));
        Assert.assertTrue(circle.contains(new Point(2, 0)));
        Assert.assertTrue(circle.contains(new Point(0, -2)));
        Assert.assertTrue(circle.contains(new Point(-2, 0)));

    }
}
