package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeTest {

    @Test
    public void testEquals(){
        Node n1 = BypassHiddenConstructorUtil.createNodeViaReflection(1,1);
        Node n2 = BypassHiddenConstructorUtil.createNodeViaReflection(1,1);

        assertEquals(n1,n2);
    }


}