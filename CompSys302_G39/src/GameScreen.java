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
		
		Menu Main = new Menu();
		Main.AddButton("Start", 100, 40, 50, 100);
		Main.AddButton("Continue", 100, 40, 100, 100);
		Main.AddButton("Options", 100, 40, 150, 100);
		Main.AddButton("Exit", 100, 40, 200, 100);
		
		Menu Options = new Menu();
		Options.AddButton("thing2", 100, 40, 50, 100);
		Options.AddButton("thing3", 100, 40, 100, 100);
		Options.AddButton("Back", 100, 40, 150, 100);
		
		long delta = 0;
		while(true){
			long lastTime = System.nanoTime();
			
			Game.Update((float)(delta/1000000000.0));
			
			Control.update(f, Game, Main, Options);
			
			delta = System.nanoTime() - lastTime;
		
			Render.render(Game, Main.GetPanel(),Options.GetPanel());
			
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