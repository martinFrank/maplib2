package com.github.martinfrank.maplib2.generate.delaunay;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.github.martinfrank.maplib2.generate.delaunay.DoubleApproximity.isApproximatelyEqual;

public class DelaunayTriangle {

    private final DelaunayNode a;
    private final DelaunayNode b;
    private final DelaunayNode c;

    private final DelaunayEdge ab;
    private final DelaunayEdge ac;
    private final DelaunayEdge bc;

    public final double area;

    private final DelaunayNode center;
    private final DelaunayEdge aCenter;

    public DelaunayTriangle(DelaunayNode a, DelaunayNode b, DelaunayNode c) {
        this.a = a;
        this.b = b;
        this.c = c;

        ab = new DelaunayEdge(a, b);
        ac = new DelaunayEdge(a, c);
        bc = new DelaunayEdge(b, c);

        area = calculateArea();
        center = calculateCircumscribedCenter();
        aCenter = new DelaunayEdge(a, center);
    }

    private DelaunayNode calculateCircumscribedCenter() {
        //https://en.wikipedia.org/wiki/Circumscribed_circle
        double d = 2 * (
                a.getX() * (b.getY() - c.getY()) +
                        b.getX() * (c.getY() - a.getY()) +
                        c.getX() * (a.getY() - b.getY())
        );
        double ux =
                (Math.pow(a.getX(), 2) + Math.pow(a.getY(), 2)) * (b.getY() - c.getY()) +
                        (Math.pow(b.getX(), 2) + Math.pow(b.getY(), 2)) * (c.getY() - a.getY()) +
                        (Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2)) * (a.getY() - b.getY());
        double uy =
                (Math.pow(a.getX(), 2) + Math.pow(a.getY(), 2)) * (c.getX() - b.getX()) +
                        (Math.pow(b.getX(), 2) + Math.pow(b.getY(), 2)) * (a.getX() - c.getX()) +
                        (Math.pow(c.getX(), 2) + Math.pow(c.getY(), 2)) * (b.getX() - a.getX());
        return new DelaunayNode(ux / d, uy / d);

    }

    private double calculateArea() {
        double la = bc.getLength();
        double lb = ac.getLength();
        double lc = ab.getLength();

        //https://en.wikipedia.org/wiki/Heron%27s_formula
        return 0.25 * Math.sqrt((la + lb + lc) * (-1 * la + lb + lc) * (la - lb + lc) * (la + lb - lc));
    }

    public DelaunayNode getA() {
        return a;
    }

    public DelaunayNode getB() {
        return b;
    }

    public DelaunayNode getC() {
        return c;
    }

    public DelaunayEdge getAB() {
        return ab;
    }

    public DelaunayEdge getAC() {
        return ac;
    }

    public DelaunayEdge getBC() {
        return bc;
    }

    public DelaunayNode getCenter() {
        return center;
    }

    public double getArea() {
        return area;
    }

    public boolean surrounds(DelaunayNode node) {
        //https://discuss.codechef.com/t/best-way-to-check-if-a-point-lies-inside-a-triangle-or-not/13528
        DelaunayTriangle abn = new DelaunayTriangle(a, b, node);
        DelaunayTriangle acn = new DelaunayTriangle(a, c, node);
        DelaunayTriangle bcn = new DelaunayTriangle(b, c, node);
        return isApproximatelyEqual(area, abn.getArea() + acn.getArea() + bcn.getArea());
    }

    public List<DelaunayNode> getNodes() {
        return Arrays.asList(a, b, c);
    }

    public List<DelaunayEdge> getEdges() {
        return Arrays.asList(ab, ac, bc);
    }

    public boolean isInCircumference(DelaunayNode x) {
        DelaunayEdge xCenter = new DelaunayEdge(x, center);
        return xCenter.getLength() <= aCenter.getLength();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DelaunayTriangle that = (DelaunayTriangle) o;
        return Objects.equals(a, that.a) && Objects.equals(b, that.b) && Objects.equals(c, that.c) ||
                Objects.equals(a, that.b) && Objects.equals(b, that.a) && Objects.equals(c, that.c) ||
                Objects.equals(a, that.c) && Objects.equals(b, that.b) && Objects.equals(c, that.a) ||
                Objects.equals(a, that.b) && Objects.equals(b, that.c) && Objects.equals(c, that.a) ||
                Objects.equals(a, that.a) && Objects.equals(b, that.c) && Objects.equals(c, that.b) ||
                Objects.equals(a, that.c) && Objects.equals(b, that.a) && Objects.equals(c, that.b)
                ;
    }

    @Override
    public int hashCode() {
        List<DelaunayNode> nodes = Arrays.asList(a, b, c);
        nodes.sort(new CounterClockwiseNodeComparator(a, b, c));
        return Objects.hash(nodes.get(0), nodes.get(1), nodes.get(2));
    }

    @Override
    public String toString() {
        return a + " -> " + b + " -> " + c;
    }

    public static DelaunayEdge getEdge(DelaunayTriangle t1, DelaunayTriangle t2) {
        return t1.getEdges().stream().filter(e -> t2.getEdges().contains(e)).findAny().orElseThrow(IllegalAccessError::new);
    }
}
