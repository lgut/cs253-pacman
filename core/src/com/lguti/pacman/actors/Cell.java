package com.lguti.pacman.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lguti.pacman.helpers.Bounds;
import com.lguti.pacman.helpers.Siblings;
import com.lguti.pacman.helpers.Tile;

public class Cell extends Actor {

    private Tile tile;
    private int cellClass;
    private Siblings siblings;

    public enum EdgeWall {
        LEFT,
        RIGHT,
        UPPER,
        LOWER
    }

    public Cell(Tile t, int x, int y, int cellClass){
        super();
        siblings = new Siblings();
        tile = t;
        setX(x);
        setY(y);
        setTile(t);
        setCellClass(cellClass);
    }

    public void setCellClass(int cellClass) {
        this.cellClass = cellClass;
    }

    public int getCellClass() {
        return cellClass;
    }

    public void setTile(Tile t){
        Texture tex = tile.getTexture();
        setBounds(getX(), getY(), tex.getWidth(), tex.getHeight());
    }

    public EdgeWall isEdgeWall(){
        Cell leftSib = siblings.getSibling(Siblings.LEFT);
        Cell rightSib = siblings.getSibling(Siblings.RIGHT);
        Cell aboveSib = siblings.getSibling(Siblings.ABOVE);
        Cell belowSib = siblings.getSibling(Siblings.BELOW);

        if (leftSib == null || leftSib.getCellClass() == 7){
            return EdgeWall.LEFT;
        }

        if (rightSib == null || rightSib.getCellClass() == 7){
            return EdgeWall.RIGHT;
        }
        if (aboveSib == null || aboveSib.getCellClass() == 7){
            return  EdgeWall.UPPER;
        }
        if (belowSib == null || belowSib.getCellClass() == 7){
            return EdgeWall.LOWER;
        }
        return null;
    }

    public Tile.Type getType(){
        return tile.getType();
    }

    public Bounds getBounds(){
        Bounds b = new Bounds();
        b.x = getX();
        b.y = getY();
        b.height = getHeight();
        b.width = getWidth();
        return b;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(tile.getTexture(), getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public Cell hit(float x, float y, boolean touchable) {
        return (Cell) super.hit(x, y, touchable);
    }

    public Siblings siblings(){
        return this.siblings;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
