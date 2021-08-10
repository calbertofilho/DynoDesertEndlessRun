package br.studio.calbertofilho.endlessrunner.controllers.soundsystem;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer implements LineListener {

	private File file;
	private AudioInputStream sound, decodedSound;
    private AudioFormat baseFormat, format;
    private DataLine.Info info;
    private Clip player;
    private FloatControl volumeControl;
    private BooleanControl muteControl;
	private long streamLength;
	private int loops;

	public AudioPlayer(String soundPath) {
		try {
			initPlayer(soundPath);
			createPlayer();
			initControls();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	private void initPlayer(String soundPath) throws UnsupportedAudioFileException, IOException {
		loops = 0;
		file = new File(soundPath);
		sound = AudioSystem.getAudioInputStream(file);
		baseFormat = sound.getFormat();
		format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
		info = new DataLine.Info(Clip.class, format);
		decodedSound = AudioSystem.getAudioInputStream(format, sound);
	}

	private void createPlayer() throws LineUnavailableException, IOException {
		player = (Clip) AudioSystem.getLine(info);
		player.open(decodedSound);
	}

	private void reloadPlayer() throws LineUnavailableException, IOException {
		closePlayer();
		createPlayer();
	}

	public void closePlayer() {
		stopSound();
		player.drain();
		player.close();
	}

	private void initControls() {
        volumeControl = (FloatControl) player.getControl(FloatControl.Type.MASTER_GAIN);
        muteControl = (BooleanControl) player.getControl(BooleanControl.Type.MUTE);
	}

	private void playSound(int loops) {
		try {
			this.loops = loops;
			if (isReady() && !isPlaying())
				player.start();
			else {
				if (isPlaying())
					stopSound();
				if (!isReady())
					reloadPlayer();
				playSound(loops);
			}
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}

	public void playSoundOnce() {
		playSound(0);
	}

	public void playSoundContinuously() {
		playSound(Clip.LOOP_CONTINUOUSLY);
	}

	public void setPause(boolean pause) {
		if (pause)
			pauseSound();
		else
			resumeSound();
	}

	private void pauseSound() {
		if (isPlaying()) {
			streamLength = player.getMicrosecondPosition();
			player.stop();
		}
	}

	private void resumeSound() {
		if (!isPlaying() && (streamLength != 0)) {
			player.setMicrosecondPosition(streamLength);
			player.start();
		} else
			playSound(loops);
	}

	public void stopSound() {
		if (isPlaying()) {
			player.stop();
			streamLength = 0;
			player.setMicrosecondPosition(streamLength);
		}
	}

	/**
	 * @param volume
	 * 
	 * 			value:	Range between 0.0f to 2.0f
	 * 					where 0.0f is 0% and 2.0f is 100% of volume
	 */
	public void setVolume(float volume) { 
		volumeControl.setValue((float) Math.min(volumeControl.getMaximum(), Math.max(volumeControl.getMinimum(), ((Math.log(volume) / Math.log(10.0)) * 20.0))));
	}

	public void setMute(boolean mute) {
		muteControl.setValue(mute);
	}

	public void fadeIn(float deltaTime) {}

	public void fadeOut(float deltaTime) {}

	public boolean isPlaying() {
		return player.isRunning();
	}

	public boolean isReady() {
		return player.isOpen();
	}

	@Override
	public void update(LineEvent event) {
		if (event.getType() == LineEvent.Type.STOP)
			stopSound();
	}

}
