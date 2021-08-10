package br.studio.calbertofilho.endlessrunner.animations;

import java.awt.image.BufferedImage;

public class Motion {

	private BufferedImage[] frames;
	private int currentFrame, numFrames, count, delay, timesPlayed;

	public Motion(BufferedImage[] frames) {
		this();
		setFrames(frames);
	}

	public Motion() {
		timesPlayed = 0;
		currentFrame = 0;
		count = 0;
		delay = 2;
	}

	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		numFrames = frames.length;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public void setFrame(int frame) {
		currentFrame = frame;
	}

	public void setNumFrames(int num) {
		numFrames = num;
	}

	public void update() {
		if (delay == -1) 
			return;
		count++;
		if (count == delay) {
			currentFrame++;
			count = 0;
		}
		if (currentFrame == numFrames) {
			currentFrame = 0;
			timesPlayed++;
		}
	}

	public int getDelay() {
		return delay;
	}
	
	public int getFrame() {
		return currentFrame;
	}

	public int getCount() {
		return count;
	}

	public BufferedImage getImage() {
		return frames[currentFrame];
	}

	public boolean hasPlayed(int num) {
		return timesPlayed == num;
	}

	public boolean hasPlayedOnce() {
		return timesPlayed > 0;
	}

}
