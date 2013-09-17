package org.bunnybag.ecogame.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Button {
	
	float x;
	float y;
	float w;
	float h;

	public Button(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean hit(float hx, float hy) {
		return hx <= x + w && hx >= x && hy <= y + h && hy >= y;
	}

	public void drawShape(ShapeRenderer shapes) {
		shapes.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		shapes.rect(x, y, w, h);
	}

}
