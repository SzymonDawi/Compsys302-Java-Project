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
		//f.setContentPane(Render);
		
		boolean tick = false;
		long delta = 0;
		long Start = System.nanoTime();
		f.setVisible(true);
		
		while(Game.IsRunning()){
			long lastTime = System.nanoTime();
			
			Game.Update(tick);
			
			//Render.repaint();
			
			delta = System.nanoTime() - lastTime;
			
			delta = (lastTime - Start)/1000000;
			
			if(delta > 15) {
				tick = !tick;
				Start = lastTime;
			}
//			frames ++;
//			if(System.currentTimeMillis() - timer > 1000) {
//				timer +=1000;
//				System.out.println("FPS: " + frames/3);
//				frames = 0;
//			}
			
		}
		f.setVisible(false);
		System.exit(0);
	}
}