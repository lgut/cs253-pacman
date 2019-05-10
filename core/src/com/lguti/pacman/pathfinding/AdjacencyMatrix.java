package com.lguti.pacman.pathfinding;

import com.lguti.pacman.helpers.collections.List;

public class AdjacencyMatrix {

    private float[][] matrix;
    private int size;

    public AdjacencyMatrix(int size){
        matrix = new float[size][size];
        this.size = size;
    }

    public void addUndirectedEdge(int from, int to, float weight){
        matrix[from][to] = weight;
        matrix[to][from] = weight;
    }
    public void addDirectedEdge(int to, int from, float weight){
        matrix[from][to] = weight;
    }
    public float getEdgeWeight(int x, int y){
        return matrix[x][y];
    }

    public List<Integer> getAdjacencyList(int sourceIndex){
        List<Integer> list = new List<>();
        for (int i = 0; i < size; i++) {
            if (matrix[sourceIndex][i] != 0){
                list.add(i);
            }
        }
        return list;
    }

    /*public int[] getAdjacencyList(int sourceIndex){
        int[] temp = new int[size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (matrix[sourceIndex][i] != 0){
                temp[i] = i;
                count++;
            }
        }
        int[] list = new int[count];
        for (int i = 0; i < count; i++) {
            list[i] = temp[i];
        }
        return list;
    }*/
}
