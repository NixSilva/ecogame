package org.bunnybag.ecogame.mainmenuscreen;

import org.bunnybag.ecogame.gamescreen.GameScreen;
import org.bunnybag.ecogame.genlevelscreen.GenLevelScreen;
import org.bunnybag.ecogame.ui.Button;

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
public class MainMenuScreen implements Screen {
	Game game;
	
	int w;
	int h;
	OrthographicCamera guiCam;
	SpriteBatch batcher;
	Vector3 touchPoint;
	Button playButton;
	Button genLevelButton;
	ShapeRenderer shapes;
	
	public MainMenuScreen(Game game) {
		this.game = game;
		
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		
		
		guiCam = new OrthographicCamera(1.0f, (float)h/w);
		guiCam.position.set(0.5f, (float)h/w * 0.5f, 0.0f);
		
		shapes = new ShapeRenderer();
		
		playButton = new Button(0.1f, 0.3f, 0.8f, 0.1f);
		genLevelButton = new Button(0.1f, 0.1f, 0.8f, 0.1f);
		touchPoint = new Vector3();
	}
	
	private void process_input() {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (playButton.hit(touchPoint.x, touchPoint.y)) {
				game.setScreen(new GameScreen(game));
				return;
			}
			if (genLevelButton.hit(touchPoint.x, touchPoint.y)) {
				game.setScreen(new GenLevelScreen(game));
				return;
			}
		}
	}
	
	private void draw() {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		
		shapes.setProjectionMatrix(guiCam.combined);
		shapes.begin(ShapeType.Filled);
			playButton.drawShape(shapes);
			genLevelButton.drawShape(shapes);
		shapes.end();
		
	}

	@Override
	public void render(float delta) {
		process_input();
		draw();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
