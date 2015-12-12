package thegame;

import java.net.URL;
import javax.sound.sampled.*;
import static sun.plugin.javascript.navig.JSType.URL;

public class Sound {
    static Object sound1;
	
	private Clip clip;
	
	// Change file name to match yours, of course
	public static Sound sound = new Sound("Client/src/resources/sample14.wav");
	//public static Sound sound2 = new Sound("/sound2.wav");
	//public static Sound sound3 = new Sound("/sound3.wav");
	
	public Sound (String fileName) {
		try {
                        URL url = this.getClass().getResource("InGameSound.wav");
                        AudioInputStream aiStream = AudioSystem.getAudioInputStream(url);   
			clip = AudioSystem.getClip();
			clip.open(aiStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop(){
		if(clip == null) return;
		clip.stop();
	}
	
	public void loop() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isActive(){
		return clip.isActive();
	}
}