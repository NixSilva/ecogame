package org.bunnybag.ecogame.world.life;

import org.bunnybag.ecogame.world.World;

public class Hare extends Animal {
	

	public Hare(float x, float y, World world) {
		super(x, y, world);
		max_life = 600.0f;
		spread = 0.01f;
		reproducibility = 0.00f;
		max_speed = 0.05f;
		vision = 0.1f;
		hungry_speed = 0.001f;
		thirsty_speed = 0.001f;
		nutrition_max = 10.0f;
		juice_max = 10.0f;
		strength = 1.0f;
		max_health = 10.0f;
		health = 10.0f;
		life = (float) Math.random() * max_life;
	}

	@Override
	public void update(float delta) {
		super.update(delta);
	}
	
}
