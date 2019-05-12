package com.lguti.pacman.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lguti.pacman.helpers.Tile;
import com.lguti.pacman.helpers.TileSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * TODO: plan for this class
 * THe map is a group of actors
 * Those actors are cells
 * map should expose method to get cell at a grid coordinate
 * Map should have a way for player actor to query what cell it is it
 * based on its screen coordinate
 * OR Player and ghost actors should kep track of they're current cell
 * Thus cells should know about they're own coordinates in the map
 */

//TODO: render texture map
public class Map extends Actor {
    int[][] tileLayer;
    public final int width = 21, height = 21;
    public static final int tileSize = 32;
    private TileSet tileSet;
    private Cells mapState;

    public Map() {
        super();
        tileSet = new TileSet();
        tileLayer = new int[height][width];
        mapState = new Cells(height, width);

        try {
            readTextureFile();
        } catch (FileNotFoundException e) {

        }
        loadTileSet();
        this.mapState = renderCells(this.tileLayer);
    }

    private Cells renderCells(int[][] tileLayer) {
        Cells newMapState = new Cells(height,width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                //TODO: Collison check with pacman
                Cell c;
                switch (tileLayer[y][x]) {
                    case 0:
                        c = new Cell(tileSet.getTile(Tile.Type.PILL), x * tileSize, y * tileSize, 0);
                        break;
                    case 1:
                        c = new Cell(tileSet.getTile(Tile.Type.WALL), x * tileSize, y * tileSize, 1);
                        break;
                    case 2:
                        // ghost walls not implemented
                        c = new Cell(tileSet.getTile(Tile.Type.WALL), x * tileSize, y * tileSize, 2);
                        break;
                    case 3:
                        c = new Cell(tileSet.getTile(Tile.Type.NOPILL), x * tileSize, y * tileSize, 3);
                        break;
                    case 4: // player only path
                        c = new Cell(tileSet.getTile(Tile.Type.NOPILL), x * tileSize, y * tileSize, 4);
                        break;
                    case 5: // ghost only path
                        c = new Cell(tileSet.getTile(Tile.Type.NOPILL), x * tileSize, y * tileSize,5);
                        break;
                    case 6:
                        c = new Cell(tileSet.getTile(Tile.Type.FRUIT), x * tileSize, y * tileSize,6);
                        break;
                    case 7: // blank space not a path
                        c = new Cell(tileSet.getTile(Tile.Type.NOPILL), x * tileSize, y * tileSize,7);
                        break;
                    case 8: // teleport loop
                        c = new Cell(tileSet.getTile(Tile.Type.NOPILL), x * tileSize, y * tileSize,8);
                        break;
                    case 9: // startpos
                        c = new Cell(tileSet.getTile(Tile.Type.NOPILL), x*tileSize,y*tileSize,9);
                        break;
                    default:
                        // dont know what to do here really, default should never be used
                        c = new Cell(tileSet.getTile(Tile.Type.NOPILL), x * tileSize, y * tileSize,7);
                        break;
                }
                newMapState.set(x,y,c);
            }
        }
        newMapState.establishSiblings();
        return newMapState;
    }

    public Cells getState(){
        return this.mapState;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        mapState.draw(batch, parentAlpha);
    }

    @Override
    public Cell hit(float x, float y, boolean touchable) {
        // returns the first
        return mapState.hit(x, y, touchable);
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
