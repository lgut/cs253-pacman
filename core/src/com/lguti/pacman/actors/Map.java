package com.lguti.pacman.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.lguti.pacman.helpers.Tile;
import com.lguti.pacman.helpers.TileSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * TODO: plan for this class
 *      THe map is a group of actors
 *      Those actors are cells
 *      map should expose method to get cell at a grid coordinate
 *      Map should have a way for player actor to query what cell it is it
 *          based on its screen coordinate
 *          OR Player and ghost actors should kep track of they're current cell
 *          Thus cells should know about they're own coordinates in the map
 */

//TODO: render texture map
public class Map extends Group {
    //FIXME: graphs and such will bedend on this array
    int[][] tileLayer;
    public final int width = 21, height = 21;
    public static final int tileSize = 32;
    private TileSet tileSet;
    private Cell[][] mapState;

    public Map() {
        super();
        tileSet = new TileSet();
        tileLayer = new int[height][width];
        mapState = new Cell[tileLayer.length][tileLayer[0].length];

        try {
            readTextureFile();
        } catch (FileNotFoundException e) {

        }
        loadTileSet();
        initCells();
    }

    private void initCells(){
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++) {
                //TODO: Collison check with pacman
                Cell c;
                if (tileLayer[y][x] > 0){
                    c = new Cell(tileSet.getTile(Tile.Type.WALL), x * tileSize, y * tileSize);
                }else{
                    c = new Cell(tileSet.getTile(Tile.Type.PILL), x * tileSize, y * tileSize);
                }
                mapState[y][x] = c;
            }
        }
    }

    //TODO: add get cells method, Cells class?
    //TODO: add a way to get cell from at a position ex: getCell(x,y)

    //FIXME: no longer needed since this is a group
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++) {
                //TODO: Collison check with pacman
                if (tileLayer[y][x] > 0){
                    batch.draw(tileSet.getTile(Tile.Type.WALL).getTexture(), x * tileSize, y * tileSize, tileSize, tileSize);
                }else{
                    batch.draw(tileSet.getTile(Tile.Type.PILL).getTexture(), x * tileSize, y * tileSize, tileSize, tileSize);
                }
            }
        }

    }

    private void loadTileSet() {
        tileSet.setTile(new Tile("barrier.png", Tile.Type.WALL));
        tileSet.setTile(new Tile("pill.png", Tile.Type.PILL));
        tileSet.setTile(new Tile("nopill.png", Tile.Type.NOPILL));
        tileSet.setTile(new Tile("fruit.png", Tile.Type.FRUIT));

    }

    private void readTextureFile() throws FileNotFoundException {
        File f = Gdx.files.internal("texturemap.txt").file();
        Scanner sc = new Scanner(f);
        int row = 0;
        while (sc.hasNext() && row < tileLayer.length) {
            String str = sc.next();
            if (str.contains("*")) {
                break;
            }
            String[] chr = str.split("");
            for (int i = 0; i < tileLayer[0].length; i++) {
                tileLayer[row][i] = Integer.parseInt(chr[i]);
            }
            row++;
        }

    }


}
