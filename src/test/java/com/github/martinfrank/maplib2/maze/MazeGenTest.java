package com.github.martinfrank.maplib2.maze;

import com.github.martinfrank.maplib2.generate.MapGenerationParameter;
import com.github.martinfrank.maplib2.generate.MapGenerator;
import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Field;
import com.github.martinfrank.maplib2.map.Map;
import com.github.martinfrank.maplib2.map.Node;
import org.junit.Test;

import java.util.List;

public class MazeGenTest {

    @Test
    public void doIt(){
        //given
        MapGenerationParameter parameter = MapGenerationParameter.newBuilder().width(8).height(8).build();
        Map<Field, Edge, Node> map = new MapGenerator<>().generate(parameter);
        Field f = map.fields.getField(2,2);

        //when
        List<Field> surrounds = RecursiveBackTrackerAlgorithm.FIELDS.getFieldsByNode(f);

        //then
        for (Field s: surrounds){
            System.out.println(s);
        }


    }
}
