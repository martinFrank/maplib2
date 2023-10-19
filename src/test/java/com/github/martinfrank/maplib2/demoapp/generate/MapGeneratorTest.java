package com.github.martinfrank.maplib2.demoapp.generate;

import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Field;
import com.github.martinfrank.maplib2.demoapp.map.Map;
import com.github.martinfrank.maplib2.demoapp.map.Node;
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


        map.getEdgeOnScreen(4,4,2,3);

        //then
        assertNotNull(map);
    }

}