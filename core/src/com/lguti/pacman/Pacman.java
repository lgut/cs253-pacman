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

	@Override
	public void create () {
		Map map = new Map();
		cam = new OrthographicCamera();
		cam.viewportHeight = 320;
		cam.viewportWidth = 480;
		cam.position.set(map.width*map.tileSize/2, map.height*map.tileSize/2,0);
		stage = new Stage(new ScreenViewport(cam));


	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

	@Override
	public void dispose () {

	}
}
