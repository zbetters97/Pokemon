package application;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*** SOUND CARD CLASS ***/
public class SoundCard {
	
	private static boolean active = true;
	private Clip music;
	
	/** CONSTRUCTOR **/
	public SoundCard(String soundfile) {
		
		try {			
			// retrieve sound file based on argument given
			String path = new File("").getAbsolutePath() + "//lib//sounds//" + soundfile + ".wav";	
		    AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            
		    music = AudioSystem.getClip();
		    
		    // prepare music file
		    music.open(ais); 
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
	}
	/** END CONSTRUCTOR **/
	
	/** SET ACTIVE METHOD **/
	public static void setActive(boolean act) {
		active = act;
	}
	/** END SET ACTIVE METHOD **/
	
	/** PLAY/STOP MUSIC METHODS **/
	public void playMusic() {
		music.start();
		music.loop(Clip.LOOP_CONTINUOUSLY);
	}			
	public void stopMusic() {
		music.stop();
	}
	/** END PLAY/STOP MUSIC METHODS **/
	
	/** SOUND CARD METHOD **/
	// optional parameter to wait until finished playing //
	public static void play(String arg, boolean... hold) {
		
		if (active) {
			try {
				// retrieve sound file based on argument given
				String path = new File("").getAbsolutePath() + "//lib//sounds//" + arg + ".wav";	
	            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
	            
	            Clip c = AudioSystem.getClip();            
	            c.open(ais); c.start(); 
	            
	            // wait until sound file has finished playing
	            if (hold.length >= 1) {
	            	double duration = (ais.getFrameLength() + 0.0) / ais.getFormat().getFrameRate(); 
	            	Sleeper.pause(duration);
	            }
	        }
	        catch (Exception e) { 
	        	
	        }
		}
	}
	/** END SOUND CARD METHOD **/
	
	/** HIT SOUND METHOD **/
	public static void playHit(double effectiveness) {
		
		String hit = "";
		
		// set hit to corresponding path
		switch (Double.toString(effectiveness)) {
			case "0.5": hit = "hit-weak.wav"; break;
			case "1.0": hit = "hit-normal.wav"; break;
			case "1.5": hit = "hit-super.wav"; break;
			
			default: return;
		}
		
		String path = new File("").getAbsolutePath() + "//lib//sounds//in-battle//" + hit;		
        File sound = new File(path);
		
		if (active) {        
	        try {
	            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
	            
	            double duration = (ais.getFrameLength() + 0.0) / ais.getFormat().getFrameRate(); 
	            Clip c = AudioSystem.getClip();
	            c.open(ais); c.start(); 
	            
	            Sleeper.pause(duration);
	        }
	        catch (Exception e) { }
        }
        
        if (effectiveness == 1.5) 
        	Sleeper.print("It's super effective!");
		else if (effectiveness == .5)
			Sleeper.print("It's not very effective...");
		else if (effectiveness == 0) 
			Sleeper.print("It has no effect!");
	}
	/** END HIT SOUND METHOD **/
	
	/** STATUS SOUND METHOD **/
	public static void playStatus(String status) {
		
		String hit = "";
		
		// set hit to corresponding path
		switch (status) {
			case "PRZ": hit = "paralyzed.wav"; break;
			case "PSN": hit = "poison.wav"; break;
			case "CNF": hit = "confused.wav"; break;
			case "BRN": hit = "burned.wav"; break;
			case "FRZ": hit = "frozen.wav"; break;
			case "SLP": hit = "asleep.wav"; break;
			
			default: return;
		}
		
		String path = new File("").getAbsolutePath() + "//lib//sounds//in-battle//" + hit;		
        File sound = new File(path);
        
        if (active) {
	        try {
	            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
	            
	            double duration = (ais.getFrameLength() + 0.0) / ais.getFormat().getFrameRate();
	            
	            Clip c = AudioSystem.getClip();
	            c.open(ais); c.start(); 
	            
	            Sleeper.pause(duration);
	        }
	        catch (Exception e) { }
        }
	}
	/** END STATUS SOUND METHOD **/
}
/*** END SOUND CARD CLASS ***/