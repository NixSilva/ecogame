package org.bunnybag.ecogame.life;

import org.bunnybag.ecogame.World;
import org.bunnybag.ecogame.tile.Tile;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Life implements Cloneable {

	private World world;
	private float x, y;
	private float age;
	protected Tile tile;
	boolean alive;
	protected float life;
	protected float max_life;
	protected float spread;
	protected float reproducibility;
	protected float fertility;
	protected float water_needness;
	protected float water_consume;
	
	public Life() {}
	
	public Life(float x, float y, World world) {
		this.x = x;
		this.y = y; 
		this.world = world;
		int tile_x = (int) (this.x / Tile.getLength());
		int tile_y = (int) (this.y / Tile.getLength());
		this.tile = world.getTiles()[tile_x][tile_y];
		alive = true;
		age = 0.0f;
	}
	
	public boolean isAlive() { return alive; }

	public void update(float delta) {
		assert(alive);
		if (alive) {
			age += delta;
			if (Math.random() < fertility * delta) {
				world.getNewLives().add(reproduce());
			}
			if (age > life) {
				alive = false;
			}
		}
	}

	private Life reproduce() {
		Life child = null;
		try {
			child = (Life) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (child != null) {
			float max_x = world.getWidth() * Tile.getLength();
			float max_y = world.getHeight() * Tile.getLength();
			do {
				child.x = this.x + ((float)Math.random() - 0.5f) * spread;
			} while (child.x < 0.0 || child.x > max_x);
			do {
				child.y = this.y + ((float)Math.random() - 0.5f) * spread;
			} while (child.y < 0.0 || child.y > max_y);
			child.age = 0.0f;
			child.alive = true;
			int tile_x = (int) (child.x / Tile.getLength());
			int tile_y = (int) (child.y / Tile.getLength());
			child.tile = world.getTiles()[tile_x][tile_y];
			child.life = (float) Math.random() * max_life;
		}
		return child;
	}

	public void drawShape(ShapeRenderer shapes) {
		float length = (float) Math.pow(age, 0.1f) * 0.01f;
		if (alive) {
			//float color = fertility * 10.0f;
			float color = 1.0f;
			shapes.setColor(0.0f, color, 0.0f, 1.0f);
		} else {
			shapes.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		}
		shapes.rect(x-length/2, y-length/2, length, length);
	}
	
	public void drawLinkage(ShapeRenderer shapes) {
		float tile_x = (this.tile.getX() + 0.5f) * Tile.getLength();
		float tile_y = (this.tile.getY() + 0.5f) * Tile.getLength();
		shapes.line(this.x, this.y, tile_x, tile_y);
	}

}
