
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RenderEngine extends JPanel implements ActionListener{
	private JFrame f = new JFrame();
	private GameEngine Engine = new GameEngine();
	private Timer Timer = new Timer(1000/120,this);
	RenderEngine(JFrame Frame, GameEngine Engine){
		Timer.start();
		f = Frame;
		this.Engine = Engine;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Timer) {
			repaint();
		}
	}
	//private Map Map = new Map();
	private int i;
	//private JPanel Panel = new JPanel();
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Render(g);
	}
	
	private void Render(Graphics g) {
		Graphics2D g2d= (Graphics2D) g;
		
		if(Engine.GetState() == 0) {
			Menu Menu = Engine.GetMainMenu();
			for(i=0;i< Menu.GetNumberOfButtons(); i++) {
				g2d.setColor(Color.cyan);
				if(Menu.GetButton(i).Selected()) {
					g2d.setColor(Color.magenta);
				}
				g2d.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 200, 50);
				g2d.setColor(Color.BLACK);
				g2d.drawString(Menu.GetButton(i).GetName(), Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY());
				//g.drawImage(img, x, y, observer)
			}
		}
		else if(Engine.GetState() ==1) {
			Menu Menu = Engine.GetOptionsMenu();
			for(i=0;i< Menu.GetNumberOfButtons(); i++) {
				g2d.setColor(Color.cyan);
				if(Menu.GetButton(i).Selected()) {
					g2d.setColor(Color.magenta);
				}
				g2d.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 200, 50);
				g2d.setColor(Color.BLACK);
				g2d.drawString(Menu.GetButton(i).GetName(), Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY());
				//g.drawImage(img, x, y, observer)
			}
		}
		else if(Engine.GetState() == 2) {
			//Map
			g2d.drawImage(Engine.Getlevel().LoadMap(),0,0,f);
			
			//Enemies
			for(i=0;i <Engine.GetNumberOfEnemies(); i++) {
				//use this for drawing an sprite
				//g.drawImage(img, x, y, observer)
			}
			
			for(i=0;i <Engine.GetNumberOfObstacles(); i++) {
				Obstacle O = Engine.GetObstacle(i);
				g2d.setColor(Color.yellow);
				g2d.fillRect(O.GetX(), O.GetY(), O.GetWidth(), O.GetHeight());
			}
			
			//Player
			g2d.setColor(Color.blue);
			g2d.fillRect(Engine.GetPlayer().GetX(), Engine.GetPlayer().GetY(), 10, 10);	
		}	
		
		else if(Engine.GetState() ==3) {
			Menu Menu = Engine.GetSoundMenu();
			if (Engine.getVolume() == "0") {
			g2d.drawString("Mute", 300, 100);
			} else {
				g2d.drawString(Engine.getVolume(), 300, 100);
			}
			for(i=0;i< Menu.GetNumberOfButtons(); i++) {
				g2d.setColor(Color.cyan);
				if(Menu.GetButton(i).Selected()) {
					g2d.setColor(Color.magenta);
				}
				if(i == 0 || i ==1) {
					g2d.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 70, 50); 
				} else {
				g2d.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 200, 50); 
				}
				g2d.setColor(Color.BLACK);
				g2d.drawString(Menu.GetButton(i).GetName(), Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY());
				//g.drawImage(img, x, y, observer)
			}
		}
		
		
		
		
	}
}