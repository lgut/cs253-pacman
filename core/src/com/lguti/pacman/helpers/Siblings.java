package com.lguti.pacman.helpers;

import com.lguti.pacman.actors.Cell;

import java.util.Iterator;
import com.lguti.pacman.helpers.collections.List;

public class Siblings implements Iterable<Cell> {

    public static final int ABOVE = 0;
    public static final int BELOW = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private Cell[] siblings;

    public Siblings(){
        siblings = new Cell[4];
    }

    public Cell[] toArray() {
        return siblings;
    }

    public void setSibling(int type, Cell c){
        siblings[type] = c;
    }

    public Cell getSibling(int type){
        return siblings[type];
    }

    public int getType(Cell cell){
        for (int i = 0; i < siblings.length; i++){
            if (cell.equals(siblings[i])){
                return i;
            }
        }
        return -1;
    }

    public int length(){
        return new SiblingsIterator(this).currentLengh();
    }

    @Override
    public Iterator<Cell> iterator() {
        return new SiblingsIterator(this);
    }
}

class SiblingsIterator implements Iterator<Cell>{

    private List<Cell> nonNullSiblings;

    public SiblingsIterator(Siblings sibs) {
        nonNullSiblings = new List<>();
        for (Cell sib :
                sibs.toArray()) {
            if (sib != null) {
                nonNullSiblings.add(sib);
            }
        }
    }

    public int currentLengh(){
        return nonNullSiblings.size();
    }

    @Override
    public boolean hasNext() {
        return nonNullSiblings.size() > 0;
    }

    @Override
    public Cell next() {
        return nonNullSiblings.shift();
    }
}
