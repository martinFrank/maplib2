package com.github.martinfrank.maplib2.geo;

import java.util.Comparator;

/**
 * Comparator to sort List<GeoPoint> clockwise (requires a center)
 * @author martinFrank
 *
 */
class CirclePointComparator implements Comparator<Point>{

	/**
	 * center to which the points are compared - default = 0/0
	 */
	private final Point center;

	/**
	 * comparator with non-default center (default would have been 0/0)
     * @param center center of the comperator
	 */
    CirclePointComparator(Point center){
		this.center = center;
	}
	
	/**
	 * comparator with non-default center (default would have been 0/0)
     * @param cx center x of the comperator
     * @param cy center y of the comperator
	 */
	CirclePointComparator(int cx, int cy){
		this(new Point(cx, cy) );
	}
	
	/**
	 * comparator with default center (0/0)
	 */
	CirclePointComparator(){
		this(new Point(0,0) );
	}
	
	@Override
	public int compare(Point o1, Point o2) {
		GlPolarPoint p1 = new GlPolarPoint(o1, center);
		GlPolarPoint p2 = new GlPolarPoint(o2, center);
		return p1.compareTo(p2);
	}

    //visibleForTesting
	/**
	 * polar points represent a point with angle/distance<br>
	 * they are equal to Cartesian points(x/y)
	 * @author martinFrank
	 *
	 */
	class GlPolarPoint implements Comparable<GlPolarPoint> {
		private final double theta;
		private final double length;

        GlPolarPoint(Point point, Point center) {
			double dx = point.x - center.x;
			double dy = point.y - center.y;
			theta = Math.atan2(dy, dx);
			length = Line.distance(point, center);
		}

		@Override
		public int compareTo(GlPolarPoint o) {
			int d = Double.compare(theta, o.theta);
			if(d == 0){
                return Double.compare(o.length, length); //closer points come first
			}else{
				return d;
			}
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			GlPolarPoint that = (GlPolarPoint) o;

			if (Double.compare(that.theta, theta) != 0) return false;
			return Double.compare(that.length, length) == 0;
		}

		@Override
		public int hashCode() {
			int result;
			long temp;
			temp = Double.doubleToLongBits(theta);
			result = (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(length);
			result = 31 * result + (int) (temp ^ (temp >>> 32));
			return result;
		}
	}
}