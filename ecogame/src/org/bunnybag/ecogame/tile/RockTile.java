package org.bunnybag.ecogame.tile;

import org.bunnybag.ecogame.World;

public class RockTile extends Tile{

	public RockTile(int i, int j, World world) {
		super(i, j, world);
		leak_water = 0.0f;
		max_water = 0.0f;
		min_water = 0.0f;
		loss_water = 0.05f;
	}
}
