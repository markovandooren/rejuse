package org.rejuse.graph.test;

import junit.framework.TestCase;

import org.rejuse.graph.Edge;
import org.rejuse.graph.Graph;
import org.rejuse.graph.Node;
import org.rejuse.graph.Path;
import org.rejuse.graph.UniEdgeFactory;

/**
 * @author Marko van Dooren
 */
public class TestGraph extends TestCase {

  public TestGraph(String string) {
    super(string);
  }
  
  public static void main(String[] args) {
    junit.swingui.TestRunner.run(TestGraph.class);
  }

  /*
   * @see TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  public void testGraph() {
    Graph graph = new Graph();
    String a = "a";
    String b = "b";
    String c = "c";
    String d = "d";
    graph.addNode(a);
    assertTrue(graph.contains(a));
    assertTrue(graph.getNbNodes() == 1);
    assertTrue(graph.getNodes().size() == 1);
    assertTrue(graph.getObjects().contains(a));
    graph.addNode(b);
    assertTrue(graph.contains(b));
    assertTrue(graph.getNbNodes() == 2);
    assertTrue(graph.getNodes().size() == 2);
    assertTrue(graph.getObjects().contains(b));
    graph.addNode(c);
    assertTrue(graph.contains(c));
    assertTrue(graph.getNbNodes() == 3);
    assertTrue(graph.getNodes().size() == 3);
    assertTrue(graph.getObjects().contains(c));
    graph.addNode(a);
    assertTrue(graph.getNbNodes() == 3);
    Node nodeA = graph.getNode(a); 
    Node nodeB = graph.getNode(b);
    Node nodeC = graph.getNode(c);
    Edge edge = null;
    edge = graph.addEdge(a,b,1);
    assertTrue(edge.getFirst().getObject() == a);
    assertTrue(edge.getSecond().getObject() == b);
    assertTrue(edge.startsIn(graph.getNode(a)));
    assertTrue(edge.startsIn(graph.getNode(b)));
    assertTrue(edge.endsIn(graph.getNode(a)));
    assertTrue(edge.endsIn(graph.getNode(b)));
    assertTrue(edge.getWeight() == 1);
    assertTrue(nodeA.getNbStartEdges() == 1);
    assertTrue(nodeA.getNbEndEdges() == 1);
    assertTrue(nodeB.getNbStartEdges() == 1);
    assertTrue(nodeB.getNbEndEdges() == 1);
    assertTrue(nodeC.getNbStartEdges() == 0);
    assertTrue(nodeC.getNbEndEdges() == 0);
    assertTrue(graph.getNode(a).canReach(graph.getNode(b)));
    graph.addEdge(b,c,1);
    graph.addEdge(a,c,1.8);
    //Path shortest = nodeA.dijkstra(nodeC);
    //assertTrue(shortest.getLength() == 1.8);
    //assertTrue(shortest.getEnd() == nodeC);
    //assertTrue(shortest.getNbEdges() == 1);
  }
  
  public void testDijkstra() {
    Graph g = getBigGraph();
    Path p = null;
    
    print(g, "a", "w");
    print(g, "q", "i");
    print(g, "f", "b");
    print(g, "x", "u");
    print(g, "e", "l");
    print(g, "d", "n");
  }
  
  private void print(Graph g, Object from, Object to) {
    System.out.println(g.getNode(from).dijkstra(g.getNode(to)));
  }

  private Graph getBigGraph() {
    Graph graph = new Graph(new UniEdgeFactory());
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
}
