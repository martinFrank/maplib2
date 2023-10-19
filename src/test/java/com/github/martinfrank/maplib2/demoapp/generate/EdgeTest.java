package com.github.martinfrank.maplib2.demoapp.generate;

import com.github.martinfrank.maplib2.demoapp.map.Edge;
import com.github.martinfrank.maplib2.demoapp.map.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EdgeTest {

    @Test
    public void test_ab_equal_ba(){
        //given
        Node a = BypassHiddenConstructorUtil.createNodeViaReflection(1,1);
        Node b = BypassHiddenConstructorUtil.createNodeViaReflection(2,2);

        //when
        Edge ab = BypassHiddenConstructorUtil.createEdgeViaReflection(a,b);
        Edge ba = BypassHiddenConstructorUtil.createEdgeViaReflection(b,a);

        //then
        assertEquals(ab, ba);
    }

}