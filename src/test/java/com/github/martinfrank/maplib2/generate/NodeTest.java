package com.github.martinfrank.maplib2.generate;

import com.github.martinfrank.maplib2.map.Node;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class NodeTest extends TestCase {

    @Test
    public void testEquals(){
        Node n1 = BypassHiddenConstructorUtil.createNodeViaReflection(1,1);
        Node n2 = BypassHiddenConstructorUtil.createNodeViaReflection(1,1);

        Assert.assertEquals(n1,n2);
    }


}