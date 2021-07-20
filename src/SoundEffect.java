
import javax.sound.sampled.Clip;

public class SoundEffect {
	
	public static enum Volume {
		MUTE,	// off
		NORMAL	// on
	}
	// volume off by default
	public static Volume volume = Volume.MUTE;
	
	// used to handle support problem of sound under linux system
	protected static boolean isSoundAvailable = true;
	
	// Each sound effect has its own clip, loaded with its own sound file.
	private Clip clip;
	
	/**
	 * Sound switches
	 * @param v
	 */
	public static void setVolume(Volume v) {
		SoundEffect.volume = v;
	}
	
	/**
	 * Play or Re-play the sound effect from the beginning, by rewinding.
	 */
	public void play(int times) {
		if(volume != Volume.MUTE) {
			if(clip.isRunning())
				return;
			clip.setFramePosition(0);
			clip.start();
			clip.loop(times);
		}
	}
	
	/**
	 * Stop playing music
	 */
	public void stopPlay() {
		if(clip.isRunning())
			clip.stop();
	}
	
	/**
	 * 
	 * @return	clip object
	 */
	public Clip getClip() {
		return this.clip;
	}
	

	/**
	 * 
	 * @param clip
	 */
	public void setClip(Clip clip) {
		this.clip = clip; 
	}
	
}
