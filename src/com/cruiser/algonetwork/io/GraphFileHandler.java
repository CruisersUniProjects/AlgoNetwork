package com.cruiser.algonetwork.io;

import java.io.*;
import java.util.Scanner;




public class GraphFileHandler {

    private String filePath;
    private int nodeCount;
    private EdgeConfig[] edges;

    public GraphFileHandler() {}

    public void initialize() {
        retrievePath();
        readNodeCount();
        readEdges();
    }

    public String getFilePath() {
        return filePath;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public EdgeConfig[] getEdges() {
        return edges;
    }

    private void retrievePath() {
        Scanner scanner = new Scanner(System.in);
        File file;
        while (true) {
            System.out.print("Enter the absolute path of the input file: ");
            String input = scanner.nextLine().trim();
            file = new File(input);
            if (file.exists() && file.isFile()) {
                this.filePath = input;
                break;
            } else {
                System.out.println("Invalid file path. Please try again.");
            }
        }
    }

    private void readNodeCount() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                this.nodeCount = Integer.parseInt(line.trim());
            } else {
                throw new RuntimeException("Input file is empty or badly formatted.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file during node count extraction.", e);
        }
    }

    private void readEdges() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();


            java.util.List<EdgeConfig> edgeList = new java.util.ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length != 3) {
                    throw new RuntimeException("Invalid edge line: " + line);
                }
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                long capacity = Long.parseLong(parts[2]);
                edgeList.add(new EdgeConfig(from, to, capacity));
            }

            this.edges = edgeList.toArray(new EdgeConfig[0]);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file during edges extraction.", e);
        }
    }


}