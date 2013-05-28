package org.bunnybag.ecogame;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bunnybag.ecogame.life.Grass;
import org.bunnybag.ecogame.life.Life;
import org.bunnybag.ecogame.tile.RockTile;
import org.bunnybag.ecogame.tile.SoilTile;
import org.bunnybag.ecogame.tile.SpringTile;
import org.bunnybag.ecogame.tile.Tile;


public class World {

	private WorldRenderer renderer;
	private Tile[][] tiles;
	private int width;
	private int height;
	private Set <Life> lives;
	private Set <Life> new_lives;
	
	public World(int width, int height) {
		this.renderer = new WorldRenderer(this);
		this.width = width;
		this.height = height;
		
		tiles = new Tile[width][height];
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				tiles[i][j] = new SoilTile(i, j, this);
			}
		}
		tiles[width/2][height/2] = new SpringTile(width/2, height/2, this);
		tiles[0][0] = new SpringTile(0, 0, this);
		tiles[1][1] = new RockTile(1, 1, this);
		tiles[width-1][height-1] = new SpringTile(width-1, height-1, this);
		
		lives = new HashSet<Life>();
		new_lives = new HashSet<Life>();
		float total_width = width * Tile.getLength();
		float total_height = height * Tile.getLength();
		for (int i=0; i<100; i++) {
			Grass grass = new Grass(
					(float) Math.random() * total_width, 
					(float) Math.random() * total_height,
					this);
			lives.add(grass);
		}
	}
	
	public Tile[][] getTiles() { return tiles; }
	public Set<Life> getLives() { return lives; }
	public Set<Life> getNewLives() { return new_lives; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }

	public void update(float delta) {
		for (Tile[] row: tiles) {
			for (Tile tile: row) {
				tile.update(delta);
			}
		}
		for (Life life: lives) {
			life.update(delta);
		}
		for (Iterator<Life> it = lives.iterator(); it.hasNext();) 
			if (!it.next().isAlive()) it.remove();
		
		lives.addAll(new_lives);
		new_lives.clear();
	}

	public void render() {
		renderer.render();
	}

	public void resize(int width, int height) {
		renderer.resize(width, height);
	}

	public void dispose() {
		renderer.dispose();
	}

}
