package br.studio.calbertofilho.endlessrunner.controllers.managers.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import br.studio.calbertofilho.endlessrunner.controllers.containers.DisplayPanel;
import br.studio.calbertofilho.endlessrunner.controllers.containers.Window;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Keyboard;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Mouse;
import br.studio.calbertofilho.endlessrunner.controllers.managers.GameStates;
import br.studio.calbertofilho.endlessrunner.controllers.soundsystem.AudioPlayer;

public class MenuScreen extends CommonScreen {

	private Font titleFont, menuFont;
	private ArrayList<Rectangle> items;
	private String[] options = { "Iniciar", "Opcoes", "Ajuda", "Sair" };
	private int currentSelection = 0;
	private int titlePadding = 100;
	private Point menuPosition = new Point(1100, 400);
	private AudioPlayer menu_bgm, selection_fx, confirmation_fx, close_fx;
	private Image[] layers;

	public MenuScreen(GameStates manager) {
		super(manager);
	}

	@Override
	protected void init() {
		try {
			layers = new Image[] {  ImageIO.read(new BufferedInputStream(getClass().getResourceAsStream("/assets/images/scenery/parallax1.png"))),
									ImageIO.read(new BufferedInputStream(getClass().getResourceAsStream("/assets/images/scenery/parallax2.png"))),
									ImageIO.read(new BufferedInputStream(getClass().getResourceAsStream("/assets/images/scenery/parallax3.png"))),
									ImageIO.read(new BufferedInputStream(getClass().getResourceAsStream("/assets/images/scenery/parallax4.png"))),
									ImageIO.read(new BufferedInputStream(getClass().getResourceAsStream("/assets/images/scenery/parallax5.png")))  };
			titleFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/fonts/AREA-Q_DESERT.ttf"));
			menuFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/fonts/Desert Road.otf"));
			items = new ArrayList<Rectangle>();
			menu_bgm = new AudioPlayer(new BufferedInputStream(getClass().getResourceAsStream("/assets/sounds/bgm/game-menu.wav")));
			menu_bgm.setVolume(1.0f); // 50%
			menu_bgm.playSoundContinuously();
			selection_fx = new AudioPlayer(new BufferedInputStream(getClass().getResourceAsStream("/assets/sounds/fx/menu-selection.wav")));
			selection_fx.setVolume(2.0f); // 100%
			confirmation_fx = new AudioPlayer(new BufferedInputStream(getClass().getResourceAsStream("/assets/sounds/fx/menu-confirmation.wav")));
			confirmation_fx.setVolume(2.0f); // 100%
			close_fx = new AudioPlayer(new BufferedInputStream(getClass().getResourceAsStream("/assets/sounds/fx/menu-close_game.wav")));
			close_fx.setVolume(2.0f); // 100%
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void input(Mouse mouse, Keyboard keyboard) {
		if (selection_fx.isPlaying())
			selection_fx.stopSound();
		if (keyboard.wasTyped(KeyEvent.VK_ESCAPE)) {
			closeGame();
		}
		if ((keyboard.wasTyped(KeyEvent.VK_UP)) || (keyboard.wasTyped(KeyEvent.VK_W)) || (mouse.getScroll() == -1)) {
			selection_fx.playSoundOnce();
			currentSelection--;
			if (currentSelection < 0)
				currentSelection = options.length - 1;
		}
		if ((keyboard.wasTyped(KeyEvent.VK_DOWN)) || (keyboard.wasTyped(KeyEvent.VK_S)) || (mouse.getScroll() == 1)) {
			selection_fx.playSoundOnce();
			currentSelection++;
			if (currentSelection >= options.length)
				currentSelection = 0;
		}
		if (keyboard.wasTyped(KeyEvent.VK_ENTER)) {
			menuAction();
		}
		for (Rectangle item : items) {
			if (item.contains(mouse.getPosition())) {
				if (currentSelection != items.indexOf(item)) {
					selection_fx.playSoundOnce();
					currentSelection = items.indexOf(item);
				}
				if ((mouse.wasClicked(MouseEvent.BUTTON1)) && (currentSelection == items.indexOf(item))) {
					menuAction();
					return;
				}
			}
		}
	}

	private void menuAction() {
		menu_bgm.stopSound();
		if (currentSelection == 0) {		// Play state
			confirmation_fx.playSoundOnce();
			manager.changeState(new PlayScreen(manager));
		} else if (currentSelection == 1) {	// Configuration state
			confirmation_fx.playSoundOnce();
			manager.changeState(new ConfigurationScreen(manager));
		} else if (currentSelection == 2) {	// Tutorial state
			confirmation_fx.playSoundOnce();
			manager.changeState(new TutorialScreen(manager));
		} else if (currentSelection == 3) {	// Quit game
			closeGame();
		}
	}

	private void closeGame() {
		close_fx.playSoundOnce();
		menu_bgm.stopSound();
		System.exit(0);
	}

	@Override
	public void update() {}

	@Override
	public void render(Graphics2D graphics) {
	// draw background
		for (int i = 0; i < layers.length - 1; i++)
			graphics.drawImage(layers[i], 0, 0, DisplayPanel.getGameWidth(), DisplayPanel.getGameHeight(), null);
	// show game title
		graphics.setColor(Color.ORANGE.darker());
		graphics.setFont(titleFont.deriveFont(Font.PLAIN, 98));
		String[] titleWords = Window.title.replaceAll("\\s+", ",").split(","),
				 titleLines = { titleWords[0] + " "+titleWords[1],
						 		titleWords[2] + " "+titleWords[3] };
		for (int i = 0; i < titleLines.length; i++) {
			int lineLength = (int) graphics.getFontMetrics().getStringBounds(titleLines[i], graphics).getWidth();
			graphics.drawString(titleLines[i], (DisplayPanel.getGameWidth() - lineLength) / 2, titlePadding + i * graphics.getFontMetrics().getHeight());
		}
	// animation on player presentation
		
	// show menu option
		graphics.setFont(menuFont.deriveFont(Font.BOLD, 60));
		int optionWidthMax = 0, optionHeight = graphics.getFontMetrics().getHeight();
		String option = null;
		for (int i = 0; i < options.length; i++) {
			option = options[i];
			optionWidthMax = (graphics.getFontMetrics().stringWidth(option) > optionWidthMax) ? graphics.getFontMetrics().stringWidth(option) : optionWidthMax;
		}
		for (int i = 0; i < options.length; i++) {
			option = options[i];
			if (i == currentSelection) {
				graphics.setColor(Color.GREEN.darker());
				graphics.fillRect(menuPosition.x - 5, menuPosition.y - (optionHeight / 2) + (i * optionHeight) - 5, optionWidthMax + 10, optionHeight - (graphics.getFont().getSize() / 2) + 10);
				graphics.setColor(Color.WHITE);
				graphics.fillRect(menuPosition.x, menuPosition.y - (optionHeight / 2) + (i * optionHeight), optionWidthMax, optionHeight - (graphics.getFont().getSize() / 2));
				graphics.setColor(Color.GREEN.darker());
			}
			else
				graphics.setColor(Color.WHITE);
			graphics.drawString(option, menuPosition.x + (optionWidthMax - graphics.getFontMetrics().stringWidth(option)) / 2, menuPosition.y + (i * optionHeight));
			items.add(i, new Rectangle(menuPosition.x - 5, menuPosition.y - (optionHeight / 2) + (i * optionHeight) - 5, optionWidthMax + 10, optionHeight - (graphics.getFont().getSize() / 2) + 10));
		}
	}

}
