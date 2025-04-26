package com.cruiser.algonetwork.model;
public class Edge {
    private final int from;
    private final int to;
    private final long capacity;
    private long flow;
    private Edge residual;



    public Edge(int from, int to){
        this.from = from;
        this.to = to;
        this.capacity = 0;
    }


    public Edge(int from, int to, long capacity){
        if (capacity < 0) throw new IllegalArgumentException("Capacity can't be negative");
        this.capacity = capacity;
        this.from = from;
        this.to = to;

    }


    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public long getCapacity() {
        return capacity;
    }

    public long getFlow() {
        return flow;
    }

    public Edge getResidual() {
        return residual;
    }

    public void setFlow(long flow) {
        this.flow = flow;
    }

    public void setResidual(Edge residual) {
        //Maintaining constancy by only allowing one assignment of an edge
        if (this.residual != null) return;
        this.residual = residual;
    }

    public boolean isResidual() { return capacity == 0;}

    public long getRemainingCapacity(){
        return capacity - flow;
    }
    public void addAugmentedPath(long bottleNeck){
        flow += bottleNeck;
        residual.flow -= bottleNeck;
    }

    //Add the toString method here...

}