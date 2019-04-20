package com.lguti.pacman.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class Tile {

    public static enum Type {
        WALL,
        PILL,
        NOPILL,
        FRUIT;
    }

    private Type type;
    Texture texture;

    public Tile(String internalPath, Type type){

        this.type = type;
        this.texture = new Texture(internalPath);
    }

    public Type getType() {
        return type;
    }

    public Texture getTexture(){
        return texture;
    }
}
