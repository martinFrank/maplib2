package com.github.martinfrank.maplib.maplib2.generate;

import com.github.martinfrank.maplib.maplib2.map.Map;
import com.github.martinfrank.maplib.maplib2.map.MapPartFactory;

public class MapGenerator{

    private MapGenerator(){

    }


    public static Map generate(MapGenerationParameter parameter, MapPartFactory factory) {
        return switch (parameter.mapStyle) {
            case SQUARE -> (new SquareMapGenerator()).generate(parameter, factory);
            case HEX -> (new HexMapGenerator()).generate(parameter, factory);
//            case TRIANGLE: return (new TriangleMapGenerator()).generate(parameter);
            default -> throw new IllegalArgumentException("MapGenerationParameter are invalid");
        };
    }

    public static Map generate(MapGenerationParameter parameter){
        DefaultMapPartFactory factory = new DefaultMapPartFactory();
        return switch (parameter.mapStyle) {
            case SQUARE -> (new SquareMapGenerator()).generate(parameter, factory);
            case HEX -> (new HexMapGenerator()).generate(parameter, factory);
//            case TRIANGLE: return (new TriangleMapGenerator()).generate(parameter);
            default -> throw new IllegalArgumentException("MapGenerationParameter are invalid");
        };
    }


}
