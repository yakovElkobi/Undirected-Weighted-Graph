package ex1.tests;

import org.junit.jupiter.api.Test;
import ex1.src.WGraph_DS;
import ex1.src.node_info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WGraph_DSTest {

    @Test
    void getNode() {
        WGraph_DS g = new WGraph_DS();
        node_info n = g.getNode(0);
        g.addNode(2);
        assertTrue(n.getKey() == 4);
        assertTrue(n.getKey() == 2);

    }

    @Test
    void hasEdge() {
        WGraph_DS g = new WGraph_DS();
        assertFalse(g.hasEdge(2,3));
        g.addNode(2);
        g.addNode(3);
        g.connect(2,3,2.2);
        assertTrue(g.hasEdge(2,3));
        assertTrue(g.hasEdge(3,2));
        assertFalse(g.hasEdge(2,2));
        assertFalse(g.hasEdge(2,5) == true);
        assertFalse(g.hasEdge(5,2) == true);
    }

    @Test
    void getEdge() {
        WGraph_DS g = new WGraph_DS();
        g.addNode(3);
        g.addNode(4);
        g.connect(3,4,5);
        assertTrue(g.getEdge(3,4) == 5);
        assertTrue(g.getEdge(4,3) == 5);
        assertFalse(g.getEdge(3,4) == 3.3);

    }

    @Test
    void addNode() {
        WGraph_DS g = new WGraph_DS();
        g.addNode(2);
        g.addNode(2);
        assertTrue(g.nodeSize() == 1);

    }

    @Test
    void connect() {
        WGraph_DS g = new WGraph_DS();
        g.addNode(3);
        g.addNode(5);
        g.connect(3,5,3.5);
        assertTrue(g.hasEdge(3,5));
        assertTrue(g.getEdge(3,5) == 3.5);
        g.connect(5,3,3.5);
        assertTrue(g.nodeSize() == 2);
        g.connect(5,3,4);
        assertTrue(g.edgeSize() == 1);
        assertTrue((g.getEdge(5,3))==4);
         g.connect(3,2,4.3);
        assertTrue(g.getEdge(2,3) == -1);
         g.connect(3,5,-1);
         g.connect(3,3,2);
         assertTrue((g.edgeSize()) == 1);
    }

    @Test
    void getV() {
        WGraph_DS g = new WGraph_DS();
        for (int i = 0;i<10;i++)
            g.addNode(i);
          System.out.println(g.getV());

    }

    @Test
    void testGetV() {
    }

    @Test
    void removeNode() {
        WGraph_DS g = new WGraph_DS();
        for (int i=0;i<10;i++)
            g.addNode(i);
        g.removeNode(3);
        g.removeNode(11);
        g.connect(2,3,2);
        g.connect(1,2,3);
        g.removeNode(2);
        assertTrue(g.nodeSize()==8);
    }

    @Test
    void removeEdge() {
        WGraph_DS g = new WGraph_DS();
        for (int i=0;i<10;i++)
            g.addNode(i);
        g.connect(2,3,4);
        g.connect(3,2,4);
        g.connect(2,3,2);
        g.removeEdge(3,2);
        g.connect(3,2,1);
        g.connect(1,2,3);
        g.removeEdge(1,2);
        g.removeEdge(1,2);
        assertTrue(g.edgeSize()==1);
    }

    @Test
    void nodeSize() {
        WGraph_DS g = new WGraph_DS();
        for (int i = 0;i< 5;i++)
            g.addNode(i);
        g.removeNode(3);
        g.connect(2,3,3);
        g.removeNode(2);
        assertTrue(g.nodeSize()==3);
    }

    @Test
    void edgeSize() {
        WGraph_DS g = new WGraph_DS();
        for (int i = 0;i< 5;i++)
            g.addNode(i);
        g.connect(2,3,4);
        g.connect(2,3,2);
        g.connect(3,2,2);
        assertTrue(g.edgeSize()==1);
        g.removeNode(4);
        g.removeNode(22);
        g.removeEdge(5,3);
        g.removeNode(3);
        assertTrue(g.edgeSize()==0);
    }

    @Test
    void getMC() {
        WGraph_DS g = new WGraph_DS();
        for (int i =0;i<10;i++)
            g.addNode(i);
        assertTrue(g.getMC()==10);
        g.connect(2,4,43.3);
        g.connect(2,4,3);
        assertTrue(g.getMC()==12);
        g.removeEdge(2,4);
        assertTrue(g.getMC() == 13);
        g.connect(5,3,4);
        g.removeEdge(5,3);
        assertTrue(g.getMC()==15);
        g.removeEdge(3,2);
        g.removeNode(3);
        g.removeNode(11);
        assertTrue(g.getMC()==16);
    }
}