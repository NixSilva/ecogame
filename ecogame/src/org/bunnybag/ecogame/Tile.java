package org.bunnybag.ecogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Tile {
	int x, y;
	float age;
	private static Sprite sprite;

	public Tile(int i, int j) {
		x = i;
		y = j;
		age = 0;
		if (sprite == null)
		{
			Texture texture = new Texture(Gdx.files.internal("data/libgdx.png"));
			texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
			TextureRegion region = new TextureRegion(texture, 140, 190, 32, 32);
			sprite = new Sprite(region);
			sprite.setSize(0.05f, 0.05f * sprite.getHeight() / sprite.getWidth());
			sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		}

	}
	
	public void drawSprite(SpriteBatch batch) {
		sprite.setSize(0.045f, 0.045f);
		sprite.setPosition(x * 0.05f -sprite.getWidth()/2, y * 0.05f -sprite.getHeight()/2);
		sprite.draw(batch);
	}
	
	public void drawShape(ShapeRenderer shapes) {
		float colorX = x * 0.01f;
		float colorY = y * 0.01f;
		shapes.setColor(colorX, 0.0f, colorY, 1.0f);
		shapes.rect(x*0.05f - 0.02f, y*0.05f-0.02f, 0.04f, 0.04f);
	}
	
	public void update(float deltaTime) {
		age += deltaTime;
	}

}
