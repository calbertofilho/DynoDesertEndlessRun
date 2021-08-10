package br.studio.calbertofilho.endlessrunner.physics;

import java.awt.Point;
import java.awt.Rectangle;

public class Collision {

	// https://www.youtube.com/watch?v=Zmkuk9OlObw
	public static boolean playerTile(Point point, Rectangle block) {
		return block.contains(point);
	}

}
