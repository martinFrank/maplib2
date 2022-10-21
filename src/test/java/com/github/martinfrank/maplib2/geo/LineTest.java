package com.github.martinfrank.maplib2.geo;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LineTest {

    @Test
    public void lineTest() {
        DiscreetPoint a = new DiscreetPoint(0, 0);
        DiscreetPoint b = new DiscreetPoint(3, 3);

        List<DiscreetPoint> line = Line.getLine(a, b);
        Assert.assertEquals(4, line.size());

        Assert.assertTrue(line.contains(new DiscreetPoint(0, 0)));
        Assert.assertTrue(line.contains(new DiscreetPoint(1, 1)));
        Assert.assertTrue(line.contains(new DiscreetPoint(2, 2)));
        Assert.assertTrue(line.contains(new DiscreetPoint(3, 3)));
    }

    @Test
    public void lineTest2() {
        DiscreetPoint a = new DiscreetPoint(0, 0);
        DiscreetPoint b = new DiscreetPoint(5, 2);

        List<DiscreetPoint> line = Line.getLine(a, b);
        Assert.assertEquals(6, line.size());

        Assert.assertTrue(line.contains(new DiscreetPoint(0, 0)));
        Assert.assertTrue(line.contains(new DiscreetPoint(1, 0)));
        Assert.assertTrue(line.contains(new DiscreetPoint(2, 1)));
        Assert.assertTrue(line.contains(new DiscreetPoint(3, 1)));
        Assert.assertTrue(line.contains(new DiscreetPoint(4, 2)));
        Assert.assertTrue(line.contains(new DiscreetPoint(5, 2)));
    }
}
