package br.studio.calbertofilho.endlessrunner.controllers.managers.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import br.studio.calbertofilho.endlessrunner.controllers.Camera;
import br.studio.calbertofilho.endlessrunner.controllers.containers.DisplayPanel;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Keyboard;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Mouse;
import br.studio.calbertofilho.endlessrunner.controllers.managers.GameStates;
import br.studio.calbertofilho.endlessrunner.maps.LoadMap;

public class PlayScreen extends CommonScreen {

	private Font textsFont;
	private LoadMap map;
	private Camera camera;

	public PlayScreen(GameStates manager) {
		super(manager);
	}

	@Override
	public void init() {
		try {
			textsFont = Font.createFont(Font.TRUETYPE_FONT, new File("resources/assets/fonts/Aero.ttf"));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		camera = new Camera(0, 0);
		map = new LoadMap("resources/assets/levels/dungeon.xml");
	}

	@Override
	public void input(Mouse mouse, Keyboard keyboard) {
		if ((keyboard.isPressed(KeyEvent.VK_UP)) || (keyboard.isPressed(KeyEvent.VK_W))) {
			camera.setDy(3);
			if (camera.getY() == 0) {
				camera.setDy(0);
			}
		} else if ((keyboard.isPressed(KeyEvent.VK_DOWN)) || (keyboard.isPressed(KeyEvent.VK_S))) {
			camera.setDy(-3);
			if (camera.getY() <= -(DisplayPanel.getGameWidth() + (LoadMap.getMapWidth() / 2))) {
				camera.setDy(0);
			}
		} else if ((keyboard.isPressed(KeyEvent.VK_LEFT)) || (keyboard.isPressed(KeyEvent.VK_A))) {
			camera.setDx(3);
			if (camera.getX() == 0) {
				camera.setDx(0);
			}
		} else if ((keyboard.isPressed(KeyEvent.VK_RIGHT)) || (keyboard.isPressed(KeyEvent.VK_D))) {
			camera.setDx(-3);
			if (camera.getX() <= -(DisplayPanel.getGameHeight() + (LoadMap.getMapHeight() / 2))) {
				camera.setDx(0);
			}
		}
		else {
			camera.setDx(0);
			camera.setDy(0);
		}
		if (mouse.isPressed(MouseEvent.BUTTON1))
			System.out.println("Mouse button 1 is pressed");
		if (mouse.isPressed(MouseEvent.BUTTON2))
			System.out.println("Mouse button 2 is pressed");
		if (mouse.isPressed(MouseEvent.BUTTON3))
			System.out.println("Mouse button 3 is pressed");
	}

	@Override
	public void update() {
		camera.update();
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.setColor(new Color(36, 36, 36));
		graphics.fillRect(0, 0, DisplayPanel.getGameWidth(), DisplayPanel.getGameHeight());
	// begin camera
		graphics.translate(camera.getX(), camera.getY());
		// render map
			map.render(graphics);
	// end camera
		graphics.translate(-camera.getX(), -camera.getY());
	// show fps counter
		graphics.setColor(Color.YELLOW);
		graphics.setFont(textsFont.deriveFont(Font.BOLD, 20));
		String text = String.format("%.2f fps", DisplayPanel.getGameFPS());
		graphics.drawString(text, (DisplayPanel.getGameWidth() - graphics.getFontMetrics().stringWidth(text)) - 2, graphics.getFontMetrics().getHeight());
	}

}
