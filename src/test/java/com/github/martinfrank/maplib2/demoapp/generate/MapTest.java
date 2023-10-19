package com.github.martinfrank.maplib2.demoapp.generate;

import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Field;
import com.github.martinfrank.maplib2.demoapp.map.Map;
import com.github.martinfrank.maplib2.demoapp.map.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MapTest {

    @Test
    public void test_NodesAreUnique(){
        //given
        MapGenerationParameter parameter = MapGenerationParameter.newBuilder().width(2).height(1).build();
        Map map = MapGenerator.generate(parameter, new DefaultMapPartFactory());

        Field field_0_0 = map.getField(0, 0);
        Field field_1_0 = map.getField(1,0);

        Edge same = null;
        for (Edge edges00: field_0_0.edges){
            for(Edge edges10: field_1_0.edges ){
                if(edges00 == edges10){ //wohlgemerkt not equals!
                    same = edges00;
                    break;
                }
                if(same != null){
                    break;
                }
            }
        }
        assertNotNull(same);
    }

}