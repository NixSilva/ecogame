package org.bunnybag.ecogame.world;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bunnybag.ecogame.world.life.Grass;
import org.bunnybag.ecogame.world.life.Hare;
import org.bunnybag.ecogame.world.life.Life;
import org.bunnybag.ecogame.world.tile.RockTile;
import org.bunnybag.ecogame.world.tile.SoilTile;
import org.bunnybag.ecogame.world.tile.SpringTile;
import org.bunnybag.ecogame.world.tile.Tile;


public class World {

	private WorldRenderer renderer;
	private Tile[][] tiles;
	private int width;
	private int height;
	private float max_x, max_y;
	private Set <Life> lives;
	private Set <Life> new_lives;
	
	public World(int width, int height, int n_grass, int n_hare) {
		this.width = width;
		this.height = height;
		max_x = width * Tile.getLength();
		max_y = height * Tile.getLength();
		
		this.renderer = new WorldRenderer(this);
		
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
		for (int i=0; i<n_grass; i++) {
			Grass grass = new Grass(
					(float) Math.random() * max_x, 
					(float) Math.random() * max_y,
					this);
			addLife(grass);
		}
		for (int i=0; i<n_hare; i++) {
			Hare hare = new Hare(
					(float) Math.random() * max_x, 
					(float) Math.random() * max_y,
					this);
			addLife(hare);
		}
	}
	
	public World() {
		this.width = 16;
		this.height = 16;
		max_x = width * Tile.getLength();
		max_y = height * Tile.getLength();
		
		tiles = new Tile[width][height];
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				tiles[i][j] = new SoilTile(i, j, this);
			}
		}
		lives = new HashSet<Life>();
		new_lives = new HashSet<Life>();
		this.renderer = new WorldRenderer(this);
	}
	
	public void addLife(Life life) {
		this.lives.add(life);
	}

	public Tile[][] getTiles() { return tiles; }
	public Set<Life> getLives() { return lives; }
	public Set<Life> getNewLives() { return new_lives; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public float getMaxX() { return max_x; }
	public float getMaxY() { return max_y; }

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
			if (it.next().isDeleted()) it.remove();
		
		lives.addAll(new_lives);
		new_lives.clear();
		//System.out.println(lives.size());
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

	public WorldRenderer getRenderer() {
		return renderer;
	}

}
