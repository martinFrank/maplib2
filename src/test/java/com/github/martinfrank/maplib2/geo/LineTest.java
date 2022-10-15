package com.github.martinfrank.maplib2.geo;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LineTest {

    @Test
    public void lineTest() {
        Point a = new Point(0, 0);
        Point b = new Point(3, 3);

        List<Point> line = Line.getLine(a, b);
        Assert.assertEquals(4, line.size());

        Assert.assertTrue(line.contains(new Point(0, 0)));
        Assert.assertTrue(line.contains(new Point(1, 1)));
        Assert.assertTrue(line.contains(new Point(2, 2)));
        Assert.assertTrue(line.contains(new Point(3, 3)));
    }

    @Test
    public void lineTest2() {
        Point a = new Point(0, 0);
        Point b = new Point(5, 2);

        List<Point> line = Line.getLine(a, b);
        Assert.assertEquals(6, line.size());

        Assert.assertTrue(line.contains(new Point(0, 0)));
        Assert.assertTrue(line.contains(new Point(1, 0)));
        Assert.assertTrue(line.contains(new Point(2, 1)));
        Assert.assertTrue(line.contains(new Point(3, 1)));
        Assert.assertTrue(line.contains(new Point(4, 2)));
        Assert.assertTrue(line.contains(new Point(5, 2)));
    }
}
