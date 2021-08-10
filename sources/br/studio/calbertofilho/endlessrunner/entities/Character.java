package br.studio.calbertofilho.endlessrunner.entities;

import java.awt.Graphics2D;

import br.studio.calbertofilho.endlessrunner.controllers.handlers.Keyboard;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Mouse;

public abstract class Character {

	protected float x, y;
	protected int width, height;
	protected float velX, velY;
	protected EntityID id;

	public Character(float x, float y, EntityID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public Character() {}

	public abstract void input(Mouse mouse, Keyboard keyboard);
	public abstract void update();
	public abstract void render(Graphics2D graphics);

//-- SETTERS --//
	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setVelX(float velX) {
		this.velX = velX;
	}

	public void setVelY(float velY) {
		this.velY = velY;
	}

	public void setId(EntityID id) {
		this.id = id;
	}
//-- GETTERS --//
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float getVelX() {
		return velX;
	}

	public float getVelY() {
		return velY;
	}

	public EntityID getId() {
		return id;
	}
//---- END ----//

}
