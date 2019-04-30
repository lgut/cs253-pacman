package com.lguti.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lguti.pacman.actors.Cell;
import com.lguti.pacman.actors.Map;
import com.lguti.pacman.actors.Player;

public class Pacman extends ApplicationAdapter {
	Stage stage;
	OrthographicCamera cam;
	Map map;
	Player player;

	@Override
	public void create () {

		this.cam = new OrthographicCamera(480, 320);
		cam.setToOrtho(true);
		this.map = new Map();
		player = new Player();

		stage = new MainStage(map,player,new ScreenViewport(cam));
		Gdx.input.setInputProcessor(stage);

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		float delta = Gdx.graphics.getDeltaTime();
		stage.act(delta);
		stage.draw();

	}

	@Override
	public void dispose () {

	}

	@Override
	public void resize(int width, int height) {
		//cam.position.set(width/2, height/2,0);
		stage.getViewport().update(width, height, true);
		//stage.getCamera().update();

		//cam.viewportWidth = width;
		//cam.viewportHeight = height;
		//cam.position.set(width / 2f, height / 2f, 0); //by default camera position on (0,0,0)
	}
}
