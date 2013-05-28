package org.bunnybag.ecogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.input.GestureDetector;

public class WorldRenderer {
	
	World world;
	int w;
	int h;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer shapes;
	private CameraController controller;
	private GestureDetector detector;

	public WorldRenderer(World world) {
		this.world = world;
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		
		camera = new OrthographicCamera(1, h/w);
		controller = new CameraController(camera);
		detector = new GestureDetector(controller);
		Gdx.input.setInputProcessor(detector);
		shapes = new ShapeRenderer();
		batch = new SpriteBatch();
	}
	
	public OrthographicCamera get_camera() { return camera; };

	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		Tile[][] tiles = world.get_tiles();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(Tile[] row : tiles) {
			for (Tile tile : row) {
				tile.drawSprite(batch);
			}
		}
		batch.end();
		
		shapes.setProjectionMatrix(camera.combined);
		shapes.begin(ShapeType.Filled);
		for(Tile[] row : world.get_tiles()) {
			for (Tile tile : row) {
				tile.drawShape(shapes);
			}
		}
		shapes.end();
	}

	public void resize(int width, int height) {
		if (width > height)
			camera.setToOrtho(false, 1.0f, (float)height/width);
		else
			camera.setToOrtho(false, (float)width/height, 1.0f);
		camera.position.set(0.0f, 0.0f, 0.0f);
		camera.update();
	}

	public void dispose() {
		batch.dispose();
		shapes.dispose();
	}
}

