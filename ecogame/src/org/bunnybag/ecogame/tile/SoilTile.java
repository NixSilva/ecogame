package org.bunnybag.ecogame.tile;

import org.bunnybag.ecogame.World;

public class SoilTile extends Tile{

	public SoilTile(int i, int j, World world) {
		super(i, j, world);
		leak_water = 0.5f;
		max_water = 1.0f;
		min_water = 0.0f;
		loss_water = 0.05f;
	}
}
