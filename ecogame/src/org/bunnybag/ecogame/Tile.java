package org.bunnybag.ecogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Tile {
	//private TileType type;
	private boolean is_spring;
	private int x, y;
	private int width, height;
	private float length;
	private float water;
	private static Sprite sprite;
	private Tile[] neighbors;
	private Tile[][] tiles;

	public Tile(int i, int j, World world) {
		is_spring = false;
		x = i;
		y = j;
		length = 0.05f;
		water = 0;
		neighbors = new Tile[4];
		tiles = world.getTiles();
		width = world.getWidth();
		height = world.getHeight();
		
		neighbors[0] = x>0 ? tiles[x-1][y] : null;
		neighbors[1] = y>0 ? tiles[x][y-1] : null;
		neighbors[2] = x<width-1 ? tiles[x+1][y] : null;
		neighbors[3] = y>height-1 ? tiles[x][y+1] : null;
		
		if (sprite == null)
		{
			Texture texture = new Texture(Gdx.files.internal("texture/test.png"));
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion region = new TextureRegion(texture, 0, 0, 128, 128);
			sprite = new Sprite(region);
			sprite.setSize(length, length);
			sprite.setOrigin(length/2, length/2);
		}

	}
	
	public void setSpring(boolean spring) {
		is_spring = spring;
		if (is_spring) {
			water = 1.0f;
		}
	}
	public boolean getSpring() { return is_spring; }
	public void setWater(float newWater) { water = newWater; }
	public float getWater() { return water; }
	
	public void drawSprite(SpriteBatch batch) {
		sprite.setSize(length, length);
		sprite.setPosition(x*length-length/2, y*length-length/2);
		sprite.draw(batch);
	}
	
	public void drawShape(ShapeRenderer shapes) {
		float colorX = water * 1.0f;
		float colorY = water * 1.0f;
		shapes.setColor(0.0f, colorX, colorY, 1.0f);
		shapes.rect(x*length-length/2, y*length-length/2, length, length);
	}
	
	public void update(float deltaTime) {
		for (Tile tile: neighbors) {
			if (tile != null) {
				float nw = tile.getWater();
				float dw = water - nw;
				water -= dw * deltaTime;
				tile.setWater(nw + dw * deltaTime);
			}
		}
		water -= deltaTime * 0.1f;
		if (water < 0.0f) water = 0.0f;
		if (water > 1.0f) water = 1.0f;
		if (is_spring) water = 1.0f;
	}
}
