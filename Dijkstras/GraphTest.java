// --== CS400 File Header Information ==--
// Name:			Eric Choi
// Email: 			hchoi256@wisc.edu
// Team: 			ID
// TA:  			Mu Cai
// Lecturer: 		Gary
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the implementation of CS400Graph for the individual component of
 * Project Three: the implementation of Dijsktra's Shortest Path algorithm.
 */
public class GraphTest {
	private static CS400Graph<Integer> graph;

	@BeforeEach
	/**
	 * Instantiate graph from last week's shortest path activity.
	 */
	public void createGraph() {
		graph = new CS400Graph<>();
		// insert verticies 0-9
		for (int i = 0; i < 10; i++)
			graph.insertVertex(i);
		// insert edges from Week 08. Dijkstra's Activity
		graph.insertEdge(0, 2, 1);
		graph.insertEdge(1, 7, 2);
		graph.insertEdge(1, 8, 4);
		graph.insertEdge(2, 4, 4);
		graph.insertEdge(2, 6, 3);
		graph.insertEdge(3, 1, 6);
		graph.insertEdge(3, 7, 2);
		graph.insertEdge(4, 5, 4);
		graph.insertEdge(5, 0, 2);
		graph.insertEdge(5, 1, 4);
		graph.insertEdge(5, 9, 1);
		graph.insertEdge(6, 3, 1);
		graph.insertEdge(7, 0, 3);
		graph.insertEdge(7, 6, 1);
		graph.insertEdge(8, 9, 3);
		graph.insertEdge(9, 4, 5);
	}

	/**
	 * Checks the distance/total weight cost from the vertex labelled 0 to 8 (should
	 * be 15), and from the vertex labelled 9 to 8 (should be 17).
	 */
	@Test
	public void providedTestToCheckPathCosts() {
		assertTrue(graph.getPathCost(0, 8) == 15);
		assertTrue(graph.getPathCost(9, 8) == 17);
	}

	/**
	 * Checks the ordered sequence of data within vertices from the vertex labelled
	 * 0 to 8, and from the vertex labelled 9 to 8.
	 */
	@Test
	public void providedTestToCheckPathContents() {

		assertTrue(graph.shortestPath(0, 8).toString().equals("[0, 2, 6, 3, 1, 8]"));
		assertTrue(graph.shortestPath(9, 8).toString().equals("[9, 4, 5, 1, 8]"));
	}

	/**
	 * #10 Add an extra test method to confirm that the distance that you reported
	 * as #3 from last week's activity is correct.
	 */
	@Test
	public void testCostLastWeekActivity() {
		System.out.println(graph.getPathCost(5, 8));
		assertTrue(graph.getPathCost(5, 8) == 8);
	}

	/**
	 * #11 Add an extra test method to confirm the sequence of values along the path
	 * from your source node to this same end node (the end node that is furthest
	 * from your source node)
	 */
	@Test
	public void testSeqLastWeekActivity() {
		System.out.println(graph.shortestPath(5, 8).toString());
		assertTrue(graph.shortestPath(5, 8).toString().equals("[5, 1, 8]"));
	}

	/**
	 * #12 Add at least two more test methods to this class to help convince
	 * yourself of the correctness of your implementation.
	 */
	@Test
	public void extraTestOne() {
		// Test the case when the start and end node are both same.
		// The resultant path should only contain the start node, not throwing any
		// error.
		System.out.println(graph.shortestPath(0, 0).toString());
		assertTrue(graph.shortestPath(0, 0).toString().equals("[0]"));
	}

	@Test
	public void extraTestTwo() {
		// Test the case when the input does not exist in this graph.
		// The application should throw NoSuchElementException, then
		// return false for JUnit testing.
		System.out.println(graph.getPathCost(6, 11));
		assertTrue(graph.getPathCost(6, 11) == 0);
	}

	@Test
	public void extraTestThree() {
		// Test the case when no directed edge exists between the input nodes
		// The application should throw NoSuchElementException, then
		// return false for JUnit testing.
		graph.insertVertex(15);
		graph.insertEdge(15, 0, 1);
		System.out.println(graph.shortestPath(0, 15).toString());
		assertTrue(graph.getPathCost(0, 15) == 1);
	}

	@Test
	public void extraTestFour() {
		// Add an integer that is two time bigger than the largest of values that exist
		// in this graph so that
		// the graph includes blank data between the newly added one and the existing
		// one, such as 10, 11, 12,..., and 17.
		graph.insertVertex(18);
		graph.insertEdge(18, 0, 1);
		System.out.println(graph.shortestPath(18, 0).toString());
		assertTrue(graph.getPathCost(18, 0) == 1);
	}
}