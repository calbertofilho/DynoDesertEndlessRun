package br.studio.calbertofilho.endlessrunner.controllers.handlers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import br.studio.calbertofilho.endlessrunner.controllers.containers.DisplayPanel;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static final int BUTTONS_COUNT = 10;
	private Point pos, curPos;
	private boolean[] buttons;
	private InputID[] buttonsState;
	private int scroll;

	public Mouse(DisplayPanel game) {
		pos = curPos = new Point(0, 0);
		buttons = new boolean[BUTTONS_COUNT];
		buttonsState = new InputID[BUTTONS_COUNT];
		scroll = 0;
		for (int i = 0; i < buttons.length; i++) {
			buttonsState[i] = InputID.RELEASED;
		}
		game.addMouseListener(this);
		game.addMouseWheelListener(this);
		game.addMouseMotionListener(this);
	}

	public synchronized void update() {
		pos.setLocation(curPos);
		scroll = 0;
		for (int i = 0; i < buttons.length; i++) {
			if (buttons[i]) {
				if (buttonsState[i] == InputID.RELEASED) {
					buttonsState[i] = InputID.ONCE; 
				} else {
					buttonsState[i] = InputID.PRESSED; 
				}
			} else {
				buttonsState[i] = InputID.RELEASED; 
			}
		}
	}

	public boolean isPressed(int button) {
		return ((buttonsState[button] == InputID.ONCE) || (buttonsState[button] == InputID.PRESSED));
	}

	public boolean wasClicked(int button) {
		return buttonsState[button] == InputID.ONCE;
	}

	public int getX() {
		return pos.x;
	}

	public int getY() {
		return pos.y;
	}

	public Point getPosition() {
		return pos;
	}

	public int getScroll() {
		return scroll;
	}

	@Override
	public synchronized void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}
	
	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	@Override
	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		curPos.setLocation(e.getPoint());
	}

	@Override
	public synchronized void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

}
