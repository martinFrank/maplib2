package com.github.martinfrank.maplib2.demoapp.generate;

import com.github.martinfrank.maplib2.demoapp.geo.DiscreetPoint;
import com.github.martinfrank.maplib2.demoapp.map.MapStyle;

import java.util.ArrayList;
import java.util.List;

public class MapGenerationParameter {

    public final MapStyle mapStyle;
    public final MapStyle.Orientation orientation;

    public final int width;
    public final int height;
    private MapGenerationParameter(MapStyle mapStyle, MapStyle.Orientation orientation, int width, int height) {
        this.mapStyle = mapStyle;
        this.orientation = orientation;
        this.width = width;
        this.height = height;
    }

    public static MapGenerationParameter.Builder newBuilder() {
        return new Builder();
    }

    public static class Builder{
        private MapStyle mapStyle = MapStyle.SQUARE;
        private MapStyle.Orientation orientation = MapStyle.Orientation.HORIZONTAL;
        private int width;
        private int height;

        public Builder width(int width){
            this.width = width;
            return this;
        }

        public Builder height(int height){
            this.height = height;
            return this;
        }

        public Builder mapStyle(MapStyle mapStyle){
            this.mapStyle = mapStyle;
            return this;
        }

        public Builder orientation(MapStyle.Orientation orientation){
            this.orientation = orientation;
            return this;
        }

        public MapGenerationParameter build(){
            if(width <= 0){
                throw new IllegalStateException("width must be properly set");
            }
            if(height <= 0){
                throw new IllegalStateException("height must be properly set");
            }
            if(mapStyle == null){
                throw new IllegalStateException("mapstyle must be properly set");
            }
            if(orientation == null){
                throw new IllegalStateException("mapstyle.orientation must be properly set");
            }

            return new MapGenerationParameter(mapStyle, orientation, width, height);
        }
    }


    List<DiscreetPoint> getPositions() {
        List<DiscreetPoint> points = new ArrayList<>();
        for (int dy = 0; dy < height; dy++) {
            for (int dx = 0; dx < width; dx++) {
                points.add(new DiscreetPoint(dx, dy));
            }
        }
        return points;
    }

}
