import javax.swing.JFrame;

public class GameScreen {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1024,768);
		
		GameEngine Game = new GameEngine();
		Controller Control = new Controller(f, Game, 24);
		
		Game.init();
		Control.init();
		RenderEngine Render = new RenderEngine(f, Game);
		f.add(Render);
		
		boolean tick = false;
		long delta = 0;
		long Start = System.nanoTime();
		f.setVisible(true);
		
		//Game Loop
		while(Game.IsRunning()){
			long lastTime = System.nanoTime();
			
			Game.Update(tick);
			
			delta = System.nanoTime() - lastTime;
			delta = (lastTime - Start)/1000000;
			
			if(delta > 15) {
				tick = !tick;
				Start = lastTime;
			}
		}
		f.setVisible(false);
		System.exit(0);
	}
}