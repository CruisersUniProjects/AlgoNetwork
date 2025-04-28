package com.cruiser.algonetwork.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Queue;

public class Graph {

    // To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
    // Handle with caution
//    static final long INF = Long.MAX_VALUE / 2;

    // Might not be wanted when it comes to our case...
    private final int nodeCount;
    private final int sourceNode;
    private final int sinkNode;
    private int visitedToken = 1;
    private final int[] visitedNodes;
//    private boolean solved;
    private long maxFlow;

    // to remember the edges node wise
    private final ArrayList<Edge>[] graph;

    @SuppressWarnings("unchecked") // Suppressing warning for generic array creation.
    public Graph(int nodeCount){ // Java does not allow direct creation of generic arrays (like ArrayList<Edge>[])
        if (nodeCount < 1) throw new IllegalArgumentException(
                "Node count must be greater thane one"
        );
        this.nodeCount = nodeCount;
        sourceNode = 0;
        sinkNode = nodeCount - 1;
        graph = new ArrayList[nodeCount];
        for(int i = 0; i < nodeCount; i++){
            graph[i] = new ArrayList<>(); // Java can imply the type for diamond operator
        }
        visitedNodes = new int[nodeCount];
    }

    public void eddEdge(int from, int to,long capacity){
        if(from > nodeCount || to > nodeCount) throw new IllegalArgumentException(
                "Node not found with the given identities"
        );
        try {
            Edge e1 = new Edge(from, to, capacity);
            Edge e2 = new Edge(to, from);
            e1.setResidual(e2);
            e2.setResidual(e1);
            graph[from].add(e1);
            graph[to].add(e2);

        } catch(IllegalArgumentException e){
            System.out.printf("Edge creation failed for the edge from %d to %d %nReason: capacity can't be negative", from, to);
        }
    }

    public void printMaxFlow(boolean print){

        if (print) System.out.println("\n   --- Steps of computing the max flow ---\n");

        long startNanos = System.nanoTime();
        long flow;
        int iteration = 1;
        do{
            visitedToken++; //resetting the visited nodes array
            flow = bfs(print, iteration);
            maxFlow += flow;
            iteration++;
        }while(flow != 0);
        long elapsedNanos = System.nanoTime() - startNanos;
        System.out.println("\n   --- Base information ---\n");
        System.out.printf("Computation time: %.3f ms%nMaxFlow: %d%nNode Count: %d%n",
                elapsedNanos / 1_000_000.0,
                maxFlow,
                nodeCount
        );

        if (print) printEdgeFlows();

    }

    private long bfs(boolean print, int iteration){

        Queue<Integer> queue = new ArrayDeque<>(nodeCount);
        visitedNodes[sourceNode] = visitedToken;
        queue.offer(sourceNode); // Throws an exception if the queue overflows...

        Edge[] previous = new Edge[nodeCount];
        while(!queue.isEmpty()){
            int node = queue.poll();
            if (node == sinkNode) break;
            for(Edge edge: graph[node]){
                long capacity = edge.getRemainingCapacity();
                if((capacity > 0) && (visitedNodes[edge.getTo()] != visitedToken)){
                    visitedNodes[edge.getTo()] = visitedToken;
                    previous[edge.getTo()] = edge;
                    queue.offer(edge.getTo());
                }
            }
        }

        if (previous[sinkNode] == null) return 0;

        long bottleNeck = Long.MAX_VALUE;

        for(Edge edge = previous[sinkNode]; edge != null; edge = previous[edge.getFrom()])
            bottleNeck = Math.min(bottleNeck, edge.getRemainingCapacity());

        for(Edge edge = previous[sinkNode]; edge != null; edge = previous[edge.getFrom()])
            edge.augment(bottleNeck);


        if (print) {
            System.out.print("Step: " + iteration + " | Bottleneck: " + bottleNeck + " | Path: ");

            Deque<Edge> stack = new ArrayDeque<>();
            for (Edge e = previous[sinkNode]; e != null; e = previous[e.getFrom()]) {
                stack.push(e);
            }

            System.out.print(sourceNode);
            while (!stack.isEmpty()) {
                Edge e = stack.pop();
                System.out.print(" -> " + e.getTo());
            }
            System.out.println();
        }

        return bottleNeck;
    }

    public void printEdgeFlows() {
        System.out.println("\n   --- flows on original edges ---\n");
        for (int u = 0; u < nodeCount; u++) {
            for (Edge e : graph[u]) {
                if (!e.isResidual()) {
                    System.out.printf("from: %d | to: %d | flow: %d%n", e.getFrom(), e.getTo(), e.getFlow());
                }
            }
        }
    }



}
