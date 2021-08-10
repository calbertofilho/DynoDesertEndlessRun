package br.studio.calbertofilho.endlessrunner;

import javax.swing.ImageIcon;

import br.studio.calbertofilho.endlessrunner.controllers.containers.Window;

public class Launcher {

	public static void main(String[] args) {
		new Window("Dyno Desert Endless Run  v1.0", new ImageIcon("resources/assets/images/icon/gameIcon.png").getImage(), 1280, 720);
	}

}
