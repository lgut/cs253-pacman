package com.lguti.pacman.helpers;

public class TileSet {
    Tile[] set;
    Tile.Type[] types;

    public TileSet(){
        types = Tile.Type.values();
        set = new Tile[types.length];
    }

    public TileSet setTile(Tile tile){
        int index = findIndex(tile.getType());
        set[index] = tile;
        return this;
    }

    public Tile getTile(Tile.Type type){
        int index = findIndex(type);
        return set[index];
    }

    private int findIndex(Tile.Type t){
        int index = -1;
        for (int i = 0; i < types.length; i++) {
            if(types[i].equals(t)){
                index = i;
                break;
            }
        }
        return index;
    }
}
