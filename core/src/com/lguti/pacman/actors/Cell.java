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
