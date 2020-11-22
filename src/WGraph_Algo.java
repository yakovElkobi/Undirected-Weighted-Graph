package ex1.src;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph g;

    public WGraph_Algo(){
        this.g = new WGraph_DS();
    }
    @Override
    public void init(weighted_graph g) {
        this.g = g;
    }

    @Override
    public weighted_graph getGraph() {
        return g;
    }

    @Override
    public weighted_graph copy() {
    	if(g == null) return null;
        WGraph_DS copy = new WGraph_DS();
        for(node_info n : g.getV()) {
        	int key = n.getKey();
        	copy.addNode(key);
        	node_info copy_node = copy.getNode(key);
        	copy_node.setInfo(n.getInfo());
        	copy_node.setTag(n.getTag());
        }
        for (node_info n: g.getV()) {
        	for(node_info m : g.getV(n.getKey())) {
        		copy.connect(n.getKey(), m.getKey(), g.getEdge(n.getKey(), m.getKey()));
        	}
        }
        return copy;
    }

    private HashMap<Integer, node_info> dijkstra(int src){
    	HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();
        HashMap<Integer, node_info> vertex = new HashMap<Integer, node_info>();
        PriorityQueue<node_info> q = new PriorityQueue<node_info>(new Comparator<node_info>() {
			@Override
			public int compare(node_info n1, node_info n2) {
				if(n1.getTag() < n2.getTag()) return -1;
				else if(n1.getTag() > n2.getTag()) return 1;
				else return 0;
			}
		});
        for (node_info n: g.getV()) {
            n.setTag(Double.POSITIVE_INFINITY);
            visited.put(n.getKey(), false);
            vertex.put(n.getKey(), null);
        }
        node_info n = g.getNode(src);
        q.add(n);
        n.setTag(0);
        while(!q.isEmpty()) {
            node_info m = q.poll();
            for (node_info v: g.getV(m.getKey())) {
            	double w = m.getTag() + g.getEdge(m.getKey(), v.getKey());
                if (!visited.get(v.getKey()) && v.getTag() > w){
                    vertex.put(v.getKey(), m);
                    v.setTag(w);
                    q.remove(v);
                    q.add(v);
                }
            }
            visited.put(m.getKey(), true);
        }
        return vertex;
    }
	/**
	 * using the BFS algorithm with the first node in graph, then checks if there is a node the BFS search does not visited
	 * (the tag is -1) and if its true return false (not connected), else return true (BFS visit all nodes)
	 */
    @Override
    public boolean isConnected() {
    	if(g == null || g.nodeSize() == 0) return true;
		bfs(g.getV().iterator().next().getKey());
		for(node_info n : g.getV()) {
			if(n.getTag() == -1) return false;
		}
		return true;
    }
	/**
	 * using shortestPath that return the list with the shortestPath and the size of  is the length of the shortestPath
	 */
    @Override
    public double shortestPathDist(int src, int dest) {
    	List<node_info> p = shortestPath(src, dest);
    	if(p == null) return -1;
    	double sum = 0;
    	for (int i = 1; i < p.size(); i++) {
			sum += g.getEdge(p.get(i-1).getKey(), p.get(i).getKey());
		}
        return sum;
    }
	/**
	 * using the dijkstra algorithm from src that put the parent of each node in his tag and after the algorithm,
	 * start from dest and insert the parent to the list until we get back to src
	 */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
    	if(g == null || g.getNode(src) == null || g.getNode(dest) == null) return null;
    	HashMap<Integer, node_info> ans = dijkstra(src);
    	List<node_info> path = new LinkedList<node_info>();
    	path.add(0, g.getNode(dest));
    	int x = dest;
    	System.out.println(ans.values().toString());
    	while(x != src) {
    	//	ans.get(x).toString();
    		node_info p = ans.get(x);
    		if(p==null) return null;
    		path.add(0, p);
    		x = p.getKey();
    	}
        return path;
    }
    
    @Override
    public boolean save(String file)  {
    	try {
    		FileOutputStream fout = new FileOutputStream(file);
    		ObjectOutputStream obj = new ObjectOutputStream(fout);
    		
    		obj.writeObject(g);
    		obj.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e){
    		e.printStackTrace();
		}
        return false;
    }

    @Override
    public boolean load(String file) {
    	try {
    		FileInputStream fin = new FileInputStream(file);
    		ObjectInputStream obj = new ObjectInputStream(fin);
    		g = (weighted_graph) obj.readObject();
    		obj.close();
    		fin.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return false;
    }
    
	private void bfs(int src) { // O(|V|+|E|)
		ArrayBlockingQueue<Integer> q = new ArrayBlockingQueue<Integer>(g.nodeSize());
		for(node_info n : g.getV()) {
			n.setTag(-1);
		}
		q.add(src);
		g.getNode(src).setTag(0);
		while(!q.isEmpty()) {
			int v = q.poll();
			for(node_info n : g.getV(v)) {
				if(n.getTag() == -1) {
					n.setTag(v);
					q.add(n.getKey());
				}
			}
		}
	}
}
