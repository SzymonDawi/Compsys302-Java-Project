import java.io.File;
import java.io.IOException;


import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Sound {

		Clip clip;
		boolean Mute = true;


		public void getSound(String soundFileName) { //this loads in the sound file (must use full file path)
			try {
				File file = new File("sounds/" + soundFileName + ".wav");
				AudioInputStream sound = AudioSystem.getAudioInputStream(file);
				clip = AudioSystem.getClip();
				clip.open(sound);
				Mute = false;
			}
			catch(Exception e) {
				
			}
		}	
		
		public void playSound() {  // this plays the whole sound clip once
			clip.setFramePosition(0);
			clip.start();
		}
		
		public void stopSound() {  // this plays the whole sound clip once
			clip.stop();
		}
		
		
		public void loopSound(int count) { //loops sound file 'count' number of times. Type LOOP_CONTINUOUSLY to repeat forever
			clip.setFramePosition(0);
			clip.start();
			clip.loop( count);

		}
	
		
		public float getVol() {
			FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			return (float) Math.pow(10f, gain.getValue()/20f);
		}
		
		public void setVol( double vol) {
			if(vol> 0f && vol < 1f) {
				FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gain.setValue(20f * (float)Math.log10(vol));
			}
		}
		
		public void Mute(boolean Mute) {
			this.Mute = Mute;
		}
		
		public boolean isMute() {
			return Mute;
		}
		
}