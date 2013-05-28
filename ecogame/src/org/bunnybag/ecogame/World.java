package org.bunnybag.ecogame;


public class World {

	private WorldRenderer renderer;
	private Tile[][] tiles;
	
	public World(int width, int height) {
		this.renderer = new WorldRenderer(this);
		
		tiles = new Tile[width][height];
		for (int i=0; i<width; i++) {
			for (int j=0; j<width; j++) {
				tiles[i][j] = new Tile(i, j);
			}
		}
	}
	
	public Tile[][] get_tiles() { return tiles; }

	public void update(float delta) {
		for(int i=0; i<tiles.length; i++) {
			for(int j=0; j<tiles[0].length; j++) {
				tiles[i][j].update(delta);
			}
		}
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
