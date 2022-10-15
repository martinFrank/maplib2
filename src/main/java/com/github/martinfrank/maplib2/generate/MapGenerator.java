package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.MapPartFactory;
import com.github.martinfrank.maplib2.map.Node;

public class MapGenerator<F extends Field<E,N>, E extends Edge<N>, N extends Node> {

    public Map<F, E, N> generate(MapGenerationParameter parameter, MapPartFactory<F, E, N> factory){
        switch (parameter.mapStyle){
            case SQUARE: return (new SquareMapGenerator<F, E, N>()).generate(parameter, factory);
            case HEX: return (new HexMapGenerator<F, E, N>()).generate(parameter, factory);
//            case TRIANGLE: return (new TriangleMapGenerator()).generate(parameter);
            default: throw new IllegalArgumentException("MapGenerationParameter are invalid");
        }
    }

    public Map<Field<Edge<Node>, Node>, Edge<Node>, Node> generate(MapGenerationParameter parameter){
        DefaultMapPartFactory factory = new DefaultMapPartFactory();
        switch (parameter.mapStyle){
            case SQUARE: return (new SquareMapGenerator<>()).generate(parameter, factory);
            case HEX: return (new HexMapGenerator<>()).generate(parameter, factory);
//            case TRIANGLE: return (new TriangleMapGenerator()).generate(parameter);
            default: throw new IllegalArgumentException("MapGenerationParameter are invalid");
        }
    }



}
