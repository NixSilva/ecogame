package org.bunnybag.ecogame.world.life;

import org.bunnybag.ecogame.world.World;
import org.bunnybag.ecogame.world.tile.SpringTile;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Grass extends Life {

	private float water_consume;

	public Grass(float x, float y, World world) {
		super(x, y, world);
		max_life = 60.0f;
		spread = 0.1f;
		reproducibility = 0.10f;
		water_consume = 0.01f;
		nutrition_max = 1.0f;
		juice_max = 1.0f;
		health = 1.0f;
		state = State.Growing;
		life = (float) Math.random() * max_life;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (tile.getClass() == SpringTile.class) state = State.Deleted;
		fertility = reproducibility * tile.getWater();
		tile.incWater(- water_consume * delta);
	}
	
	@Override
	public void drawShape(ShapeRenderer shapes) {
		if (state == State.Growing) {
			shapes.setColor(0.0f, 1.0f, 0.0f, 1.0f);
		} else if (state == State.Dead) {
			float color = nutrition / (nutrition_max * age / max_life);
			shapes.setColor(color, color, 0.0f, 1.0f);
		}
		super.drawShape(shapes);
	}


}
