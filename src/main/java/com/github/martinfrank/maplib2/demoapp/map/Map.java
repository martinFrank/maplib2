package com.github.martinfrank.maplib2.demoapp.map;

import com.github.martinfrank.maplib2.demoapp.geo.FloatingPoint;
import com.github.martinfrank.maplib2.demoapp.geo.Polygon;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Map {

    private final Fields fields;

    public final Polygon polygon;

    private Map(Fields fields) {
        this.fields = fields;
        double w = fields.getWidth();
        double h = fields.getHeight();
        polygon = new Polygon(Arrays.asList(
                new FloatingPoint(0, 0),
                new FloatingPoint(0, h),
                new FloatingPoint(w, h),
                new FloatingPoint(w, 0)
        ));
    }

    public Map(Map sample) {
        this(sample.fields);
    }


    public Field getFieldOnScreen(double x, double y, double scale, double catchRadius) {
        return fields.getFieldOnScreen(new FloatingPoint(x, y), scale, catchRadius);
    }

    public Edge getEdgeOnScreen(int x, int y, double scale, int catchRadius) {
        return fields.getEdgeOnScreen(new FloatingPoint(x, y), scale, catchRadius);
    }

    public Node getNodeOnScreen(int x, int y, double scale, int catchRadius) {
        return fields.getNodeOnScreen(new FloatingPoint(x, y), scale, catchRadius);
    }

    public List<Field> getBorders() {
        return fields.getBorders();
    }

    public Field getRandomFieldWithinBorders() {
        return fields.getRandomFieldWithinBorders();
    }

    public Stream<Field> fields() {
        return fields.stream();
    }

    public Field getField(int x, int y) {
        return fields.getField(x, y);
    }

    public List<Field> getFields() {
        return fields.getAll();
    }

    public double calculateWidth(double scale) {
        double width = 0;
        for (Field f : fields.getAll()) {
            for (FloatingPoint p : f.polygon.getScaled(scale)) {
                if (p.x > width) {
                    width = (int) p.x;
                }
            }
        }
        return width;
    }

    public double calculateHeight(double scale) {
        double height = 0;
        for (Field f : fields.getAll()) {
            for (FloatingPoint p : f.polygon.getScaled(scale)) {
                if (p.y > height) {
                    height = (int) p.y;
                }
            }
        }
        return height;
    }

}
