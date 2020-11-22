package ex1.src;

import java.io.Serializable;
import java.util.*;
/**
 * Graph_DS implements the graph interface and represents the Graph structure
 * * Graph_DS implements the node_info  private class
 * all nodes in the graph are stores in HashMap
 * all edges in graph are stores in double HashMap represents by the neighbors and weighted of each node
 * the edgeSize is counting within add\remove edges methods
 * The mc is increase on each method that changes something in the graph
 */
public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer,node_info> nodes;
    private HashMap<Integer, HashMap<Integer,Double>> edges;
    private int edgeSize;
    private int mc;

    public WGraph_DS(){
        nodes = new HashMap<Integer,node_info>();
        edges = new HashMap<Integer, HashMap<Integer, Double>>();
        edgeSize = 0;
        mc = 0;
    }

    @Override
    public node_info getNode(int key) {
        if(!nodes.containsKey(key)) return null;
        return nodes.get(key);
    }
    /**
     * checks if the nodes containsKey  and one node is neighbor of the second one
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (edges.containsKey(node1))
        return edges.get(node1).containsKey(node2);
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if(hasEdge(node1,node2))
            return edges.get(node1).get(node2);
        return -1;

    }

    @Override
    public void addNode(int key) {
        if(!nodes.containsKey(key)){
            NodeInfo n1 = new NodeInfo(key);
            edges.put(key, new HashMap<Integer, Double>());
            nodes.put(n1.getKey(), n1);
            mc++;
        }
    }
    /**
     * checks that the nodes are different and checks that the node are not connected yet
     * if all checks are passed then add each one to other's neighbors and add edge weight (undirected graph)
     * else if edge already exists updates the weight of the edge
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if(!nodes.containsKey(node1) || !nodes.containsKey(node2)) return;
    if(!hasEdge(node1,node2) && node1 != node2 && w >= 0){
        edges.get(node1).put(node2,w);
        edges.get(node2).put(node1,w);
        edgeSize++;
        mc++;
     }
    else if(node1 != node2 && w >= 0 && w != getEdge(node1,node2) ) {
        edges.get(node1).put(node2, w);
        edges.get(node2).put(node1, w);
        mc++;

     }
    }

    @Override
    public Collection<node_info> getV() {
        return nodes.values();
    }
    /**
     * get the  key
     * loop over all neighbors of given and put them in ArrayList and return the list
     *
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if (!nodes.containsKey(node_id)) return null;
        Set<Integer> keys = edges.get(node_id).keySet();
        ArrayList<node_info> k = new ArrayList<>();
        for (Integer i: keys) {
            k.add(getNode(i));
        }
        return k;

    }
    /**
     * get the key
     * loop over all neighbors of given node and remove it from their neighbors
     * finally, remove given node from the nodes
     * all this method counting as only 1 change (modeCount)
     */
    @Override
    public node_info removeNode(int key) {
        if(nodes.containsKey(key)) {
            Set<Integer> niKeys = edges.get(key).keySet();
            edges.remove(key);
            for (Integer i : niKeys) {
                edges.get(i).remove(key);
                edgeSize--;
            }
            mc++;
        }
        return nodes.remove(key);
    }
    /**
     * checks that the node are connected
     * remove each node from other's neighbors (undirected graph)
     */
    @Override
    public void removeEdge(int node1, int node2) {
     if(hasEdge(node1,node2)){
         edges.get(node1).remove(node2);
         edges.get(node2).remove(node1);
         edgeSize--;
         mc++;
     }
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public int getMC() {
        return mc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        return edgeSize == wGraph_ds.edgeSize &&
                mc == wGraph_ds.mc &&
                nodes.equals(wGraph_ds.nodes) &&
                edges.equals(wGraph_ds.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, edges, edgeSize, mc);
    }

    private class NodeInfo implements node_info,Serializable{
        private int key;
        private double tag;
        private String info;

        public NodeInfo(int key) {
            this.key = key;
            tag = -1;
            info = "";
        }
        @Override
        public int getKey() {
            return key;
        }

        @Override
        public String getInfo() {
            return info;
        }

        @Override
        public void setInfo(String s) {
         info = s;
        }

        @Override
        public double getTag() {
            return tag;
        }

        @Override
        public void setTag(double t) {
         tag = t;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeInfo nodeInfo = (NodeInfo) o;
            return key == nodeInfo.key &&
                    Double.compare(nodeInfo.tag, tag) == 0 &&
                    Objects.equals(info, nodeInfo.info);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, tag, info);
        }
    }
}
