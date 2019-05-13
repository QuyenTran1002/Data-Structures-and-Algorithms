import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.HashMap;

/**
 * Your implementations of various graph algorithms.
 *
 * @author Quyen Tran
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Perform breadth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may import/use {@code java.util.Queue}, {@code java.util.Set},
     * {@code java.util.Map}, {@code java.util.List}, and any classes
     * that implement the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are traversing
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> breadthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input(start or graph) "
                    + "cant be null");
        }
        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException("The start does not exist "
                    + "in the graph");
        }

        Map<Vertex<T>, List<VertexDistancePair<T>>> adjacencyList =
                graph.getAdjacencyList();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> bfsList = new LinkedList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();

        queue.add(start);
        visitedSet.add(start);

        while (!queue.isEmpty()) {
            Vertex<T> currentVertex = queue.remove();
            bfsList.add(currentVertex);
            for (VertexDistancePair<T> v : adjacencyList.get(currentVertex)) {
                if (!visitedSet.contains(v.getVertex())) {
                    queue.add(v.getVertex());
                    visitedSet.add(v.getVertex());
                }
            }
        }

        return bfsList;

    }

    /**
     * Perform depth first search on the given graph, starting at the start
     * Vertex.  You will return a List of the vertices in the order that
     * you visited them.  Make sure to include the starting vertex at the
     * beginning of the list.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You MUST implement this method recursively.
     * Do not use any data structure as a stack to avoid recursion.
     * Implementing it any other way WILL cause you to lose points!
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are traversing
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    public static <T> List<Vertex<T>> depthFirstSearch(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input(start or graph) "
                    + "cant be null");
        }
        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException("The start does not exist "
                    + "in the graph");
        }
        List<Vertex<T>> dfsList = new LinkedList<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        return depthFirstSearchHelper(start, graph, dfsList, visitedSet);
    }

    /**
     * Heplper method for depthFirstSeach()
     *
     * @param start the Vertex you are starting at
     * @param graph the Graph you are traversing
     * @param dfsList the List of the vertices not in order 
     * @param visitedSet the Set of vertext that is visited
     * @param <T> the data type representing the vertices in the graph.
     * @return a List of vertices in the order that you visited them
     */
    private static <T> List<Vertex<T>> depthFirstSearchHelper(Vertex<T> start,
            Graph<T> graph, List<Vertex<T>> dfsList,
            Set<Vertex<T>> visitedSet) {
        dfsList.add(start);
        visitedSet.add(start);
        Map<Vertex<T>, List<VertexDistancePair<T>>> adjacencyList =
                graph.getAdjacencyList();
        for (VertexDistancePair<T> v : adjacencyList.get(start)) {
            if (!visitedSet.contains(v.getVertex())) {
                depthFirstSearchHelper(v.getVertex(),
                        graph, dfsList, visitedSet);
            }
        }
        return dfsList;
    }

    /**
     * Find the shortest distance between the start vertex and all other
     * vertices given a weighted graph where the edges only have positive
     * weights.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a distance of 0.
     *
     * There are guaranteed to be no negative edge weights in the graph.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are searching
     * @param <T> the data type representing the vertices in the graph.
     * @return a map of the shortest distances from start to every other node
     *         in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
            Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input(start or graph) "
                    + "cant be null");
        }
        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException("The start does not exist "
                    + "in the graph");
        }
        Map<Vertex<T>, Integer> shortestD = new HashMap<>();
        Map<Vertex<T>, List<VertexDistancePair<T>>> adjacencyList =
                graph.getAdjacencyList();
        PriorityQueue<VertexDistancePair<T>> pq = new PriorityQueue<>();

        pq.add(new VertexDistancePair<>(start, 0));

        for (Vertex<T> v : adjacencyList.keySet()) {
            if (!v.equals(start)) {
                shortestD.put(v, Integer.MAX_VALUE);
            } else {
                shortestD.put(v, 0);
            }
        }

        while (!pq.isEmpty()) {
            VertexDistancePair<T> currentVertex = pq.poll();

            for (VertexDistancePair<T> vDistance
                        : adjacencyList.get(currentVertex.getVertex())) {
                if (shortestD.get(vDistance.getVertex())
                        > currentVertex.getDistance()
                                + vDistance.getDistance()) {
                    shortestD.put(vDistance.getVertex(),
                        currentVertex.getDistance() + vDistance.getDistance());
                    VertexDistancePair<T> newVDistance = 
                        new VertexDistancePair<T>(vDistance.getVertex(),
                        currentVertex.getDistance() + vDistance.getDistance());
                    pq.add(newVDistance);
                }
            }
        }

        return shortestD;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return null.
     *
     * When exploring a Vertex, make sure you explore in the order that the
     * adjacency list returns the neighbors to you.  Failure to do so may
     * cause you to lose points.
     *
     * You may assume that for a given starting vertex, there will only be
     * one valid MST that can be formed. In addition, only an undirected graph
     * will be passed in.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null, or if
     *         {@code start} doesn't exist in the graph
     * @param start the Vertex you are starting at
     * @param graph the Graph you are creating the MST for
     * @param <T> the data type representing the vertices in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("The input(start or graph) "
                    + "cant be null");
        }
        if (!graph.getAdjacencyList().containsKey(start)) {
            throw new IllegalArgumentException("The start does not exist "
                    + "in the graph");
        }
        Set<Edge<T>> minSpanning = new HashSet<>();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Map<Vertex<T>, List<VertexDistancePair<T>>> adjacencyList =
                graph.getAdjacencyList();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>();

        if (graph.getAdjacencyList().size() <= 1) {
            return minSpanning;
        }

        while (minSpanning.size() != adjacencyList.size() - 1) {
            for (VertexDistancePair<T> vdPair : adjacencyList.get(start)) {
                Vertex<T> vertext = vdPair.getVertex();
                if (!visitedSet.contains(vertext)) {
                    pq.add(new Edge<>(start, vertext,
                            vdPair.getDistance(), false));
                }
            }
            if (pq.isEmpty()) {
                return null;
            }

            Edge<T> currentEdge = pq.poll();
            if (!(visitedSet.contains(currentEdge.getU())
                        && visitedSet.contains(currentEdge.getV()))) {
                minSpanning.add(currentEdge);
                visitedSet.add(currentEdge.getU());
                visitedSet.add(currentEdge.getV());
                start = currentEdge.getV();
            }
                
        }
        return minSpanning;
    }

}
