package com.github.martinfrank.maplib2.geo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this class provides a method to calculate a circle from center and a radius
 * (bresenham algortihm)
 * 
 * see also https://en.wikipedia.org/wiki/Midpoint_circle_algorithm
 * 
 * @author martinFrank
 *
 */
public class Circle {

	/**
	 * private Constructor - not required<br>
	 * use getCircle() instead
	 */
	private Circle() {
	}

	/**
	 * creates a circle by given center and radius
	 * 
	 * @param x0 - x of center
	 * @param y0 - y of center
	 * @param radius of the circle
	 * @return a list of points representing a circle (sorted clockwise)
	 */
	public static List<Point> getCircle(int x0, int y0, int radius) {
		List<Point> circle = new ArrayList<>();
		int x = 0;
		int y = radius;
		int dp = 1 - radius;

        addPoint(circle, new Point(x0, y0 + radius));
        addPoint(circle, new Point(x0, y0 - radius));
        addPoint(circle, new Point(x0 + radius, y0));
        addPoint(circle, new Point(x0 - radius, y0));

		do {
			if (dp < 0) {
				dp = dp + 2 * (++x) + 3;
			} else {
				dp = dp + 2 * (++x) - 2 * (--y) + 5;
			}

            addPoint(circle, new Point(x0 + x, y0 + y)); // For the 8 octants
            addPoint(circle, new Point(x0 - x, y0 + y));
            addPoint(circle, new Point(x0 + x, y0 - y));
            addPoint(circle, new Point(x0 - x, y0 - y));
            addPoint(circle, new Point(x0 + y, y0 + x));
            addPoint(circle, new Point(x0 - y, y0 + x));
            addPoint(circle, new Point(x0 + y, y0 - x));
            addPoint(circle, new Point(x0 - y, y0 - x));
		} while (x < y);
		CirclePointComparator cc = new CirclePointComparator(x0, y0);
		circle.sort(cc);
		return circle;
	}

    private static void addPoint(List<Point> circle, Point geoPoint) {
        if (!circle.contains(geoPoint)) {
            circle.add(geoPoint);
        }
    }

	/**
	 * creates a circle by given center and radius
	 *
	 * @param center of the circle
	 * @param radius of the circle
	 * @return a list of points representing a circle (sorted clockwise)
	 */
	public static List<Point> getCircle(Point center, int radius) {
		return getCircle((int) center.x, (int) center.y, radius);
	}

}
