package br.studio.calbertofilho.endlessrunner.controllers.managers.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import br.studio.calbertofilho.endlessrunner.controllers.containers.DisplayPanel;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Keyboard;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Mouse;
import br.studio.calbertofilho.endlessrunner.controllers.managers.GameStates;

public class SplashScreen extends CommonScreen {

	private Font loadingFont;
	private Image background, loading;
	private long initialTime;
	private String text;

	public SplashScreen(GameStates manager) {
		super(manager);
	}

	@Override
	protected void init() {
		try {
			loadingFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/assets/fonts/Desert Road.otf"));
			background = ImageIO.read(new BufferedInputStream(getClass().getResourceAsStream("/assets/images/app/splash.png")));
			loading = new ImageIcon(getClass().getResource("/assets/images/app/resources-loading.gif")).getImage();
			initialTime = System.nanoTime();
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void input(Mouse mouse, Keyboard keyboard) {}

	@Override
	public void update() {
		// load resources and change state
		if (((System.nanoTime() - initialTime) / 1000000000) <= 1)
			text = "Carregando recursos...";
		else if (((System.nanoTime() - initialTime) / 1000000000) < 3)
			text = "Carregando fontes...";
		else if (((System.nanoTime() - initialTime) / 1000000000) < 5)
			text = "Carregando textos...";
		else if (((System.nanoTime() - initialTime) / 1000000000) < 7)
			text = "Carregando imagens...";
		else
			text = "Iniciando...";
		if (((System.nanoTime() - initialTime) / 1000000000) >= 10)
			manager.changeState(new MenuScreen(manager));
	}

	@Override
	public void render(Graphics2D graphics) {
		graphics.drawImage(background, (DisplayPanel.getGameWidth() - background.getWidth(null)) / 2, (DisplayPanel.getGameHeight() - background.getHeight(null)) / 2, background.getWidth(null), background.getHeight(null), null);
		graphics.drawImage(loading, 370, 410, loading.getWidth(null), loading.getHeight(null), null);
		graphics.setFont(loadingFont.deriveFont(Font.BOLD, 24));
		graphics.setColor(Color.WHITE);
		int textLength = (int) graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
		graphics.drawString(text, (DisplayPanel.getGameWidth() - textLength) / 2, 405 + ((loading.getHeight(null) + graphics.getFontMetrics().getHeight()) / 2));
	}

}
