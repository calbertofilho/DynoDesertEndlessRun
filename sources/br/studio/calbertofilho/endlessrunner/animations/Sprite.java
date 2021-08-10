package br.studio.calbertofilho.endlessrunner.animations;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import br.studio.calbertofilho.endlessrunner.maps.tiles.Tile;

public class Sprite {

	private BufferedImage spriteSheet = null;
	private BufferedImage[][] spriteArray;
	private BufferedImage sprite;
	private int width, height;
	private int widthSprite, heightSprite;

	public Sprite(String file, int width, int height) {
		this.width = width;
		this.height = height;
		System.out.println("Loading: " + file + "...");
		spriteSheet = loadSprite(file);
		widthSprite = spriteSheet.getWidth() / width;
		heightSprite = spriteSheet.getHeight() / height;
		loadSpriteArray();
	}

	public Sprite(String file) {
		this(file, Tile.DEFAULT_SIZE, Tile.DEFAULT_SIZE);
	}

	private BufferedImage loadSprite(String file) {
		sprite = null;
		try {
			sprite = ImageIO.read(new File(file).getAbsoluteFile());
		} catch (Exception e) {
			System.out.println("ERROR: Could not load file: " + file);
			e.printStackTrace();
		}
		return sprite;
	}

	private void loadSpriteArray() {
		spriteArray = new BufferedImage[heightSprite][widthSprite];
		for (int j = 0; j < heightSprite; j++)
			for (int i = 0; i < widthSprite; i++)
				spriteArray[j][i] = getSprite(i, j);
	}

	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public BufferedImage getSprite(int i, int j) {
		return getSpriteSheet().getSubimage(i * width, j * height, width, height);
	}

	public BufferedImage[][] getSpriteArray() {
		return spriteArray;
	}

	public BufferedImage[] getSpriteArrayMotion(int i) {
		return spriteArray[i];
	}

	public void setSize(int width, int height) {
		setWidth(width);
		setHeight(height);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public void setHeight(int height) {
		this.height = height;
		heightSprite = spriteSheet.getHeight() / height;
	}
	
	public void setWidth(int width) {
		this.width = width;
		widthSprite = spriteSheet.getWidth() / width;
	}

}
