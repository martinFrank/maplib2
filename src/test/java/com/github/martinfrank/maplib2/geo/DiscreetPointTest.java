package com.github.martinfrank.maplib2.geo;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DiscreetPointTest {

    @Test
    public void testEquals(){
        DiscreetPoint p1 = new DiscreetPoint(1,1);
        DiscreetPoint p2 = new DiscreetPoint(1,1);

        Assert.assertEquals(p1,p2);
    }

    @Test
    public void testIsNear(){
        FloatingPoint p1 = new FloatingPoint(1,1);
        FloatingPoint p2 = new FloatingPoint(1.09,1);

        Assert.assertTrue(p1.isNear(p2, 0.1));
    }

    @Test
    public void testEqualLocation() {
        DiscreetPoint origin = new DiscreetPoint(0,0);
        DiscreetPoint origin2 = new DiscreetPoint(0, 0);
        assertEquals(origin, origin2);

        DiscreetPoint p1 = new DiscreetPoint(2, 2);
        DiscreetPoint p2 = new DiscreetPoint(2, 2);
        assertEquals(p1, p2);
    }

}