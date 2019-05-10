package com.lguti.pacman.pathfinding;

import com.lguti.pacman.helpers.collections.List;

public class Graph<T> {
    private AdjacencyMatrix matrix;
    private List<Vertex<T>> vertices;

    public Graph(List<Vertex<T>> vertices){
        this.vertices = vertices;
        int index = 0;
        for (Vertex<T> vertex :
                vertices) {
            vertex.setIndex(index);
            index++;
        }

        matrix = new AdjacencyMatrix(vertices.size());
    }

    public void createDirectedEdge(int from, int to){
        matrix.addDirectedEdge(from, to, 1);
    }
    public void createDirectedEdge(int from, int to, float weight){
        matrix.addDirectedEdge(from, to, weight);
    }

    public void createDirectedEdge(Vertex<T> from, Vertex<T> to){
        matrix.addDirectedEdge(from.getIndex(), to.getIndex(), 1.0f);
    }
    public void createDirectedEdge(Vertex<T> from, Vertex<T> to, float weight){
        matrix.addDirectedEdge(from.getIndex(), to.getIndex(), weight);
    }

    public void createUndirectedEdge(int from, int to){
        matrix.addUndirectedEdge(from, to, 1);
    }
    public void createUndirectedEdge(int from, int to, float weight){
        matrix.addUndirectedEdge(from, to, weight);
    }
    public void createUndirectedEdge(Vertex<T> from, Vertex<T> to){
        matrix.addUndirectedEdge(from.getIndex(), to.getIndex(), 1);
    }
    public void createUndirectedEdge(Vertex<T> from, Vertex<T> to, float weight){
        matrix.addUndirectedEdge(from.getIndex(), to.getIndex(), weight);
    }

    public List<Vertex<T>> getAdjacentVertices(int sourceIndex){
        List<Integer> adjacentIndices = matrix.getAdjacencyList(sourceIndex);
        List<Vertex<T>> adjacentVertices = new List<>();

        for (int vertexIndex :
                adjacentIndices) {
            adjacentVertices.add(vertices.get(vertexIndex));
        }
        return adjacentVertices;
    }
    public List<Vertex<T>> getAdjacentVertices(Vertex<T> source){
        return getAdjacentVertices(source.getIndex());
    }
    public float getEdgeWeight(Vertex<T> v1, Vertex<T> v2){
        return getEdgeWeight(v1.getIndex(),v2.getIndex());
    }
    public float getEdgeWeight(int i1, int i2){
        return matrix.getEdgeWeight(i1,i2);
    }


    public List<Vertex<T>> getVertices() {
        return vertices;
    }
}
