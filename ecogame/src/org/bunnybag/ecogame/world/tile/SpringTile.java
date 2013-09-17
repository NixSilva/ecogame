package org.bunnybag.ecogame.world.tile;

import org.bunnybag.ecogame.world.World;

public class SpringTile extends Tile{

	public SpringTile(int i, int j, World world) {
		super(i, j, world);
		leak_water = 1.0f;
		max_water = 1.0f;
		min_water = 1.0f;
		loss_water = 0.05f;
	}
}
