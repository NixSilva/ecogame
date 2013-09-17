package org.bunnybag.ecogame.world.life;

import org.bunnybag.ecogame.world.World;
import org.bunnybag.ecogame.world.tile.Tile;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Life implements Cloneable {

	public enum State {
		Dead, Deleted,
		Growing,
		Wandering, Seeking, Eating, Fleeing, Attacking
	}

	protected World world;
	protected Tile tile;
	protected float x, y;
	protected float age;
	protected float life;
	protected float max_life;
	protected float spread;
	protected float reproducibility;
	protected float fertility;
	protected State state;
	protected float nutrition;
	protected float juice;
	protected float nutrition_max;
	protected float juice_max;
	private float decompose_speed;
	private float evaporate_speed;
	protected float max_health;
	protected float health;
	
	public Life(float x, float y, World world) {
		this.x = x;
		this.y = y; 
		this.world = world;
		decompose_speed = 0.01f;
		evaporate_speed = 0.01f;
		int tile_x = (int) (this.x / Tile.getLength());
		int tile_y = (int) (this.y / Tile.getLength());
		this.tile = world.getTiles()[tile_x][tile_y];
		tile.getLives().add(this);
		age = 0.0f;
	}
	
	public boolean isDeleted() { return (state == State.Deleted); }
	public boolean isDead() { return (state == State.Dead); }
	public float getX() { return x; }
	public float getY() { return y; }
	public void incHealth(float inc) { health += inc; }
	public void incNutrition(float inc) { nutrition += inc; }
	public void incJuice(float inc) { juice += inc; }

	public void update(float delta) {
		assert(state != State.Deleted);
		if (state != State.Dead) {
			age += delta;
			if (Math.random() < fertility * delta) {
				world.getNewLives().add(reproduce());
			}
			if (health < 0.0f) {
				killMe();
			}
			if (age > life) {
				killMe();
			}
		} else {
			decompose(delta);
		}
	}
	
	protected void decompose(float delta) {
		assert(state == State.Dead);
		nutrition -= decompose_speed * delta;
		juice -= evaporate_speed * delta;
		tile.incFertile(decompose_speed * delta);
		tile.getLives().remove(this);
		if (nutrition < 0.0f) { state = State.Deleted; }
	}
	
	protected void killMe() {
		assert(state != State.Dead);
		System.out.print(this.getClass().toString());
		System.out.println(" Health: " + health + " Age: " + age + "/" + life);
		
		state = State.Dead;
		nutrition = nutrition_max * age / max_life;
		juice = juice_max * age / max_life;
	}

	protected Life reproduce() {
		Life child = null;
		try {
			child = (Life) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (child != null) {
			// TODO: change these out!
			float max_x = world.getMaxX();
			float max_y = world.getMaxY();
			do {
				child.x = this.x + ((float)Math.random() - 0.5f) * spread;
			} while (child.x < 0.0 || child.x > max_x);
			do {
				child.y = this.y + ((float)Math.random() - 0.5f) * spread;
			} while (child.y < 0.0 || child.y > max_y);
			child.age = 0.0f;
			int tile_x = (int) (child.x / Tile.getLength());
			int tile_y = (int) (child.y / Tile.getLength());
			child.tile = world.getTiles()[tile_x][tile_y];
			child.tile.getLives().add(child);
			child.life = (float) Math.random() * max_life;
		}
		return child;
	}

	public void drawShape(ShapeRenderer shapes) {
		float length = (float) Math.pow(age, 0.1f) * 0.01f;
		shapes.rect(x-length/2, y-length/2, length, length);
	}
	
	public void drawLinkage(ShapeRenderer shapes) {
		float tile_x = (this.tile.getX() + 0.5f) * Tile.getLength();
		float tile_y = (this.tile.getY() + 0.5f) * Tile.getLength();
		shapes.line(this.x, this.y, tile_x, tile_y);
	}



}
