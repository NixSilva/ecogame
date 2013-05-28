package org.bunnybag.ecogame;


public class World {

	private WorldRenderer renderer;
	private Tile[][] tiles;
	private int width;
	private int height;
	
	public World(int width, int height) {
		this.renderer = new WorldRenderer(this);
		this.width = width;
		this.height = height;
		
		tiles = new Tile[width][height];
		for (int i=0; i<width; i++) {
			for (int j=0; j<height; j++) {
				tiles[i][j] = new Tile(i, j, this);
			}
		}
		tiles[width/2][height/2].setSpring(true);
		tiles[0][0].setSpring(true);
		tiles[width-1][height-1].setSpring(true);
	}
	
	public Tile[][] getTiles() { return tiles; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }

	public void update(float delta) {
		for (Tile[] row: tiles) {
			for (Tile tile: row) {
				tile.update(delta);
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
