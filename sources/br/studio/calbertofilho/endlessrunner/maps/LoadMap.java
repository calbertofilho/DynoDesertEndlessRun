package br.studio.calbertofilho.endlessrunner.maps;

import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.studio.calbertofilho.endlessrunner.animations.Sprite;
import br.studio.calbertofilho.endlessrunner.maps.tiles.Tile;
import br.studio.calbertofilho.endlessrunner.maps.tiles.TileID;

public class LoadMap {

	private String imagePath, layerID;
	private int width, height, tileWidth, tileHeight, tileCount, tileLine, tileColumns, layers, tileData;
	private static int mapWidth, mapHeight;
	private Sprite sprite;
	private String[] lineDatas, datas;
	private ArrayList<Tile> tileMap;
	private DocumentBuilderFactory builderFactory;
	private DocumentBuilder documentBuilder;
	private Document document;
	private NodeList list;
	private Node node;
	private Element element;

	public LoadMap() {
		tileMap = new ArrayList<Tile>();
	}

	public LoadMap(String path) {
		this();
		addTilesToMap(path);
	}

	private void addTilesToMap(String path) {
		try {
			builderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = builderFactory.newDocumentBuilder();
			document = documentBuilder.parse(new File(path).getAbsoluteFile());
			document.getDocumentElement().normalize();
			list = document.getElementsByTagName("image");
			node = list.item(0);
			element = (Element) node;
			imagePath = element.getAttribute("source");
			list = document.getElementsByTagName("tileset");
			node = list.item(0);
			element = (Element) node;
			tileWidth = Integer.parseInt(element.getAttribute("tilewidth"));
			tileHeight = Integer.parseInt(element.getAttribute("tileheight"));
			tileCount = Integer.parseInt(element.getAttribute("tilecount"));
			tileColumns = Integer.parseInt(element.getAttribute("columns"));
			sprite = new Sprite("resources/assets/images/sceneries/" + imagePath, tileWidth, tileHeight);
			list = document.getElementsByTagName("layer");
			layers = list.getLength();
			lineDatas = new String[layers];
			for (int i = 0; i < lineDatas.length; i++) {
				tileLine = i + 1;
				node = list.item(i);
				element = (Element) node;
				layerID = element.getAttribute("name");
				if (i <= 0) {
					width = Integer.parseInt(element.getAttribute("width"));
					height = Integer.parseInt(element.getAttribute("height"));
				}
				lineDatas[i] = element.getElementsByTagName("data").item(0).getTextContent();
				System.out.println("------ Layer: " + layerID + " ------\n" + lineDatas[i]);
				datas = lineDatas[i].split(",");
				for (TileID id : TileID.values()) {
					if (id.toString().equalsIgnoreCase(layerID)) {
						for (int j = 0; j < (width * height); j++) {
							tileData = Integer.parseInt(datas[j].replaceAll("\\s+", ""));
							if (tileData != 0)
								tileMap.add(new Tile(sprite.getSprite((int) ((tileData - 1) % tileColumns), (int) ((tileData - 1) / tileColumns)), ((int) (j % width) * tileWidth ), ((int) (j / height) * tileHeight), tileWidth, tileHeight, tileLine, tileColumns, id));
						}
						datas = null;
					}
				}
				if (datas != null)
					throw new DOMException(DOMException.NOT_FOUND_ERR, layerID + " layer not found...");
			}
			mapWidth = width * tileWidth;
			mapHeight = height * tileHeight;
			System.out.println(tileCount + " tiles were loading on map...");
		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D graphics) {
		for (Tile tile : tileMap)
			tile.render(graphics);
	}

	public static int getMapHeight() {
		return mapHeight;
	}

	public static int getMapWidth() {
		return mapWidth;
	}

}
