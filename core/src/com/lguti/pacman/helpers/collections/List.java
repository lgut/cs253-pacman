package com.lguti.pacman.helpers.collections;

import com.lguti.pacman.helpers.FindFunction;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class List<T> implements Iterable<T>, Collection<T> {
    private Node<T> head;
    private Node<T> tail;
    private int count;

    public List(){
        head = null;
        tail = null;
        count = 0;
    }

    private List(Node<T> head, Node<T> tail, int count){
        this.head = head;
        this.tail = tail;
        this.count = count;
    }

    @Override
    public int size() {
        return count;
    }

    private T search(int index){
        Node<T> curr = head;
        int i = 0;
        while (curr != null && i <=index) {
            if (i == index) {
                return curr.data;
            }
            curr = curr.next;
            i++;
        }
        return null;
    }

    private T revSearch(int index){
        Node<T> curr = tail;
        int i = count -1;
        while (curr != null && i >= index){
            if (i == index){
                return curr.data;
            }
            curr = curr.prev;
            i--;
        }
        return null;
    }

    /**
     * Returns a shallow copy of this list
     * @return
     */
    @Override
    public List<T> clone() {
        return new List<>(this.head, this.tail, this.count);
    }

    public T get(int index){
        if (!isEmpty()){
            if (index > count /2){
                return revSearch(index);
            }
            return search(index);
        }
        return null;
    }

    /**
     * Allows for duplicates
     * @param item
     * @return
     */
    @Override
    public boolean add(T item){

        Node<T> n = new Node<>(item);

        if (isEmpty()){
            head = n;
            tail = head;
        }else{
            tail.next = n;
            n.prev = tail;
            tail = n;
        }
        count++;
        return true;
    }

    private T unlink(Node<T> n){
        final T data = n.data;
        final Node<T> next = n.next;
        final Node<T> prev = n.prev;

        if (prev == null){
            head = next;
        }else {
            prev.next = next;
            n.prev = null;
        }

        if (next == null){
            tail = prev;
        }else {
            next.prev = prev;
            n.next = null;
        }

        n.data = null;
        count--;
        return data;
    }

    @Override
    public boolean remove(Object o) {
        if (!isEmpty()){
            if (count == 1){
                clear();
            }else {
                Node<T> curr = head;
                while (curr != null){
                    if (curr.data.equals(o)){
                        unlink(curr);
                        break;
                    }
                    curr = curr.next;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object item :
                collection) {
            if (contains(item) == false){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> collection) {
        collection.forEach(this::add);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        for (Object item :
                collection) {
            if (!contains(item)) {
                return false;
            }
        }
        collection.forEach(this::remove);
        return true;
    }

    /**
     * NOT IMPLEMENTED
     * @param collection
     * @return
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.count = 0;
    }

    /**
     * Remove from end of list
     * @return
     */
    public T pop(){
        if (isEmpty()){
            return null;
        }
        Node<T> n = tail;
        tail = tail.prev;
        count--;
        return n.data;
    }

    /**
     * Remove from beginning of list
     * @return
     */
    public T shift(){
        if (isEmpty()){
            return null;
        }else{
            Node<T> n = head;
            head = head.next;
            if (head != null){
                head.prev = null;
            }
            count--;
            return n.data;
        }
    }

    /**
     * Add to begining of list
     * @param item
     */
    public void unshift(T item){
        if (isEmpty()){
            add(item);
        }else{
            Node<T> n = new Node<>(item);
            n.next = head;
            head = n;
            count++;
        }
    }



    public boolean isEmpty(){

        if(head == null){
            tail = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (T item :
                this) {
            if (item.equals(0)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void forEach(Consumer<? super T> consumer) {
        for (T item :
                this) {
            consumer.accept(item);
        }
    }

    public T find(FindFunction<T> f){
        for (T item :
                this) {
            if (f.find(item)){
                return item;
            }
        }
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> curr = head;

            @Override
            public boolean hasNext() {
                return curr != null;
            }

            @Override
            public T next() {
                Node<T> n = curr;
                curr = curr.next;
                return n.data;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] t1s) {
        return null;
    }
}

class Node<T>{
    public Node<T> prev;
    public Node<T> next;
    public T data;

    public Node(T d){
        data = d;
        next = null;
        prev = null;
    }
}
