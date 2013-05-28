package org.bunnybag.ecogame.life;

import org.bunnybag.ecogame.World;
import org.bunnybag.ecogame.tile.SpringTile;

public class Grass extends Life {
	

	public Grass(float x, float y, World world) {
		super(x, y, world);
		max_life = 60.0f;
		spread = 0.1f;
		reproducibility = 0.35f;
		water_needness = 1.0f;
		water_consume = 0.01f;
		life = (float) Math.random() * max_life;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (tile.getClass() == SpringTile.class) alive=false;
		fertility = reproducibility * tile.getWater();
		tile.incWater(- water_consume * delta);
	}

}
