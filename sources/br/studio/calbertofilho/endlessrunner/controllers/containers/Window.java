package br.studio.calbertofilho.endlessrunner.controllers.containers;

import java.awt.Image;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private DisplayPanel game;
	private int width, height;
	public static String title;

	public Window(String title, Image icon, int width, int height) {
		this.width = width;
		this.height = height;
		Window.title = title;
		setTitle(title);
		setIconImage(icon);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(getContentPane().getPreferredSize());
		setMaximumSize(getContentPane().getPreferredSize());
		pack();
		setIgnoreRepaint(true);
		setFocusable(false);
		setResizable(false);
		setLocationRelativeTo(null);
//		to FullScreen Mode
//		setSize(Toolkit.getDefaultToolkit().getScreenSize());
//		etUndecorated(true);
		setVisible(true);
	}

	@Override
	public void addNotify() {
		super.addNotify();
		game = new DisplayPanel(width, height);
		setContentPane(game);
	}

}
