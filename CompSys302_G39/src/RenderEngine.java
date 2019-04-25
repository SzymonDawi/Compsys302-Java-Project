
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class RenderEngine extends JPanel implements ActionListener{
	private JFrame f = new JFrame();
	private GameEngine Engine = new GameEngine();
	private Timer Timer = new Timer(1000/120,this);
	private spriteLoader iconLoader = new spriteLoader();
	private String PlayerDirrection;
	private Animation currentAnimation;
	private Image playerSprite;
	private Image pickupSprite;
	private BufferedImage currentIcon;
	private BufferedImage meleeWeapon;
	private BufferedImage rangedWeapon;
	private attacks attacks = new attacks();
	private Color HUD_Background = new Color(0f,0f,0f,.35f ); //new color of 25% opacity
	private Font buttonFont = new Font("Georgia", Font.BOLD,20);
	private Font HUDFont = new Font("Georgia", Font.PLAIN,15);
	
	RenderEngine(JFrame Frame, GameEngine Engine){
		Timer.start();
		f = Frame;
		this.Engine = Engine;
		PlayerDirrection = "Forward";
		currentAnimation = Engine.GetPlayer().getCurrentSprite(PlayerDirrection, Engine.playerIsStandingStill(), Engine.PlayerAttack());
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == Timer) {
			PlayerDirrection = Engine.GetCurrentPlayerDirrection();
			currentAnimation = Engine.GetPlayer().getCurrentSprite(PlayerDirrection, Engine.playerIsStandingStill(), Engine.PlayerAttack());
			repaint();
		}
	}
	
	public void drawEntity (Graphics g, Animation EntityAnimation){
		if(EntityAnimation != null) {
			EntityAnimation.update(System.currentTimeMillis());
			g.drawImage(EntityAnimation.Sprite, Engine.GetPlayer().GetX(),Engine.GetPlayer().GetY(), 32,32,null);
			
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
		currentIcon = iconLoader.loadSprite("MenusAndIcons/MenuBG");
		if(Engine.GetState() == 0) {
			g2d.drawImage(currentIcon, 0,0, 1024,768,null);
			Menu Menu = Engine.GetMainMenu();
			for(i=0;i< Menu.GetNumberOfButtons(); i++) {
				g2d.setColor(Color.cyan);
				if(Menu.GetButton(i).Selected()) {
					g2d.setColor(Color.magenta);
				}
				g2d.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 200, 50);
				g2d.setColor(Color.BLACK);
				g2d.setFont(buttonFont);
				g2d.drawString(Menu.GetButton(i).GetName(), Menu.GetButton(i).GetX()+70, Menu.GetButton(i).GetY()+28);
				//g.drawImage(img, x, y, observer)
			}
		}
		else if(Engine.GetState() ==1) {
			g2d.drawImage(currentIcon, 0,0, 1024,768,null);
			Menu Menu = Engine.GetOptionsMenu();
			for(i=0;i< Menu.GetNumberOfButtons(); i++) {
				g2d.setColor(Color.cyan);
				if(Menu.GetButton(i).Selected()) {
					g2d.setColor(Color.magenta);
				}
				g2d.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 200, 50);
				g2d.setColor(Color.BLACK);
				g2d.setFont(buttonFont);
				g2d.drawString(Menu.GetButton(i).GetName(), Menu.GetButton(i).GetX()+70, Menu.GetButton(i).GetY()+28);
				//g.drawImage(img, x, y, observer)
			}
		}
		else if(Engine.GetState() == 2) {
			
			//Map
			g2d.drawImage(Engine.Getlevel().LoadMap(),0,0,f);
			
			//Player
			g2d.drawImage(currentAnimation.Sprite, Engine.GetPlayer().GetX(),Engine.GetPlayer().GetY(), 32,32,null);
			playerSprite = createImage(getWidth(),getHeight());
			drawEntity(playerSprite.getGraphics(), currentAnimation);
			if (Engine.getPlayerAttacking()) {
				g2d.drawImage(attacks.getAttackSprite("playerMeleeAttack", Engine.GetCurrentPlayerDirrection()), Engine.GetPlayerAttackLocation("x"),Engine.GetPlayerAttackLocation("y"), 32,32,null);
			}
			
			//Enemies
			for(i=0;i <Engine.GetNumberOfEnemies(); i++) {
				//use this for drawing an sprite
				//g.drawImage(img, x, y, observer)
			}
			
			//pickups
			for(i=0;i <Engine.GetNumberOfPickups(); i++) {
				pickups P = Engine.GetPickup(i);
				g2d.drawImage(P.getPickupAnimation().Sprite, P.GetX(),P.GetY(), 32,32,null);
				pickupSprite = createImage(getWidth(),getHeight());
				drawEntity(pickupSprite.getGraphics(), P.getPickupAnimation());
				
			}
			
			for(i=0;i <Engine.GetNumberOfObstacles(); i++) {
				Obstacle O = Engine.GetObstacle(i);
				g2d.setColor(Color.yellow);
				g2d.fillRect(O.GetX(), O.GetY(), O.GetWidth(), O.GetHeight());
			}
			
			//HUD
			meleeWeapon = iconLoader.loadSprite("MenusAndIcons/HUD_weaponMelee");
			rangedWeapon = iconLoader.loadSprite("Error"); //replace with ranged weapon when done
			currentIcon = iconLoader.loadSprite("MenusAndIcons/HUD_score");
			g2d.setFont(HUDFont);
			g2d.setColor(HUD_Background);
			g2d.drawImage(currentIcon,850, 10, 160, 34,null); //score icon
			currentIcon = iconLoader.loadSprite("MenusAndIcons/HUD_health");
			g2d.drawImage(currentIcon,80, 675, 190, 40,null); //health icon
			currentIcon = iconLoader.loadSprite("MenusAndIcons/HUD_weaponBox");
			g2d.drawImage(currentIcon,680, 640, 70, 70,null); //weapon box icon
			g2d.fillRect(800, 650, 140, 60); //ammo
			if(Engine.GetPlayer().getWeaponType() == "melee") {   //to display weapons
				g2d.drawImage(meleeWeapon,690, 650, 50, 50,null); //melee weapon icon
			} else {
				g2d.drawImage(rangedWeapon,690, 650, 50, 50,null); //ranged weapon icon
			}
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawString("Score:", 880, 32);
			g2d.drawString(Engine.GetScore(), 940, 32);
			g2d.drawString("Health", 120, 700);
			g2d.drawString(Engine.GetPlayer().GetHealth() + "/" +  Engine.GetPlayer().GetMaxHealth(), 190, 700);
			g2d.drawString(Engine.GetPlayer().getWeaponType(), 820, 685);
			g2d.drawString("AMMO", 880, 670);
			g2d.drawString(Engine.GetPlayer().getAmmo() + "/" +  Engine.GetPlayer().getMaxAmmo(), 880, 700);
			
		}	
		
		else if(Engine.GetState() ==3) {
			g2d.drawImage(currentIcon, 0,0, 1024,768,null);
			Menu Menu = Engine.GetSoundMenu();
			g2d.setColor(Color.cyan);
			g2d.fillRect(1024/2-25, 100, 150, 50);
			g2d.setColor(Color.BLACK);
			g2d.setFont(buttonFont);
			if (Engine.getVolume() == "0") {
			g2d.drawString("Mute", 1024/2+20, 130);
			} else {
				g2d.drawString(Engine.getVolume(), 1024/2+28, 130);
			}
			for(i=0;i< Menu.GetNumberOfButtons(); i++) {
				g2d.setColor(Color.cyan);
				if(Menu.GetButton(i).Selected()) {
					g2d.setColor(Color.magenta);
				}
				if(i == 0 || i ==1) {
					g2d.fillRect(Menu.GetButton(i).GetX()-50, Menu.GetButton(i).GetY(), 200, 50); 
				} else {
				g2d.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 200, 50); 
				}
				g2d.setColor(Color.BLACK);
				g2d.setFont(buttonFont);
				g2d.drawString(Menu.GetButton(i).GetName(), Menu.GetButton(i).GetX()+70, Menu.GetButton(i).GetY()+28);
				//g.drawImage(img, x, y, observer)
			}
		}
	
		else if(Engine.GetState() ==4) {
			g2d.drawImage(currentIcon, 0,0, 1024,768,null);
			Menu Menu = Engine.GetScoreMenu();
			for(i=0;i< Menu.GetNumberOfButtons(); i++) {
				g2d.setColor(Color.cyan);
				if(Menu.GetButton(i).Selected()) {
					g2d.setColor(Color.magenta);
				}
				g2d.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 200, 50);
				g2d.setColor(Color.BLACK);
				g2d.setFont(buttonFont);
				g2d.drawString(Menu.GetButton(i).GetName(), Menu.GetButton(i).GetX()+70, Menu.GetButton(i).GetY()+28);
				
				g2d.setColor(HUD_Background);
				g2d.fillRect(295,100, 370, 450);
				g2d.setColor(Color.white);
				g2d.setFont(buttonFont);
				for (int i = 0; i<10;i++) {
					String place = String.valueOf(i+1);
					if(i ==9) {
						g2d.drawString(place +"----------------------------" +Engine.GetScoreEngine().getHighScore(i), 310, 135+(i*42));
					} else {
						g2d.drawString(place +"------------------------------" +Engine.GetScoreEngine().getHighScore(i), 310, 135+(i*42));
					}
				}
				
				//g.drawImage(img, x, y, observer)
			}
		}
	}
}