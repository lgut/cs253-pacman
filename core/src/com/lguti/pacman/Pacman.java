package com.lguti.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lguti.pacman.actors.Ghost;
import com.lguti.pacman.actors.Map;
import com.lguti.pacman.actors.Player;

public class Pacman extends ApplicationAdapter {
	Stage stage;
	OrthographicCamera cam;
	Map map;
	Player player;
	Ghost ghost;

	@Override
	public void create () {

		this.cam = new OrthographicCamera(480, 320);
		cam.setToOrtho(true);
		this.map = new Map();
		player = new Player();
		ghost = new Ghost();

		stage = new MainStage(map,player,ghost, new ScreenViewport(cam));
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
		stage.getViewport().update(width, height, true);
	}
}
