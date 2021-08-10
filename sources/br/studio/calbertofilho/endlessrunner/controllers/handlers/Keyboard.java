package br.studio.calbertofilho.endlessrunner.controllers.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import br.studio.calbertofilho.endlessrunner.controllers.containers.DisplayPanel;

public class Keyboard implements KeyListener {

	private static final int KEYS_COUNT = 256;
	private boolean[] keys;
	private InputID[] keysState;

	public Keyboard(DisplayPanel game) {
		keys = new boolean[KEYS_COUNT];
		keysState = new InputID[KEYS_COUNT];
		for (int i = 0; i < keys.length; i++) {
			keysState[i] = InputID.RELEASED;
		}
		game.addKeyListener(this);
	}

	public synchronized void update() {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i]) {
				if(keysState[i] == InputID.RELEASED) {
					keysState[i] = InputID.ONCE;
				} else {
					keysState[i] = InputID.PRESSED;
				}
			} else {
				keysState[i] = InputID.RELEASED;
			}
		}
	}

	public boolean isPressed(int keyCode) { // return true the entire time the key is down
		return ((keysState[keyCode] == InputID.ONCE) || (keysState[keyCode] == InputID.PRESSED));
	}

	public boolean wasTyped(int keyCode) { // return true the first time the key is down
		return keysState[keyCode] == InputID.ONCE;
	}

	@Override
	public synchronized void keyPressed(KeyEvent e) {
		if ((e.getKeyCode() >= 0) && (e.getKeyCode() < KEYS_COUNT))
			keys[e.getKeyCode()] = true;
	}

	@Override
	public synchronized void keyReleased(KeyEvent e) {
		if ((e.getKeyCode() >= 0) && (e.getKeyCode() < KEYS_COUNT))
			keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

}
