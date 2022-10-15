package com.github.martinfrank.maplib2.geo;

import org.junit.Assert;
import org.junit.Test;

public class CircleComparatorTest {

    @Test
    public void testComparator() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);

        CirclePointComparator cc = new CirclePointComparator();
        Assert.assertEquals(1, cc.compare(p1, p2));
    }

    @Test
    public void testPolarPoints() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point center = new Point(0,0);
        CirclePointComparator.GlPolarPoint pp1 = new CirclePointComparator().new GlPolarPoint(p1, center);
        CirclePointComparator.GlPolarPoint pp2 = new CirclePointComparator().new GlPolarPoint(p2, center);

        Assert.assertNotEquals(pp1, pp2);
        Assert.assertNotEquals(pp1, null);
        Assert.assertNotEquals(pp1, "hello");
        Assert.assertNotEquals(pp1.hashCode(), pp2.hashCode());
        Assert.assertEquals(pp1, pp1);

    }
}
