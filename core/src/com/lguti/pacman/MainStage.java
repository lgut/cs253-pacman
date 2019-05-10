package com.lguti.pacman;

import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lguti.pacman.actors.*;
import com.lguti.pacman.helpers.Siblings;
import com.lguti.pacman.helpers.Tile;
import com.lguti.pacman.helpers.collections.List;
import com.lguti.pacman.pathfinding.Graph;
import com.lguti.pacman.pathfinding.Vertex;

import java.util.Iterator;
import java.util.function.Function;

public class MainStage extends Stage {

    Player player;
    Map map;
    Ghost ghost;

    public MainStage(Map m, Player p, Ghost g, ScreenViewport v){
        super(v);
        map = m;
        player = p;
        ghost = g;

        addActor(map);
        addActor(ghost);
        addActor(player);

        // starting possition
        player.setPos(map.getState().find((Cell c) -> { return c.getCellClass() == 9; }));
        ghost.setPos(map.getState().find((Cell c) -> { return c.getCellClass() == 0; }));
    }

    @Override
    public void draw() {
        update();


        // pass new graph to ghost before each render

        super.draw();
    }

    public void update(){
        detectHits();
        Graph<Cell> graph = generateGraph();
        Vertex<Cell> playerV = graph.getVertices().find(v -> v.getData().equals(player.getCurrentPos()));
        Vertex<Cell> ghostV = graph.getVertices().find(v -> v.getData().equals(ghost.getCurrentPos()));

        

        ghost.findPath(graph,player);
    }
    
    public Graph<Cell> generateGraph(){
        // used later to determine the what weight an edge should have
        Function<Cell, Integer> getWeight = c -> {
            int val = 1;
            switch (c.getCellClass()){
                case 1:
                case 4:
                case 7:
                case 8:
                    val = Integer.MAX_VALUE;
                    break;
            }
            return val;
        };

        Cells cells = map.getState();
        // create graph from cells
        List<Vertex<Cell>> vertices = new List<>();
        for (Cell cell :
                cells) {
            Vertex<Cell> v = new Vertex<>(cell);
            vertices.add(v);
        }
        Graph<Cell> graph = new Graph<>(vertices);

        // add edge weights to created graph
        vertices.forEach((Vertex<Cell> v1) -> {
            Cell cellV1 = v1.getData();
            // get cellV1 siblings
            Siblings sibs = cellV1.siblings();
            // establish edge between v1 and vertices containing cellV1's siblings
            for (Cell sib :
                    sibs) {
                // all cells have siblings
                // if sibling is null that particular sibling does not exist
                Vertex<Cell> v2 = vertices.find((Vertex<Cell> v) -> v.getData().equals(sib));

                int t1 = getWeight.apply(cellV1);
                int t2 = getWeight.apply(v2.getData());
                int weight =  Math.max(t1,t2);
                graph.createUndirectedEdge(v1,v2,weight);
            }

        });
        return graph;
    }

    public void detectHits(){
        ApplicationLogger logger = Gdx.app.getApplicationLogger();

        if (player.getCurrentPos().getType() == Tile.Type.FRUIT){
            //TODO: timer logic
            ghost.isEdible = true;
        }

        if (player.getCurrentPos() == ghost.getCurrentPos()){
            if (ghost.isEdible){
                logger.debug("Collision","Ghost has been eaten");

            }else{
                logger.debug("Collision","Player has been eaten");

            }
            Gdx.app.exit();
        }
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
