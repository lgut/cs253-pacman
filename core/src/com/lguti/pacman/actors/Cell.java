package com.lguti.pacman.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lguti.pacman.helpers.Bounds;
import com.lguti.pacman.helpers.Tile;

public class Cell extends Actor {

    Tile tile;

    public Cell(Tile t, int x, int y){
        super();
        tile = t;
        setX(x);
        setY(y);
        setTile(t);
    }

    public void setTile(Tile t){
        Texture tex = tile.getTexture();
        setBounds(getX(), getY(), tex.getWidth(), tex.getHeight());
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
}
