package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;
import junit.framework.TestCase;
import org.junit.Test;

public class MapGeneratorTest extends TestCase {

    @Test
    public void test_mapGenerated_defaultParameter_Successfully(){
        //given
        MapGenerationParameter parameter = MapGenerationParameter.newBuilder().width(20).height(20).build();
        MapGenerator<Field<Edge<Node>, Node>,Edge<Node>,Node> mapGenerator = new MapGenerator<>();

        //when
        Map<Field<Edge<Node>,Node>, Edge<Node>, Node> map = mapGenerator.generate(parameter);

        //then
        assertNotNull(map);
    }

}