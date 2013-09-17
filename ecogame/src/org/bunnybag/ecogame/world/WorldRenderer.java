package org.bunnybag.ecogame.world;

import org.bunnybag.ecogame.world.life.Life;
import org.bunnybag.ecogame.world.tile.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

public class WorldRenderer {
	
	World world;
	int w;
	int h;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapes;

	public WorldRenderer(World world) {
		this.world = world;
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		
		camera = new OrthographicCamera(1, h/w);
		float max_x = world.getMaxX();
		float max_y = world.getMaxY();
		camera.position.set(max_x / 2, max_y / 2, 0.0f);
		shapes = new ShapeRenderer();
		batch = new SpriteBatch();
	}
	
	public OrthographicCamera getCamera() { return camera; };

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		Tile[][] tiles = world.getTiles();
		/*
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Tile[] row : tiles) {
			for (Tile tile : row) {
				tile.drawSprite(batch);
			}
		}
		batch.end();
		*/
		
		shapes.setProjectionMatrix(camera.combined);
		shapes.begin(ShapeType.Filled);
		for(Tile[] row : tiles) {
			for (Tile tile : row) {
				tile.drawShape(shapes);
			}
		}
		for(Life life: world.getLives()) {
			life.drawShape(shapes);
		}
		shapes.end();
		
		/*
		shapes.begin(ShapeType.Line);
		for(Life life: world.getLives()) {
			life.drawLinkage(shapes);
		}
		shapes.end();
		*/
	}

	public void resize(int width, int height) {
		Vector3 old_position = camera.position.cpy();
		if (width > height) {
			camera.setToOrtho(false, 1.0f, (float)height/width);
		}
		else {
			camera.setToOrtho(false, (float)width/height, 1.0f);
		}
		camera.position.set(old_position);
		camera.update();
	}

	public void dispose() {
		batch.dispose();
		shapes.dispose();
	}
}

