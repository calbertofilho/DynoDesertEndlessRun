package br.studio.calbertofilho.endlessrunner.controllers.managers.states;

import java.awt.Graphics2D;

import br.studio.calbertofilho.endlessrunner.controllers.handlers.Keyboard;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Mouse;
import br.studio.calbertofilho.endlessrunner.controllers.managers.GameStates;

public class TutorialScreen extends CommonScreen {

	public TutorialScreen(GameStates manager) {
		super(manager);
	}

	@Override
	protected void init() {}

	@Override
	public void input(Mouse mouse, Keyboard keyboard) {}

	@Override
	public void update() {}

	@Override
	public void render(Graphics2D graphics) {}

}
