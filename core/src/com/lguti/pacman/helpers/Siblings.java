package com.lguti.pacman.helpers;

import com.lguti.pacman.actors.Cell;

public class Siblings {

    public static final int ABOVE = 0;
    public static final int BELOW = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    private Cell[] siblings;

    public Siblings(){
        siblings = new Cell[4];
    }

    public Cell[] getSiblings() {
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
}
