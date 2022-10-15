package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;
import org.junit.Assert;
import org.junit.Test;

public class MapTest {

    @Test
    public void test_NodesAreUnique(){
        //given
        MapGenerationParameter parameter = MapGenerationParameter.newBuilder().width(2).height(1).build();
        Map<Field<Edge<Node>,Node>,Edge<Node>,Node> map = new MapGenerator<>().generate(parameter, new DefaultMapPartFactory());

        Field<Edge<Node>,Node> field_0_0 = map.fields.getField(0, 0);
        Field<Edge<Node>,Node> field_1_0 = map.fields.getField(1,0);

        Edge<Node> same = null;
        for (Edge<Node> edges00: field_0_0.edges){
            for(Edge<Node> edges10: field_1_0.edges ){
                if(edges00 == edges10){ //wohlgemerkt not equals!
                    same = edges00;
                    break;
                }
                if(same != null){
                    break;
                }
            }
        }
        Assert.assertNotNull(same);
    }

}