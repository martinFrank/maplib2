package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MapGeneratorTest  {

    @Test
    public void test_mapGenerated_defaultParameter_Successfully(){
        //given
        MapGenerationParameter parameter = MapGenerationParameter.newBuilder().width(20).height(20).build();
        MapGenerator<Field, Edge, Node> mapGenerator = new MapGenerator();

        //when
        Map<Field, Edge,  Node> map = mapGenerator.generate(parameter);

        //then
        assertNotNull(map);
    }

}