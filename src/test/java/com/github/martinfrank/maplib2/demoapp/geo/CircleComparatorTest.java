package com.github.martinfrank.maplib2.demoapp.geo;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CircleComparatorTest {

    @Test
    public void testComparator() {
        DiscreetPoint p1 = new DiscreetPoint(1, 1);
        DiscreetPoint p2 = new DiscreetPoint(2, 2);

        CirclePointComparator cc = new CirclePointComparator();
        assertEquals(1, cc.compare(p1, p2));
    }

    @Test
    @SuppressWarnings("squid:S586")
    public void testPolarPoints() {
        DiscreetPoint p1 = new DiscreetPoint(1, 1);
        DiscreetPoint p2 = new DiscreetPoint(2, 2);
        DiscreetPoint center = new DiscreetPoint(0,0);
        CirclePointComparator.GlPolarPoint pp1 = new CirclePointComparator().new GlPolarPoint(p1, center);
        CirclePointComparator.GlPolarPoint pp2 = new CirclePointComparator().new GlPolarPoint(p2, center);

        assertNotEquals(pp1, pp2);
        assertNotEquals(null, pp1);
        assertNotEquals("hello", pp1);
        assertNotEquals(pp1.hashCode(), pp2.hashCode());
        assertEquals(pp1, pp1);

    }
}
