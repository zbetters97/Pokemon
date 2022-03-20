package application;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/*** SOUND CARD CLASS ***/
public class SoundCard {
	
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
		
		try {
			// retrieve sound file based on argument given
			String path = new File("").getAbsolutePath() + "//lib//sounds//" + arg + ".wav";	
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
            
            Clip c = AudioSystem.getClip();            
            c.open(ais); c.start(); 
            
            // wait until sound file has finished playing
            if (hold.length >= 1) {
            	int duration = (int) ((ais.getFrameLength() + 0.0) / ais.getFormat().getFrameRate()) * 1000; 	
            	Sleeper.pause(duration);
            }
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
	}
	/** END SOUND CARD METHOD **/
	
	/** HIT SOUND METHOD **/
	public static void play(double effectiveness) {
		
		String hit = "";
		
		// set hit to corresponding path
		switch (Double.toString(effectiveness)) {
			case "0.5": hit = "hit-weak.wav"; break;
			case "1.0": hit = "hit-normal.wav"; break;
			case "2.0": hit = "hit-super.wav"; break;
			case "-1.0": hit = "paralyzed.wav"; break;
			case "-2.0": hit = "poison.wav"; break;
			case "-3.0": hit = "confused.wav"; break;
			case "-4.0": hit = "burned.wav"; break;
			case "-5.0": hit = "frozen.wav"; break;
			case "-6.0": hit = "asleep.wav"; break;
			
			default: return;
		}
		
		String path = new File("").getAbsolutePath() + "//lib//sounds//in-battle//" + hit;		
        File sound = new File(path);
        
        if (effectiveness == 2.0) 
        	System.out.println("It's super effective!");
		else if (effectiveness == .5)
			System.out.println("It's not very effective...");
		else if (effectiveness == 0) 
			System.out.println("It has no effect!");

        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
            
            int duration = (int) ((ais.getFrameLength() + 0.0) / ais.getFormat().getFrameRate()) * 1000; 
            
            Clip c = AudioSystem.getClip();
            c.open(ais); c.start(); 
            
            Sleeper.pause(duration);
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
	}
	/** END HIT SOUND METHOD **/
}
/*** END SOUND CARD CLASS ***/