
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenderEngine {
	private JFrame f = new JFrame();
	private GameEngine Engine = new GameEngine();
	//Double buffering
	//private BufferedImage Screen = new BufferedImage(600,600,BufferedImage.TYPE_INT_RGB);
	RenderEngine(JFrame Frame, GameEngine Engine){
		f = Frame;
		this.Engine = Engine;
	}
	private Graphics g;
	//private Map Map = new Map();
	private int i;
	
	public void render() {
		f.setVisible(true);
		BufferStrategy Buffer = f.getBufferStrategy();
		if(Buffer == null) {
			f.createBufferStrategy(3);
			return;
		}
		
		g = Buffer.getDrawGraphics();
		
		if(Engine.GetState() == 0) {
			Menu Menu = Engine.GetMainMenu();
			for(i=0;i< Menu.GetNumberOfButtons(); i++) {
				g.setColor(Color.cyan);
				if(Menu.GetButton(i).Selected()) {
					g.setColor(Color.magenta);
				}
				g.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 200, 50);
				g.setColor(Color.BLACK);
				g.drawString(Menu.GetButton(i).GetName(), Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY());
				//g.drawImage(img, x, y, observer)
			}
		}
		else if(Engine.GetState() ==1) {
			
		}
		else if(Engine.GetState() == 2) {
			//Map
			g.drawImage(Engine.Getlevel(),0,0,f);
			///Enemies
			for(i=0;i <Engine.GetNumberOfEnemies(); i++) {
				//use this for drawing an sprite
				//g.drawImage(img, x, y, observer)
			}
			
			//Player
			g.setColor(Color.blue);
			g.fillRect(Engine.GetPlayer().GetX(), Engine.GetPlayer().GetY(), 10, 10);	
		}	
		g.dispose();
		Buffer.show();
	}
	
	//Things that only need to be initialized once
	public void init() {
		
	}
}