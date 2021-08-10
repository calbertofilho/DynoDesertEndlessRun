package br.studio.calbertofilho.endlessrunner.controllers;

public class Camera {

	private float x, y;
	private float dx, dy;

	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
		dx = 0;
		dy = 0;
	}

	public void update() {
		x += dx;
		y += dy;
	}

//-- SETTERS --//
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}
//-- GETTERS --//
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getDx() {
		return dx;
	}

	public float getDy() {
		return dy;
	}
//---- END ----//

}
