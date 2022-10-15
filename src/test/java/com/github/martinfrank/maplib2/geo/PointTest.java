package com.github.martinfrank.maplib2.geo;

import org.junit.Assert;
import org.junit.Test;

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

}