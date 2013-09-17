package org.bunnybag.ecogame.world.life;

import java.util.Set;

import org.bunnybag.ecogame.world.World;
import org.bunnybag.ecogame.world.tile.Tile;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Animal extends Life {
	
	protected float fullness;
	protected float quenchness;
	protected float max_speed;
	protected float dest_x;
	protected float dest_y;
	protected float vision;
	protected float hungry_speed;
	protected float thirsty_speed;
	protected Life dest_life;
	protected float eat_speed;
	protected float strength;

	public Animal(float x, float y, World world) {
		super(x, y, world);
		fullness = 1.0f;
		quenchness = 1.0f;
		state = State.Wandering;
		newDestination();
	}
	
	
	public void incFullness(float inc) { fullness += inc; }
	public void incQuenchness(float inc) { quenchness += inc; }

	@Override
	public void update(float delta) {
		super.update(delta);
		if (state != State.Dead && state != State.Deleted) {
			fertility = reproducibility * fullness * quenchness;
			if (state == State.Wandering && (fullness < 0.8f || quenchness < 0.8f)) {
				dest_life = null;
				state = State.Seeking;
			}
			switch (state) {
			case Wandering:
				updateWandering(delta);
				break;
			case Seeking:
				updateSeeking(delta);
				break;
			case Eating:
				updateEating(delta);
				break;
			default:
				break;
			}
			fullness -= hungry_speed;
			quenchness -= thirsty_speed;
			if (fullness < 0.0f || quenchness < 0.0f) {
				System.out.print("Dead of Hunger: " + fullness + " "+ quenchness + " " + state.toString() + " ");
				if (dest_life != null) {
					System.out.println(dest_life.toString() + " " + dest_life.state.toString());
				} else {
					System.out.println();
				}
				killMe();
			}
		}
	}

	private void gotoDestination(float delta) {
		float dst_x;
		float dst_y;
		if (dest_life != null) {
			dst_x = dest_life.getX();
			dst_y = dest_life.getY();
		} else {
			dst_x = dest_x;
			dst_y = dest_y;
		}
		float theta = (float) Math.atan2(dst_y-y, dst_x-x);
		x += Math.cos(theta) * max_speed * delta;
		y += Math.sin(theta) * max_speed * delta;
		if (x > world.getMaxX()) x = world.getMaxX() - 1e-5f;
		if (y > world.getMaxY()) y = world.getMaxY() - 1e-5f;
		updateTile();
	}

	private void updateTile() {
		int new_x = (int) (x / Tile.getLength());
		int new_y = (int) (y / Tile.getLength());
		if (new_x!=x || new_y!=y) {
			tile.getLives().remove(this);
			tile = world.getTiles()[new_x][new_y];
			tile.getLives().add(this);
		}
	}

	private void newDestination() {
		float max_x = world.getMaxX();
		float max_y = world.getMaxY();
		do {
			dest_x = this.x + ((float)Math.random() - 0.5f) * vision;
		} while (dest_x < 0.0 || dest_x > max_x);
		do {
			dest_y = this.y + ((float)Math.random() - 0.5f) * vision;
		} while (dest_y < 0.0 || dest_y > max_y);
	}

	private boolean arrived(float dst_x, float dst_y) {
		return Math.abs(x-dst_x) + Math.abs(y-dst_y) < 0.001f;
	}
	
	private void updateWandering(float delta) {
		if (arrived(dest_x, dest_y)) {
			newDestination();
		} else {
			gotoDestination(delta);
		}
	}
	
	private void updateSeeking(float delta) {
		if (dest_life != null && !dest_life.isDeleted()) {
			if (arrived(dest_life.getX(), dest_life.getY())) {
				state = State.Eating;
				return;
			}
		} else {
			dest_life = null;
			findFood();
		}
		gotoDestination(delta);
	}
	
	private void findFood() {
		Set<Life> lives = tile.getLives();
		for (Life life: lives) {
			if (life.getClass() == Grass.class) {
				dest_life = life;
			} 
		}
		if (dest_life == null) {
			if (arrived(dest_x, dest_y)) {
				newDestination();
			}
		}
	}


	private void updateEating(float delta) {
		if (!dest_life.isDeleted()) {
			float damage = delta * strength;
			if (!dest_life.isDead()) {
				dest_life.incHealth(-damage);
			} else {
				dest_life.incNutrition(-damage);
				dest_life.incJuice(-damage);
				if (dest_life.isDeleted()) {
					state = State.Seeking;
					dest_life = null;
					newDestination();
				}
			}
			incHealth(damage);
			incFullness(damage);
			incQuenchness(damage);
			if (health > max_health) health = max_health;
			if (fullness > 1.0f) fullness = 1.0f;
			if (fullness > 1.0f) fullness = 1.0f;
			if (quenchness > 1.0f) quenchness = 1.0f;
			if (fullness >= 1.0f && quenchness >= 1.0f) {
				state = State.Wandering;
				dest_life = null;
				newDestination();
			}
		} else {
			state = State.Seeking;
			dest_life = null;
			newDestination();
		}
	}
	
	@Override
	protected Life reproduce() {
		Animal child = (Animal) super.reproduce();
		child.fullness = 1.0f;
		child.quenchness = 1.0f;
		return child;
	}
	
	@Override
	public void drawShape(ShapeRenderer shapes) {
		float color = fullness;
		if (state == State.Wandering) {
			shapes.setColor(color, color, color, 1.0f);
		} else if (state == State.Seeking) {
			shapes.setColor(0.0f, color, color, 1.0f);
		} else if (state == State.Eating) {
			shapes.setColor(0.0f, 0.0f, color, 1.0f);
		} else if (state == State.Dead) {
			color = nutrition / (nutrition_max * age / max_life);
			shapes.setColor(color, 0.0f, 0.0f, 1.0f);
		}
		super.drawShape(shapes);
	}

}
