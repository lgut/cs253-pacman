package com.lguti.pacman.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.lguti.pacman.helpers.Direction;
import com.lguti.pacman.helpers.Siblings;
import com.lguti.pacman.helpers.Tile;

public class Player extends Actor {

    private Texture spritesheet;
    private Animation<TextureRegion> upAnim;
    private Animation<TextureRegion> downAnim;
    private Animation<TextureRegion> leftAnim;
    private Animation<TextureRegion> rightAnim;
    private float elapsedTime;
    private final float animDuration = 1f/15f;

    private Direction direction;
    private Cell currentPos;
    private boolean movementCompleted = true;

    public Player(){
        spritesheet = new Texture("pacman.png");
        TextureRegion[][] temp = TextureRegion.split(spritesheet, 32, 32);
        this.direction = Direction.LEFT;

        createAnimations(temp[0]);
        this.addListener(new WASDListener(this));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();

        TextureRegion currFrame = upAnim.getKeyFrame(elapsedTime,true);
        Cell cellToMoveTo = null;

        switch (direction){
            case UP:
                currFrame = upAnim.getKeyFrame(elapsedTime,true);
                cellToMoveTo= currentPos.siblings().getSibling(Siblings.ABOVE);
                break;
            case DOWN:
                currFrame = downAnim.getKeyFrame(elapsedTime,true);
                cellToMoveTo = currentPos.siblings().getSibling(Siblings.BELOW);
                break;
            case LEFT:
                currFrame = leftAnim.getKeyFrame(elapsedTime,true);
                cellToMoveTo = currentPos.siblings().getSibling(Siblings.LEFT);
                break;
            case RIGHT:
                currFrame = rightAnim.getKeyFrame(elapsedTime,true);
                cellToMoveTo = currentPos.siblings().getSibling(Siblings.RIGHT);
                break;
        }
        // what to do when a wall is hit
        if (cellToMoveTo == null || cellToMoveTo.getType().equals(Tile.Type.WALL)){

            Siblings sibs = currentPos.siblings();

            for (Cell sib :
                    sibs) {
                if(sib != null && sib.getType() != Tile.Type.WALL){
                    cellToMoveTo = sib;
                    int type = sibs.getType(sib);
                    switch (type){
                        case Siblings.ABOVE:
                            currFrame = upAnim.getKeyFrame(elapsedTime,true);
                            break;
                        case Siblings.BELOW:
                            currFrame = downAnim.getKeyFrame(elapsedTime,true);
                            break;
                        case Siblings.LEFT:
                            currFrame = leftAnim.getKeyFrame(elapsedTime,true);
                            break;
                        case Siblings.RIGHT:
                            currFrame = rightAnim.getKeyFrame(elapsedTime,true);
                            break;
                    }
                    break;
                }
            }


        }
        moveTo(cellToMoveTo);
        batch.draw(currFrame,getX(),getY());
    }

    private void createAnimations(TextureRegion[] frames){
        // stupid lwjgl thing
        for(int i =0; i < frames.length;i++){
            frames[i].flip(false, true);
        }
        rightAnim = new Animation<TextureRegion>(animDuration, frames[0],frames[1],frames[2]);
        leftAnim = new Animation<TextureRegion>(animDuration, frames[3],frames[4],frames[5]);
        upAnim = new Animation<TextureRegion>(animDuration,frames[6],frames[7],frames[8]);
        downAnim = new Animation<TextureRegion>(animDuration,frames[9],frames[10],frames[11]);
    }

    public void moveTo(Cell c){
        if (movementCompleted == true){
            this.addAction(Actions.sequence(
                    Actions.moveTo(c.getX(),c.getY(),0.5f),
                    new PlayerCompletedMovementCallback(c)
            ));
            this.movementCompleted = false;
            //currentPos = c;
        }
        //this.addAction(Actions.moveToAligned());
    }

    public void movementCompleted(boolean state){
        this.movementCompleted = state;
    }

    public void setPos(Cell c){
        currentPos = c;
        setX(c.getX());
        setY(c.getY());

    }

    public Cell getCurrentPos() {
        return currentPos;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}

class WASDListener extends InputListener {

    private Player player;

    public WASDListener(Player p){
        player = p;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        switch (keycode){
            case Input.Keys.W:
                player.setDirection(Direction.UP);
                break;
            case Input.Keys.A:
                player.setDirection(Direction.LEFT);
                break;
            case Input.Keys.S:
                player.setDirection(Direction.DOWN);
                break;
            case Input.Keys.D:
                player.setDirection(Direction.RIGHT);
                break;
        }
        return true;
    }
}

class PlayerCompletedMovementCallback extends Action {

    private Cell cell;

    public PlayerCompletedMovementCallback(Cell c){
        this.cell = c;
    }

    @Override
    public boolean act(float delta) {
        Player context = (Player) target;
        context.setPos(cell);
        context.movementCompleted(true);
        return true;
    }
}
