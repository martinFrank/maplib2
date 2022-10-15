package com.github.martinfrank.maplib2.geo;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PointTest {

    @Test
    public void testEquals(){
        Point p1 = new Point(1,1);
        Point p2 = new Point(1,1);

        Assert.assertEquals(p1,p2);
    }

    @Test
    public void testIsNear(){
        Point p1 = new Point(1,1);
        Point p2 = new Point(1.09,1);

        Assert.assertTrue(p1.isNear(p2, 0.1));
    }

    @Test
    public void testEqualLocation() {
        Point origin = new Point(0,0);
        Point origin2 = new Point(0, 0);
        assertEquals(origin, origin2);

        Point p1 = new Point(2, 2);
        Point p2 = new Point(2, 2);
        assertEquals(p1, p2);
    }

}