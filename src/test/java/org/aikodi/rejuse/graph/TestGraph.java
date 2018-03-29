package org.aikodi.rejuse.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.aikodi.rejuse.data.graph.BidiEdgeFactory;
import org.aikodi.rejuse.data.graph.DefaultNodeFactory;
import org.aikodi.rejuse.data.graph.Edge;
import org.aikodi.rejuse.data.graph.Graph;
import org.aikodi.rejuse.data.graph.Node;
import org.aikodi.rejuse.data.graph.Path;
import org.aikodi.rejuse.data.graph.UniEdgeFactory;
import org.aikodi.rejuse.data.graph.Weight;
import org.aikodi.rejuse.data.graph.WeightedEdgeFactory;
import org.aikodi.rejuse.data.graph.WeightedGraph;
import org.junit.Test;

/**
 * @author Marko van Dooren
 */
public class TestGraph {

	@Test
  public void testGraph() {
  	WeightedGraph<String> graph = new WeightedGraph<String>(
        new DefaultNodeFactory<String>(), 
        new WeightedEdgeFactory<String>(
      	 	  new BidiEdgeFactory<String>()));
    String a = "a";
    String b = "b";
    String c = "c";
    String d = "d";
    graph.addNode(a);
    assertTrue(graph.contains(a));
    assertTrue(graph.nbNodes() == 1);
    assertTrue(graph.nodes().size() == 1);
    assertTrue(graph.objects().contains(a));
    graph.addNode(b);
    assertTrue(graph.contains(b));
    assertTrue(graph.nbNodes() == 2);
    assertTrue(graph.nodes().size() == 2);
    assertTrue(graph.objects().contains(b));
    graph.addNode(c);
    assertTrue(graph.contains(c));
    assertTrue(graph.nbNodes() == 3);
    assertTrue(graph.nodes().size() == 3);
    assertTrue(graph.objects().contains(c));
    graph.addNode(a);
    assertTrue(graph.nbNodes() == 3);
    Node<String> nodeA = graph.node(a); 
    Node<String> nodeB = graph.node(b);
    Node<String> nodeC = graph.node(c);
    Edge<String> edge = null;
    edge = graph.addEdge(a,b,1);
    assertTrue(edge.getFirst().object() == a);
    assertTrue(edge.getSecond().object() == b);
    assertTrue(edge.startsIn(graph.node(a)));
    assertTrue(edge.startsIn(graph.node(b)));
    assertTrue(edge.endsIn(graph.node(a)));
    assertTrue(edge.endsIn(graph.node(b)));
    assertTrue(edge.get(Weight.class).weight() == 1);
    assertTrue(nodeA.numberOfSuccessorEdges() == 1);
    assertTrue(nodeA.numberOfPredecessorEdges() == 1);
    assertTrue(nodeB.numberOfSuccessorEdges() == 1);
    assertTrue(nodeB.numberOfPredecessorEdges() == 1);
    assertTrue(nodeC.numberOfSuccessorEdges() == 0);
    assertTrue(nodeC.numberOfPredecessorEdges() == 0);
    assertTrue(graph.node(a).canReachNode(graph.node(b)));
    graph.addEdge(b,c,1);
    graph.addEdge(a,c,1.8);
    //Path shortest = nodeA.dijkstra(nodeC);
    //assertTrue(shortest.getLength() == 1.8);
    //assertTrue(shortest.getEnd() == nodeC);
    //assertTrue(shortest.getNbEdges() == 1);
  }
  
  public void testDijkstra() {
    WeightedGraph<String> g = getBigGraph();
    
    print(g, "a", "w");
    print(g, "q", "i");
    print(g, "f", "b");
    print(g, "x", "u");
    print(g, "e", "l");
    print(g, "d", "n");
  }
  
  private <O> void print(WeightedGraph<O> g, O from, O to) {
    System.out.println(g.shortestPath(from,to));
  }

