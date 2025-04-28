package com.cruiser.algonetwork;

import com.cruiser.algonetwork.io.EdgeConfig;
import com.cruiser.algonetwork.io.GraphFileHandler;
import com.cruiser.algonetwork.model.Graph;

public class Main {
    public static void main(String[] args) {

        System.out.printf("Hello and welcome!");

        GraphFileHandler graphFileHandler = new GraphFileHandler();
        graphFileHandler.initialize();
//        System.out.println(graphFileHandler.getEdges()[2].from());
//        System.out.println(graphFileHandler.getEdges()[2].to());
//        System.out.println(graphFileHandler.getEdges()[2].capacity());

        Graph graph = new Graph(graphFileHandler.getNodeCount());
        for(EdgeConfig edge: graphFileHandler.getEdges()){
            graph.eddEdge(
                    edge.from(),
                    edge.to(),
                    edge.capacity()
            );
        }

        System.out.println(graph.getMaxFlow());


    }
}