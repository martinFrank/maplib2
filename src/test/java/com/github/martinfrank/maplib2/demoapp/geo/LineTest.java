package com.github.martinfrank.maplib2.demoapp.geo;
 

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LineTest {

    @Test
    public void lineTest() {
        DiscreetPoint a = new DiscreetPoint(0, 0);
        DiscreetPoint b = new DiscreetPoint(3, 3);

        List<DiscreetPoint> line = Line.getLine(a, b);
        assertEquals(4, line.size());

        assertTrue(line.contains(new DiscreetPoint(0, 0)));
        assertTrue(line.contains(new DiscreetPoint(1, 1)));
        assertTrue(line.contains(new DiscreetPoint(2, 2)));
        assertTrue(line.contains(new DiscreetPoint(3, 3)));
    }

    @Test
    public void lineTest2() {
        DiscreetPoint a = new DiscreetPoint(0, 0);
        DiscreetPoint b = new DiscreetPoint(5, 2);

        List<DiscreetPoint> line = Line.getLine(a, b);
        assertEquals(6, line.size());

        assertTrue(line.contains(new DiscreetPoint(0, 0)));
        assertTrue(line.contains(new DiscreetPoint(1, 0)));
        assertTrue(line.contains(new DiscreetPoint(2, 1)));
        assertTrue(line.contains(new DiscreetPoint(3, 1)));
        assertTrue(line.contains(new DiscreetPoint(4, 2)));
        assertTrue(line.contains(new DiscreetPoint(5, 2)));
    }
}
