package org.bunnybag.ecogame.genlevelscreen;

import org.bunnybag.ecogame.gamescreen.GameScreen;
import org.bunnybag.ecogame.ui.Button;
import org.bunnybag.ecogame.world.World;
import org.bunnybag.ecogame.world.life.Hare;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
enum BRUSH_TYPE{
	PAN,
	UNIT_BRUSH,
};

public class GenLevelScreen implements Screen {
	Game game;
	
	int w;
	int h;
	OrthographicCamera guiCam;
	SpriteBatch batcher;
	Vector3 touchPoint;
	Button unitButton;
	Button playButton;
	ShapeRenderer shapes;
	World world;
	BRUSH_TYPE brush;

	public GenLevelScreen(Game game) {
		this.game = game;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		
		guiCam = new OrthographicCamera(1.0f, (float)h/w);
		guiCam.position.set(0.5f, (float)h/w * 0.5f, 0.0f);
		
		shapes = new ShapeRenderer();
		
		unitButton = new Button(0.0f, 0.0f, 0.1f, 0.1f);
		playButton = new Button(0.1f, 0.1f, 0.1f, 0.1f);
		touchPoint = new Vector3();
		brush = BRUSH_TYPE.PAN;
		world = new World(10, 10, 100, 10);
		//world = new World();
		world.addLife(new Hare(0.0f, 0.0f, world));
	}
	
	private void process_input() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (playButton.hit(touchPoint.x, touchPoint.y)) {
				game.setScreen(new GameScreen(game));
				return;
			}
			if (unitButton.hit(touchPoint.x, touchPoint.y)) {
				brush = BRUSH_TYPE.UNIT_BRUSH;
				return;
			}
		}
	}
	
	private void draw(float delta) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		shapes.setProjectionMatrix(guiCam.combined);
		shapes.begin(ShapeType.Filled);
		playButton.drawShape(shapes);
		unitButton.drawShape(shapes);
		shapes.end();
		//world.update(delta);
		//world.render();
		
	}

	@Override
	public void render(float delta) {
		//process_input();
		//draw(delta);
		world.update(delta);
		world.render();
	}

	@Override
	public void resize(int width, int height) {
		world.resize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		world.dispose();
	}

}
