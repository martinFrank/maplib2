package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Edge;
import com.github.martinfrank.maplib2.map.Node;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class EdgeTest extends TestCase {

    @Test
    public void test_ab_equal_ba(){
        //given
        Node a = BypassHiddenConstructorUtil.createNodeViaReflection(1,1);
        Node b = BypassHiddenConstructorUtil.createNodeViaReflection(2,2);

        //when
        Edge ab = BypassHiddenConstructorUtil.createEdgeViaReflection(a,b);
        Edge ba = BypassHiddenConstructorUtil.createEdgeViaReflection(b,a);

        //then
        Assert.assertEquals(ab, ba);
    }

}