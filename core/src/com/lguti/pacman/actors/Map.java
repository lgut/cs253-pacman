package com.lguti.pacman.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//TODO: render texture map
public class Map extends Actor {
    //FIXME: graphs and such will bedend on this array
    int[][] tileLayer;
    public final int width = 21, height = 21;
    public static final int tileSize = 32;

    public Map(){
        tileLayer = new int[height][width];

        try{
            readTextureFile();
        }catch (FileNotFoundException e){

        }

    }

    private void readTextureFile() throws FileNotFoundException {
        File f = Gdx.files.internal("texturemap.txt").file();
        Scanner sc = new Scanner(f);
        int row = 0;
        while (sc.hasNext() && row < tileLayer.length){
            String str = sc.next();
            if (str.contains("*")){
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
