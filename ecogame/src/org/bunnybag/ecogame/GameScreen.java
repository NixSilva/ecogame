package org.bunnybag.ecogame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	
	Game game;
	World world;
	
	public GameScreen(Game game) {
		this.game = game;
		this.world = new World(50, 50);
	}

	@Override
	public void render(float delta) {
		world.update(delta);
		world.render();
		//System.out.println(1.0f/ delta);
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
