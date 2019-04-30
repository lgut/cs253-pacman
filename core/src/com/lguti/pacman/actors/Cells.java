package com.lguti.pacman.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.lguti.pacman.helpers.Siblings;

import java.util.Iterator;
import java.util.NoSuchElementException;




public class Cells extends Group implements Iterable<Cell> {
    private Cell[][] matrix;
    public interface FindFunction{
        boolean find(Cell c);
    }

    public Cells(int height, int width){
        matrix = new Cell[height][width];
    }

    public void set(int x, int y, Cell c){
        if(matrix[y][x] != null){
            removeActor(matrix[y][x]);
        }
        matrix[y][x] = c;
        addActor(c);
    }

    public Cell get(int x, int y){
        return matrix[y][x];
    }

    @Override
    public Cell hit(float x, float y, boolean touchable) {
        return (Cell) super.hit(x, y, touchable);
    }

    @Override
    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int currentX = 0;
            int currentY = 0;
            @Override
            public boolean hasNext() {
                if(currentX >= matrix[0].length){
                    currentX = 0;
                    currentY++;
                }
                return !isOutofBounds();
            }

            @Override
            public Cell next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }

                Cell c = get(currentX, currentY);
                currentX++;
                return c;
            }

            @Override
            public void remove() {

            }

            private boolean isOutofBounds(){
                if(currentY >= matrix.length){
                    return true;
                }
                return false;
            }
        };
    }

    public Cell find(FindFunction f){
        for (Cell c :
                this) {
            if (f.find(c) == true){
                return c;
            }
        }
        return null;
    }

    public void establishSiblings(){
        for (int y = 0; y < matrix.length; y++){
            for (int x = 0; x < matrix[0].length; x++) {
                Cell curr = matrix[y][x];
                if(y == 0){
                    curr.siblings().setSibling(Siblings.BELOW,matrix[y+1][x]);
                    curr.siblings().setSibling(Siblings.ABOVE,null);

                }else if(y == matrix.length - 1) {
                    curr.siblings().setSibling(Siblings.BELOW,null);
                    curr.siblings().setSibling(Siblings.ABOVE,matrix[y-1][x]);
                }else {
                    curr.siblings().setSibling(Siblings.ABOVE,matrix[y-1][x]);
                    curr.siblings().setSibling(Siblings.BELOW,matrix[y+1][x]);
                }

                if (x == 0){
                    curr.siblings().setSibling(Siblings.RIGHT,matrix[y][x+1]);
                    curr.siblings().setSibling(Siblings.LEFT,null);
                }else if (x == matrix[0].length -1){
                    curr.siblings().setSibling(Siblings.RIGHT,null);
                    curr.siblings().setSibling(Siblings.LEFT,matrix[y][x-1]);
                }else{
                    curr.siblings().setSibling(Siblings.RIGHT,matrix[y][x+1]);
                    curr.siblings().setSibling(Siblings.LEFT,matrix[y][x-1]);
                }

            }
        }
    }

    //TODO: foreach method
    //TODO: map method
    //TODO: getAdjacencyMatrix
}


