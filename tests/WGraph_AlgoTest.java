package ex1.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {

    @Test
    void init() {

    }

    @Test
    void getGraph() {
    }

    @Test
    void copy() {
        WGraph_DS g1 = new WGraph_DS();
        for (int i = 0;i<20;i++)
            g1.addNode(i);
        g1.connect(3,2,1);
        g1.connect(4,3,5);
        g1.connect(4,3,5);
        WGraph_Algo g2 = new WGraph_Algo();
        g2.init(g1);
         WGraph_DS g3= (WGraph_DS) g2.copy();
        assertEquals(g3.nodeSize(),g1.nodeSize());
        assertEquals(g3.edgeSize(),g1.edgeSize());
        g3.removeNode(3);
        assertFalse(g1.nodeSize()== g3.nodeSize());
        assertFalse(g1.edgeSize() == g3.edgeSize());
        assertFalse(g1.getV() == g3.getV());

    }

    @Test
    void isConnected() {
        WGraph_DS g1 = new WGraph_DS();
        WGraph_Algo g2 = new WGraph_Algo();
        g2.init(g1);
        g1.addNode(1);
        g1.addNode(2);
        assertFalse(g2.isConnected());
        g1.connect(1,2,3);
        assertTrue(g2.isConnected());
        g2.init(g1);
        assertTrue(g2.isConnected());
        for (int i = 0;i<10;i++)
            g1.addNode(i);
        g2.init(g1);
        assertFalse(g2.isConnected());
        g1.connect(5,6,5);
        g1.connect(4,3,5);
        assertFalse(g2.isConnected());
    }

    @Test
    void shortestPathDist() {
        WGraph_DS g1 = new WGraph_DS();
        WGraph_Algo g2 = new WGraph_Algo();
        for (int i=0;i<10;i++)
            g1.addNode(i);
        g1.connect(1,2,3);
        g1.connect(1,5,6);
        g1.connect(2,4,4);
        g1.connect(2,5,4);
        g1.connect(1,6,6);
        g1.connect(5,3,2);
        g2.init(g1);
        assertTrue(g2.shortestPathDist(1,3)==8);
       assertTrue(g2.shortestPathDist(1,4)==7);
        assertTrue(g2.shortestPathDist(1,4)==7);
        assertTrue(g2.shortestPathDist(1,7) == -1);

    }

    @Test
    void shortestPath() {
        WGraph_DS g= new WGraph_DS();
        for (int i =0;i<20;i++)
            g.addNode(i);
        g.connect(1,2,3);
        g.connect(2,5,2);
        g.connect(3,7,7);
        g.connect(4,9,8);
        WGraph_Algo g1 = new WGraph_Algo();
        g1.init(g);
        Assertions.assertNotNull(g1.shortestPath(2,5));
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}