  private WeightedGraph<String> getBigGraph() {
  	WeightedGraph<String> graph = new WeightedGraph<String>(
  			                              new DefaultNodeFactory<String>(), 
  			                              new WeightedEdgeFactory<String>(
  			                            	 	  new UniEdgeFactory<String>()));
    graph.addNode("a");
    graph.addNode("b");
    graph.addNode("c");
    graph.addNode("d");
    graph.addNode("e");
    graph.addNode("f");
    graph.addNode("g");
    graph.addNode("h");
    graph.addNode("i");
    graph.addNode("j");
    graph.addNode("k");
    graph.addNode("l");
    graph.addNode("m");
    graph.addNode("n");
    graph.addNode("o");
    graph.addNode("p");
    graph.addNode("q");
    graph.addNode("r");
    graph.addNode("s");
    graph.addNode("t");
    graph.addNode("u");
    graph.addNode("v");
    graph.addNode("w");
    graph.addNode("x");
    graph.addNode("y");
    graph.addNode("z");
    
    graph.addEdge("a", "f", 77);
    
    graph.addEdge("b", "a", 70);
    graph.addEdge("b", "e", 87);
    
    graph.addEdge("c", "d", 85);
    graph.addEdge("c", "l", 59);
    
    graph.addEdge("d", "u", 40);
    graph.addEdge("d", "h", 73);
    
    graph.addEdge("e", "v", 51);
    graph.addEdge("e", "m", 64);
    
    graph.addEdge("f", "a", 77);
    graph.addEdge("f", "b", 82);
    graph.addEdge("f", "m", 76);
    graph.addEdge("f", "g", 117);
    
    graph.addEdge("g", "r", 61);
    graph.addEdge("g", "o", 86);
    graph.addEdge("g", "m", 86);
    
    graph.addEdge("h", "d", 73);
    graph.addEdge("h", "v", 59);
    graph.addEdge("h", "b", 55);
    graph.addEdge("h", "y", 198);
    
    graph.addEdge("i", "y", 52);
    graph.addEdge("i", "p", 81);
    graph.addEdge("i", "t", 51);
    
    graph.addEdge("j", "x", 61);
    
    graph.addEdge("k", "w", 64);
    graph.addEdge("k", "n", 87);
    
    graph.addEdge("l", "c", 59);
    graph.addEdge("l", "b", 60);
    
    graph.addEdge("m", "f", 76);
    graph.addEdge("m", "r", 77);
    
    graph.addEdge("n", "k", 87);
    graph.addEdge("n", "z", 36);
    graph.addEdge("n", "w", 94);
    
    graph.addEdge("o", "p", 260);
    
    graph.addEdge("p", "o", 260);
    
    graph.addEdge("q", "n", 50);
    graph.addEdge("q", "k", 70);
    
    graph.addEdge("r", "g", 61);
    
    graph.addEdge("s", "t", 129);
    graph.addEdge("s", "p", 42);
    
    graph.addEdge("t", "p", 93);
    graph.addEdge("t", "i", 51);
    
    graph.addEdge("u", "c", 71);
    
    graph.addEdge("v", "h", 59);
    graph.addEdge("v", "e", 51);
    graph.addEdge("v", "x", 81);
    
    graph.addEdge("w", "k", 64);
    graph.addEdge("w", "q", 46);
    graph.addEdge("w", "y", 102);
    
    graph.addEdge("x", "r", 52);
    
    graph.addEdge("y", "w", 102);
    graph.addEdge("y", "s", 80);
    
    graph.addEdge("z", "q", 43);
    
    return graph;
  }
  
  
  @Test
  public void testNoCycles() {
    Graph<Integer> graph = new Graph<>(new UniEdgeFactory<>());
    for(int i = 0; i < 10; i++) {
      graph.addNode(i);
    }
    for(int i = 0; i < 9; i++) {
      graph.addEdge(i, i+1);
    }
    List<Path<Integer>> simpleCycles = graph.simpleCycles();
    assertTrue(simpleCycles.isEmpty());
    graph.addEdge(8, 0);
    simpleCycles = graph.simpleCycles();
    assertEquals(1,simpleCycles.size());
    
    graph.addEdge(5, 2);
    simpleCycles = graph.simpleCycles();
    assertEquals(2,simpleCycles.size());
  }

  @Test
  public void testCycles() {
  	Graph<Integer> graph = new Graph<>(new UniEdgeFactory<>());
  	int size = 9;
    for(int i = 0; i < size; i++) {
      graph.addNode(i);
    }
    for(int i = 0; i < size - 1; i++) {
      graph.addEdge(i, i + 1);
    }
    graph.addEdge(size - 1, 0);
    List<Path<Integer>> simpleCycles = graph.simpleCycles();
    assertEquals(1, simpleCycles.size());
  }
  
  @Test
  public void testCompleteCycles() {
  	Graph<Integer> graph = new Graph<>(new UniEdgeFactory<>());
  	int size = 10;
    for(int i = 0; i < size; i++) {
      graph.addNode(i);
    }
    for(int i = 0; i < size; i++) {
      for(int j = 0; j < size; j++) {
      	if(i != j) {
          graph.addEdge(i, j);
      	}
      }
    }
//    List<Path<Integer>> simpleCycles = graph.simpleCycles();
    List<List<Integer>> simpleCyclesByNodes = graph.simpleCyclesByNodes();
//    List<Path<Integer>> inefficientCycles = graph.inefficientCycles();
//    assertEquals(inefficientCycles.size(), simpleCyclesByNodes.size());
  }
  
  private int faculty(int n) {
  	return n == 1 ? 1 : n * faculty(n - 1);
  }
}
