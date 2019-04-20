package com.lguti.pacman;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.lguti.pacman.actors.Map;

public class Pacman extends ApplicationAdapter {
	Stage stage;
	OrthographicCamera cam;
	Map map;

	@Override
	public void create () {

		this.cam = new OrthographicCamera(480, 320);
		cam.setToOrtho(true);
		this.map = new Map();

		stage = new Stage(new ScreenViewport(cam));

		stage.addActor(map);
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
		stage.getCamera().update();
		stage.getViewport().update(width, height, true);
		//cam.viewportWidth = width;
		//cam.viewportHeight = height;
		//cam.position.set(width / 2f, height / 2f, 0); //by default camera position on (0,0,0)
	}
}
