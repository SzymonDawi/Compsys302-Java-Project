import javax.swing.JFrame;

public class GameScreen {

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500,500);
		
		GameEngine Game = new GameEngine();
		Controller Control = new Controller();
		
		Game.init();
		RenderEngine Render = new RenderEngine(f, Game);
		f.add(Render);
		//f.setContentPane(Render);
		
		long delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		f.setVisible(true);
		
		while(Game.IsRunning()){
			long lastTime = System.nanoTime();

			Control.update(f, Game);
			
			Game.Update((float)(delta/1000000000.0));
			
			//Render.repaint();
			
			delta = System.nanoTime() - lastTime;
			
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