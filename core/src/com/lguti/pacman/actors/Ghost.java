package com.lguti.pacman.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.lguti.pacman.helpers.Direction;
import com.lguti.pacman.helpers.collections.List;
import com.lguti.pacman.pathfinding.Graph;
import com.lguti.pacman.pathfinding.SearchAlgorithms;
import com.lguti.pacman.pathfinding.Vertex;

import java.util.Random;
import java.util.function.Function;

public class Ghost extends Actor {
    private float elapsedTime;
    private final float animDuration = 1f/15f;
    private Texture spritesheet;
    private Animation<TextureRegion> upAnim;
    private Animation<TextureRegion> downAnim;
    private Animation<TextureRegion> leftAnim;
    private Animation<TextureRegion> rightAnim;

    private Direction direction;
    private Cell currentPos;
    private boolean movementCompleted = true;
    public boolean isEdible = false;

    public Ghost(){
        spritesheet = new Texture("ghost.png");
        TextureRegion[][] temp = TextureRegion.split(spritesheet, 32, 32);
        createAnimations(temp[0]);

        direction = Direction.LEFT;
    }


    private void createAnimations(TextureRegion[] frames){
        // stupid lwjgl thing
        for(int i =0; i < frames.length;i++){
            frames[i].flip(false, true);
        }
        leftAnim = new Animation<TextureRegion>(animDuration, frames[0],frames[1]);
        rightAnim = new Animation<TextureRegion>(animDuration, frames[2],frames[3]);
        upAnim = new Animation<TextureRegion>(animDuration,frames[4],frames[5]);
        downAnim = new Animation<TextureRegion>(animDuration,frames[6],frames[7]);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currFrame = leftAnim.getKeyFrame(elapsedTime,true);

        batch.draw(currFrame,getX(),getY());
    }

    public void setPos(Cell c){
        currentPos = c;
        setX(c.getX());
        setY(c.getY());

    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void movementCompleted(boolean state){
        this.movementCompleted = state;
    }

    public void moveTo(Cell c){
        if (movementCompleted == true){
            this.addAction(Actions.sequence(
                    Actions.moveTo(c.getX(),c.getY(),0.5f),
                    new GhostCompletedMovementCallback()
            ));
            this.movementCompleted = false;
            currentPos = c;
        }
    }

    public void findPath(Graph<Cell> graph, Player player){
        List<Vertex<Cell>> vertices = graph.getVertices();
        Vertex<Cell> ghostVertex = vertices.find(v -> v.getData().equals(getCurrentPos()));
        Vertex<Cell> playerVertex = vertices.find(v -> v.getData().equals(player.getCurrentPos()));

        // this is not the shortest path but a path
        List<Vertex<Cell>> shitpath = SearchAlgorithms.breadthFirstSearchTo(graph,ghostVertex, playerVertex);
        List<Vertex<Cell>> path = SearchAlgorithms.Dijkstra(graph, ghostVertex, playerVertex);
        float cost = 0;
        for (Vertex<Cell> v :
                path) {
            cost+=v.getDistance();
            /*
            ghost will only react when the cost to reach them is at most 60
             */
            if (cost > 60){
                break;
            }
        }

        Cell nextPos = null;
        if (cost <= 60) {
            nextPos = path.get(Math.max(0, path.size() - 2)).getData();
        }else {
            // move to random valid sibling cell if player is not in range
            Random rand = new Random();

            Function<Cell, Boolean> checkInvalid = cell -> {
                switch (cell.getCellClass()){
                    case 1:
                    case 4:
                    case 7:
                    case 8:
                        return true;
                }
                return false;
            };

            do {
                int index = rand.nextInt(4);
                nextPos = currentPos.siblings().toArray()[index];
            } while (checkInvalid.apply(nextPos));
        }

        moveTo(nextPos);
    }

    public Cell getCurrentPos() {
        return currentPos;
    }
}

class GhostCompletedMovementCallback extends Action {
    @Override
    public boolean act(float delta) {
        Ghost context = (Ghost) target;
        context.movementCompleted(true);
        return true;
    }
}
