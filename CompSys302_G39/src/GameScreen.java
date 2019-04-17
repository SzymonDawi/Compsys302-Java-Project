import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameScreen {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500,500);
		
		GameEngine Game = new GameEngine();
		Controller Control = new Controller();
		//MouseInput Mouse = new MouseInput();
		
		//init menus could be moved to a new method
		Menu Main = new Menu();
		Main.AddButton("Start", 100, 40, 50, 100);
		Main.AddButton("Continue", 100, 40, 100, 100);
		Main.AddButton("Options", 100, 40, 150, 100);
		Main.AddButton("Exit", 100, 40, 200, 100);
		
		Menu Options = new Menu();
		Options.AddButton("thing2", 100, 40, 50, 100);
		Options.AddButton("thing3", 100, 40, 100, 100);
		Options.AddButton("Back", 100, 40, 150, 100);
		
		RenderEngine Render = new RenderEngine(f, Game, Main.GetPanel(), Options.GetPanel());
		Render.init();
		
		long delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(Game.IsRunning()){
			
			long lastTime = System.nanoTime();

			Control.update(f, Game, Main, Options);
			
			//f.addKeyListener(new KeyInput(Game));
			
			Game.Update((float)(delta/1000000000.0));
			
			delta = System.nanoTime() - lastTime;
		
			Render.render();
			
			frames ++;
			if(System.currentTimeMillis() - timer > 1000) {
				timer +=1000;
				System.out.println("FPS: " + frames/3);
				frames = 0;
			}
		}
		f.setVisible(false);
		f.dispose();
	}
}