package br.studio.calbertofilho.endlessrunner.controllers.managers.states;

import java.awt.Graphics2D;

import br.studio.calbertofilho.endlessrunner.controllers.handlers.Keyboard;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Mouse;
import br.studio.calbertofilho.endlessrunner.controllers.managers.GameStates;

public abstract class CommonScreen {

	protected GameStates manager;

	protected CommonScreen(GameStates manager) {
		this.manager = manager;
		init();
	}

	protected abstract void init();
	public abstract void input(Mouse mouse, Keyboard keyboard);
	public abstract void update();
	public abstract void render(Graphics2D graphics);

}
