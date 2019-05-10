package com.lguti.pacman.helpers.collections;

public class Queue<T> {
    private List<T> q;

    public Queue(){
        q = new List<>();
    }

    public int getLength(){
        return q.size();
    }

    public void enqueue(T item){
        q.add(item);
    }
    public T dequeue(){
        return q.shift();
    }
}
