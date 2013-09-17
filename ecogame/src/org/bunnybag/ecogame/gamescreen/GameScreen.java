package org.bunnybag.ecogame.gamescreen;

import org.bunnybag.ecogame.world.World;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
public class GameScreen implements Screen {
	
	Game game;
	World world;
	private GameController controller;
	private GestureDetector detector;
	
	public GameScreen(Game game) {
		this.game = game;
		this.world = new World(10, 10, 100, 10);
		controller = new GameController(world.getRenderer().getCamera());
		detector = new GestureDetector(controller);
		Gdx.input.setInputProcessor(detector);
	}

	@Override
	public void render(float delta) {
		world.update(delta);
		world.render();
		System.out.println(1.0f / delta);
	}

	@Override
	public void resize(int width, int height) {
		world.resize(width, height);
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
		world.dispose();
	}
}
