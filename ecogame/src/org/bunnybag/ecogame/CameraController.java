package org.bunnybag.ecogame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class CameraController implements GestureListener {
	OrthographicCamera camera;
	int w;
	int h;
	float old_zoom;
	boolean zooming;
	
	public CameraController(OrthographicCamera camera) {
		this.camera = camera;
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		old_zoom = 1.0f;
		zooming = false;
	}
	
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		old_zoom = camera.zoom;
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if (w > h) {
			camera.position.x -= deltaX / w * camera.zoom;
			camera.position.y += deltaY / w * camera.zoom;
		} else {
			camera.position.x -= deltaX / h * camera.zoom;
			camera.position.y += deltaY / h * camera.zoom;
		}
		camera.update();
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		camera.zoom = old_zoom * initialDistance / distance;
		camera.update();
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
	
}

