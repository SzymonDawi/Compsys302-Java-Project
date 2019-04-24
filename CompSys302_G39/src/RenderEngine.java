
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RenderEngine extends JPanel implements ActionListener{
	private JFrame f = new JFrame();
	private GameEngine Engine = new GameEngine();
	private Timer Timer = new Timer(1000/120,this);
	private String PlayerDirrection;
	private Animation currentAnimation;
	private Image playerSprite;
	private Color HUD_Background = new Color(0f,0f,0f,.35f ); //new color of 25% opacity
	
	RenderEngine(JFrame Frame, GameEngine Engine){
		Timer.start();
		f = Frame;
		this.Engine = Engine;
		PlayerDirrection = "Forward";
		currentAnimation = Engine.GetPlayer().getCurrentSprite(PlayerDirrection, Engine.playerIsStandingStill());
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Timer) {
			PlayerDirrection = Engine.GetCurrentPlayerDirrection();
			currentAnimation = Engine.GetPlayer().getCurrentSprite(PlayerDirrection, Engine.playerIsStandingStill());
			repaint();
		}
	}
	
	public void drawPlayer (Graphics g){
		if(currentAnimation != null) {
			currentAnimation.update(System.currentTimeMillis());
			g.drawImage(currentAnimation.Sprite, Engine.GetPlayer().GetX(),Engine.GetPlayer().GetY(), 32,32,null);
			
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
			
			//Player
			g2d.drawImage(currentAnimation.Sprite, Engine.GetPlayer().GetX(),Engine.GetPlayer().GetY(), 32,32,null);
			playerSprite = createImage(getWidth(),getHeight());
			drawPlayer(playerSprite.getGraphics());
			
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
			
			//HUD
			g2d.setColor(HUD_Background);
			g2d.fillRect(80, 650, 100, 60);
			g2d.fillRect(800, 650, 140, 60);
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawString("Health", 110, 670);
			g2d.drawString(Engine.GetPlayer().GetHealth() + "/" +  Engine.GetPlayer().GetMaxHealth(), 110, 700);
			g2d.drawString(Engine.GetPlayer().getWeaponType(), 820, 685);
			g2d.drawString("AMMO", 880, 670);
			g2d.drawString(Engine.GetPlayer().getAmmo() + "/" +  Engine.GetPlayer().getMaxAmmo(), 880, 700);
			
		}	
		
		else if(Engine.GetState() ==3) {
			Menu Menu = Engine.GetSoundMenu();
			if (Engine.getVolume() == "0") {
			g2d.drawString("Mute", 300, 100);
			} else {
				g2d.drawString(Engine.getVolume(), 1024/2, 100);
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