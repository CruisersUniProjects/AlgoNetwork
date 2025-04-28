package com.cruiser.algonetwork;

import com.cruiser.algonetwork.io.EdgeConfig;
import com.cruiser.algonetwork.io.GraphFileHandler;
import com.cruiser.algonetwork.model.Graph;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello and welcome!");

        GraphFileHandler graphFileHandler = new GraphFileHandler();
        graphFileHandler.initialize();

        Graph graph = new Graph(graphFileHandler.getNodeCount());
        for(EdgeConfig edge: graphFileHandler.getEdges()){
            graph.eddEdge(
                    edge.from(),
                    edge.to(),
                    edge.capacity()
            );
        }

        graph.printMaxFlow(askPrintAdditionalInfo());


    }

    public static boolean askPrintAdditionalInfo() {
        Scanner scanner = new Scanner(System.in);
        String choice;
        do {
            System.out.println("Do you want to print additional information?");
            System.out.println("  y: show augmented paths and final edge flows");
            System.out.println("  n: only print the maximum flow");
            System.out.print(":  ");
            choice = scanner.nextLine().trim();
        } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));
        return choice.equalsIgnoreCase("y");
    }
}