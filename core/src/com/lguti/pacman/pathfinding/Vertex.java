package com.lguti.pacman.pathfinding;

public class Vertex<T> {
    private int index;

    private T data;

    // keeping track during search
    private Vertex<T> parent;
    private boolean visited;
    private float distance;

    public Vertex(T data){
        this.data = data;
        index = -1;
    }

    public Vertex<T> getParent(){
        return this.parent;
    }

    public void setParent(Vertex<T> parent) {
        this.parent = parent;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public boolean isVisited() {
        return visited;
    }
    public void isVisited(boolean b){
        this.visited = b;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
