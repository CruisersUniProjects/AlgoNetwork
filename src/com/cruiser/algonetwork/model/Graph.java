package com.cruiser.algonetwork.model;

import java.util.ArrayList;

public class Graph {

    // To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
    // Handle with caution
    static final long INF = Long.MAX_VALUE / 2;

    // Might not be wanted when it comes to our case...
    private final int nodeCount;
    private final int sourceNode;
    private final int sinkNode;
    private int visitedToken = 1;
    private int[] visitedNodes;
    private boolean solved;
    private long maxFlow;

    // to remember the edges node wise
    private ArrayList<Edge>[] graph;

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


}
