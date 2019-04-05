import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameScreen {

	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		GameEngine Game = new GameEngine();
		RenderEngine Render = new RenderEngine(f);
		Controller Control = new Controller();
		
		long delta = 0;
		Game.GameInit();
		while(true){
			long lastTime = System.nanoTime();
			
			Game.Update((float)(delta/1000000000.0));
			
			Control.update(f, Game);
			
			delta = System.nanoTime() - lastTime;
		
			Render.render(Game);
			
			//Limits FrameRate;
			if(delta < 20000000L) {
				try {
					Thread.sleep((20000000L - delta)/1000000L);
				} catch(Exception e) {
					
				}
			}
			
		}
	}
}