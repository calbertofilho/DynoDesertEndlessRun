package br.studio.calbertofilho.endlessrunner.controllers.containers;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import br.studio.calbertofilho.endlessrunner.controllers.handlers.Keyboard;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Mouse;
import br.studio.calbertofilho.endlessrunner.controllers.managers.GameStates;

@SuppressWarnings("serial")
public class DisplayPanel extends JPanel implements Runnable {

	private static Dimension gameDimensions;
	private Thread thread;
	private static boolean running;
	private Keyboard keyboard;
	private Mouse mouse;
	private BufferedImage image;
	private Graphics drawer;
	private Graphics2D graphics;
	private final int TARGET_FPS = 60;
	private static double averageFPS;
	private long startTime, URDTimeMillis, targetTime, waitTime, totalTime;
	private int frameCount, maxFrameCount;
	private GameStates gameManager;

	public DisplayPanel(int width, int height) {
		super();
		gameDimensions = new Dimension(width, height);
		setPreferredSize(gameDimensions);
		setFocusable(true);
		requestFocus();
		setDoubleBuffered(true);
		running = false;
	}

	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this, "MainThread");
			thread.start();
		}
	}

	@Override
	public void run() {
		initGraphics();
		init();
		while (running) {
			startTime = System.nanoTime();
	//////////////////////////////
			inputGameControls();
			updateGame();
			renderGame();
			drawGame();
	//////////////////////////////
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = (targetTime - URDTimeMillis) > 0 ? (targetTime - URDTimeMillis) : 0;
			try {
				Thread.sleep(waitTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			totalTime += System.nanoTime() - startTime;
			frameCount++;
			if (frameCount == maxFrameCount) {
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
	}

	private void initGraphics() {
		image = new BufferedImage(getGameWidth(), getGameHeight(), BufferedImage.TYPE_INT_ARGB);
		graphics = (Graphics2D) image.getGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	public void init() {
		running = true;
		keyboard = new Keyboard(this);
		mouse = new Mouse(this);
		gameManager = new GameStates();
		totalTime = frameCount = 0;
		maxFrameCount = TARGET_FPS;
		targetTime = 1000 / TARGET_FPS;
	}

////////////////////////////////////////////////////////////////////////////////
	private void inputGameControls() {
		gameManager.input(mouse, keyboard);
	}

	private void updateGame() {
		gameManager.update();
		keyboard.update();
		mouse.update();
	}

	private void renderGame() {
		gameManager.render(graphics);
	}

	private void drawGame() {
		drawer = this.getGraphics();
		drawer.drawImage(image, 0, 0, null);
		drawer.dispose();
	}
////////////////////////////////////////////////////////////////////////////////

	public static double getGameFPS() {
		return averageFPS;
	}

	public static int getGameWidth() {
		return (int) gameDimensions.getWidth();
	}

	public static int getGameHeight() {
		return (int) gameDimensions.getHeight();
	}

	public static void setGameRunning(boolean running) {
		DisplayPanel.running = running;
	}

	public static boolean getGameRunning() {
		return running;
	}

}
