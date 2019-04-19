import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

		Clip clip;
	


		public void getSound(String soundFileName) { //this loads in the sound file (must use full file path)
			try {
				File file = new File("sounds/" + soundFileName + ".wav");
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(sound);
			}
			catch(Exception e) {
				
			}
		}	
		
		public void playSound() {  // this plays the whole sound clip once
			clip.setFramePosition(0);
			clip.start();
		}
		
		public void loopSound(int count) { //loops sound file 'count' number of times. Type LOOP_CONTINUOUSLY to repeat forever
			clip.loop( count);

		}
}