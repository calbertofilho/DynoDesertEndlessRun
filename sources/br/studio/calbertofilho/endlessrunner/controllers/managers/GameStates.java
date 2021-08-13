package br.studio.calbertofilho.endlessrunner.controllers.managers;

import java.awt.Graphics2D;
import java.util.Stack;

import br.studio.calbertofilho.endlessrunner.controllers.handlers.Keyboard;
import br.studio.calbertofilho.endlessrunner.controllers.handlers.Mouse;
import br.studio.calbertofilho.endlessrunner.controllers.managers.states.CommonScreen;
import br.studio.calbertofilho.endlessrunner.controllers.managers.states.SplashScreen;

public class GameStates {

	private Stack<CommonScreen> states;

	public GameStates() {
		states = new Stack<CommonScreen>();
		states.push(new SplashScreen(this));
	}

	public void changeState(CommonScreen newState) {
		states.push(newState);
	}

	public void input(Mouse mouse, Keyboard keyboard) {
		states.peek().input(mouse, keyboard);
	}

	public void update() {
		states.peek().update();
	}

	public void render(Graphics2D graphics) {
		states.peek().render(graphics);
	}

}
