package com.github.martinfrank.maplib.maplib2.generate;

import com.github.martinfrank.maplib.maplib2.map.Map;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MapGeneratorTest  {

    @Test
    public void test_mapGenerated_defaultParameter_Successfully(){
        //given
        MapGenerationParameter parameter = MapGenerationParameter.newBuilder().width(20).height(20).build();

        //when
        Map map = MapGenerator.generate(parameter);


        map.getEdgeOnScreen(4,4,2,3);

        //then
        assertNotNull(map);
    }

}