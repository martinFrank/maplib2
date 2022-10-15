package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.geo.Point;

import java.util.ArrayList;
import java.util.List;

public class MapGeneratorUtils {

    public static List<Point> mapSizeToPoints(MapGenerationParameter parameter) {
        List<Point> points = new ArrayList<>();
        for (int dy = 0; dy < parameter.height; dy++) {
            for (int dx = 0; dx < parameter.width; dx++) {
                points.add(new Point(dx, dy));
            }
        }
        return points;
    }

}
