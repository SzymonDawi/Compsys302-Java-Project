
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderEngine {
	private JFrame f = new JFrame();
	private GameEngine Engine = new GameEngine();
	private JPanel Main = new JPanel();
	private JPanel Options = new JPanel();
	//Double buffering
	//private BufferedImage Screen = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
	RenderEngine(JFrame Frame, GameEngine Engine,JPanel Main, JPanel Options){
		f = Frame;
		this.Engine = Engine;
		this.Main = Main;
		this.Options = Options;
	}
	private Graphics g;
	private Map Map = new Map();
	private int i;
	
	public void render() {
		f.setVisible(true);
		BufferStrategy Buffer = f.getBufferStrategy();
		if(Buffer == null) {
			f.createBufferStrategy(3);
			return;
		}
		
		g = Buffer.getDrawGraphics();
		
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 500);
		
		//Map
		g.drawImage(Map.GetImage(), Map.GetX(),Map.GetY(), f);
		
		///Enemies
		for(i=0;i <Engine.GetNumberOfEnemies(); i++) {
			//use this for drawing an sprite
			//g.drawImage(img, x, y, observer)
		}
		
		//Player
		g.setColor(Color.blue);
		g.fillRect(Engine.GetPlayer().GetX(), Engine.GetPlayer().GetY(), 10, 10);
		
		g.dispose();
		Buffer.show();
	}
	
	//Things that only need to be initialized once
	public void init() {
		Map.init();
	}
//	private void DoDrawing() {
//		if(Engine.GetGameState() == 0) {
//			f.setContentPane(Main);
//        }
//        else if(Engine.GetGameState() == 1) {
//        	f.setContentPane(Options);
//            //Options.repaint();
//        }
//        else if(Engine.GetGameState() == 2) {    
//        	f.setContentPane(Scene);
//            Scene.requestFocus();
//            //Scene.repaint();
//        }
//	}
}