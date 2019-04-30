package com.lguti.pacman;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lguti.pacman.actors.Cell;
import com.lguti.pacman.actors.Map;
import com.lguti.pacman.actors.Player;

public class MainStage extends Stage {

    Player player;
    Map map;

    public MainStage(Map m, Player p, ScreenViewport v){
        super(v);
        map = m;
        player = p;
        addActor(map);
        addActor(player);
        // starting possition
        player.setPos(map.getState().find((Cell c) -> { return c.getCellClass() == 9; }));
    }

    @Override
    public boolean keyDown(int keyCode) {
        // forward wasd keys events to player
        switch (keyCode){
            case Input.Keys.A:
            case Input.Keys.S:
            case Input.Keys.W:
            case Input.Keys.D:
                this.setKeyboardFocus(player);
                break;
        }

        return super.keyDown(keyCode);
    }
}
