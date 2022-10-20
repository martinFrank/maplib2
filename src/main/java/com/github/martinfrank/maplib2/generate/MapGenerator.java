package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib2.map.Node;

@SuppressWarnings({"rawtypes", "unused"})
public class MapGenerator<F extends Field, E extends Edge, N extends Node> {

    @SuppressWarnings("rawtypes")
    public static <F extends Field, E extends Edge, N extends Node> Map<F, E, N> generate(MapGenerationParameter parameter, MapPartFactory<F, E, N> factory) {
        switch (parameter.mapStyle) {
            case SQUARE:
                return (new SquareMapGenerator<F, E, N>()).generate(parameter, factory);
            case HEX:
                return (new HexMapGenerator<F, E, N>()).generate(parameter, factory);
//            case TRIANGLE: return (new TriangleMapGenerator()).generate(parameter);
            case DELAUNAY:
                return (new DelaunayMapGenerator<F, E, N>()).generate(parameter, factory);
            default:
                throw new IllegalArgumentException("MapGenerationParameter are invalid");
        }
    }

    public static Map<Field, Edge, Node> generate(MapGenerationParameter parameter){
        DefaultMapPartFactory factory = new DefaultMapPartFactory();
        switch (parameter.mapStyle){
            case SQUARE: return (new SquareMapGenerator<>()).generate(parameter, factory);
            case HEX: return (new HexMapGenerator<>()).generate(parameter, factory);
//            case TRIANGLE: return (new TriangleMapGenerator()).generate(parameter);
            default: throw new IllegalArgumentException("MapGenerationParameter are invalid");
        }
    }


}
