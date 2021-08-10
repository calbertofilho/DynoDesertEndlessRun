package br.studio.calbertofilho.endlessrunner.maps.tiles;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Tile extends Rectangle {

	public static final int DEFAULT_SIZE = 16;
	public static final int RENDER_SCALE = 3;
	protected BufferedImage image;
	protected int tileLine, tileColumns;
	protected TileID id;

	public Tile(BufferedImage image, int x, int y, int width, int height, int tileLine, int tileColumns, TileID id) {
		this(x, y, width, height, tileLine, tileColumns, id);
		this.image = image;
	}

	public Tile(int x, int y, int width, int height, int tileLine, int tileColumns, TileID id) {
		this(x, y, width, height);
		this.width = width;
		this.height = height;
		this.tileLine = tileLine;
		this.tileColumns = tileColumns;
		this.id = id;
	}

	public Tile(int x, int y, int width, int height) {
		setLocation(x, y);
		setSize(width, height);
	}

	public Tile(int x, int y, int size) {
		this(x, y, size, size);
	}

	public Tile(int x, int y) {
		this(x, y, DEFAULT_SIZE);
	}

	public Tile() {}

	public void render(Graphics2D graphics) {
		graphics.drawImage(image, x * RENDER_SCALE, y * RENDER_SCALE, width * RENDER_SCALE, height * RENDER_SCALE, null);
	// draw a rectangle to represent ID //
		if (id.equals(TileID.Hole))
			graphics.setColor(java.awt.Color.RED);
		else if (id.equals(TileID.Path))
			graphics.setColor(java.awt.Color.GREEN);
		else if (id.equals(TileID.Solid))
			graphics.setColor(java.awt.Color.ORANGE);
		else if (id.equals(TileID.Decoration))
			graphics.setColor(java.awt.Color.BLUE);
		graphics.drawRect(x * RENDER_SCALE, y * RENDER_SCALE, width * RENDER_SCALE, height * RENDER_SCALE);
	}

//-- SETTERS --//
	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setTileLine(int tileLine) {
		this.tileLine = tileLine;
	}

	public void setTileColumns(int tileColumns) {
		this.tileColumns = tileColumns;
	}

	public void setId(TileID id) {
		this.id = id;
	}
//-- GETTERS --//
	public BufferedImage getImage() {
		return image;
	}

	public int getTileLine() {
		return tileLine;
	}

	public int getTileColumns() {
		return tileColumns;
	}

	public TileID getId() {
		return id;
	}
//---- END ----//

}
