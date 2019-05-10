package com.lguti.pacman.pathfinding;

import com.lguti.pacman.helpers.collections.List;
import com.lguti.pacman.helpers.collections.Queue;

public class SearchAlgorithms {

    public static <T> List<Vertex<T>> Dijkstra(Graph<T> graph, Vertex<T> source, Vertex<T> goal){
        if (source.equals(goal)){
            List<Vertex<T>> ls = new List<>();
            ls.add(source);
            return ls;
        }

        List<Vertex<T>> unfinishedVertices = new List<>();
        for (Vertex<T> vertex :
                graph.getVertices()) {
            vertex.setDistance(Integer.MAX_VALUE);
            vertex.setParent(null);
            unfinishedVertices.add(vertex);
        }
        source.setDistance(0);

        while (unfinishedVertices.size() > 0){

            Vertex<T> vertex = getClosestVertex(unfinishedVertices);
            unfinishedVertices.remove(vertex);

            if (vertex.equals(goal)){
                return getPathToSource(vertex);
            }

            for (Vertex<T> adjVertex :
                    graph.getAdjacentVertices(vertex)) {

                float cost = vertex.getDistance() + graph.getEdgeWeight(vertex, adjVertex);

                if (adjVertex.getDistance() > cost){
                    adjVertex.setDistance(cost);
                    adjVertex.setParent(vertex);
                }
            }
        }
        return null;
    }

    private static <T> Vertex<T> getClosestVertex(List<Vertex<T>> list){
        List<Vertex<T>> ls = list.clone();
        Vertex<T> candidate = ls.shift();

        for (Vertex<T> vertex :
                ls) {
            if (vertex.getDistance() < candidate.getDistance()){
                candidate = vertex;
            }
        }
        return candidate;
    }

    /**
     * Will return a list that begins with the goal vertex and ends with the source vertex
     * Will return null if no path is found
     * @param graph
     * @param source
     * @param goal
     * @param <T>
     * @return
     */
    public static <T> List<Vertex<T>> breadthFirstSearchTo(Graph<T> graph, Vertex<T> source, Vertex<T> goal) {
        if (source.equals(goal)) {
            List<Vertex<T>> ls = new List<>();
            ls.add(source);
            return ls;
        }
        for (Vertex<T> vertex :
                graph.getVertices()) {
            vertex.setParent(null);
            vertex.setDistance(0);
            vertex.isVisited(false);
        }

        Queue<Vertex<T>> queue = new Queue<>();
        queue.enqueue(source);

        while (queue.getLength() > 0) {
            Vertex<T> vertex = queue.dequeue();
            List<Vertex<T>> neighbors = graph.getAdjacentVertices(vertex);

            for (Vertex<T> neighbor :
                    neighbors) {

                if (!neighbor.isVisited()) {
                    neighbor.setParent(vertex);
                    neighbor.setDistance(vertex.getDistance() + graph.getEdgeWeight(vertex, neighbor));
                    neighbor.isVisited(true);

                    if (neighbor.equals(goal)) {
                        return getPathToSource(neighbor);
                    }
                    queue.enqueue(neighbor);
                }
            }
            vertex.isVisited(true);
        }

        //no path exists
        return null;
    }

    private static <T> List<Vertex<T>> getPathToSource(Vertex<T> from){
        List<Vertex<T>> path = new List<>();
        Vertex<T> next = from;

        while (next != null){
            path.add(next);
            next = next.getParent();
        }
        return path;
    }
}
