
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
	private Image enemySprite;
	private BufferedImage currentIcon;
	private BufferedImage meleeWeapon;
	private BufferedImage rangedWeapon;
	private attacks attacks = new attacks();
	private Color HUD_Background = new Color(0f,0f,0f,.35f ); //new color of 25% opacity
	private Font buttonFont = new Font("Georgia", Font.BOLD,20);
	private Font titleFont = new Font("Georgia", Font.BOLD,40);
	private Font HUDFont = new Font("Georgia", Font.BOLD,15);
	
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
			if(Engine.GetCentreMap()) {
				g2d.drawImage(Engine.Getlevel().LoadMap(), (1024 - Engine.Getlevel().GetMaxX())/2,(768-Engine.Getlevel().GetMaxY())/2,f);
			}
			else {
				g2d.drawImage(Engine.Getlevel().LoadMap(),0,0,f);
			}
			
			//Enemies
			for(i=0;i <Engine.GetNumberOfEnemies(); i++) {
				Enemy E = Engine.GetEnemy(i);
				//g2d.setColor(Color.RED);
				//g2d.drawRect(E.GetX(),E.GetY(), 32,32);
				g2d.drawImage(E.getEnemyAnimation().Sprite, E.GetX(),E.GetY(), 64,64,null);
				enemySprite = createImage(getWidth(),getHeight());
				drawEntity(enemySprite.getGraphics(), E.getEnemyAnimation());
				//g2d.setColor(Color.yellow);
				//g2d.fillRect(E.GetBounds().x,E.GetBounds().y, E.GetWidth(), E.GetHeight());
			}
			
			//pickups
			for(i=0;i <Engine.GetNumberOfPickups(); i++) {
				pickups P = Engine.GetPickup(i);
				g2d.drawImage(P.getPickupAnimation().Sprite, P.GetX(),P.GetY(), 32,32,null);
				pickupSprite = createImage(getWidth(),getHeight());
				drawEntity(pickupSprite.getGraphics(), P.getPickupAnimation());
				
			}
			
			//Player
			g2d.drawImage(currentAnimation.Sprite, Engine.GetPlayer().GetX(),Engine.GetPlayer().GetY(), 64,64,null);
			playerSprite = createImage(getWidth(),getHeight());
			drawEntity(playerSprite.getGraphics(), currentAnimation);
			if (Engine.getPlayerAttacking()) {
				if(Engine.GetPlayer().getWeaponType() == "melee" || Engine.GetPlayer().getAmmo() >0) {
				g2d.drawImage(attacks.getAttackSprite("playerMeleeAttack", Engine.GetCurrentPlayerDirrection()), Engine.GetPlayerAttackLocation("x"),Engine.GetPlayerAttackLocation("y"), 64,64,null);
				}
			}
			
			//Obstacles
			for(i=0;i <Engine.GetNumberOfObstacles(); i++) {
				Obstacle O = Engine.GetObstacle(i);
				g2d.drawImage(O.GetCurrentSprite(), O.GetX(),O.GetY(), O.GetWidth(), O.GetHeight(),f);
				g2d.setColor(Color.yellow);
				g2d.drawRect(O.GetBounds().x,O.GetBounds().y, O.GetBounds().width, O.GetBounds().height);		
			}
			
			//HUD
			meleeWeapon = iconLoader.loadSprite("MenusAndIcons/HUD_weaponMelee");
			rangedWeapon = iconLoader.loadSprite("MenusAndIcons/HUD_weaponRanged"); 
			currentIcon = iconLoader.loadSprite("MenusAndIcons/HUD_score");
			g2d.setFont(HUDFont);
			g2d.setColor(HUD_Background);
			g2d.drawImage(currentIcon,850, 10, 160, 34,null); //score icon
			currentIcon = iconLoader.loadSprite("MenusAndIcons/HUD_health");
			g2d.drawImage(currentIcon,15, 675, 190, 40,null); //health icon
			currentIcon = iconLoader.loadSprite("MenusAndIcons/HUD_weaponBox");
			g2d.drawImage(currentIcon,680, 650, 70, 70,null); //weapon box icon
			currentIcon = iconLoader.loadSprite("MenusAndIcons/HUD_ammo");
			g2d.drawImage(currentIcon,800, 675, 190, 40,null); //ammo icon
			currentIcon = iconLoader.loadSprite("MenusAndIcons/HUD_timer");
			g2d.drawImage(currentIcon,15, 12, 190, 40,null); //timer icon
			if(Engine.GetPlayer().getWeaponType() == "melee") {   //to display weapons
				g2d.drawImage(meleeWeapon,690, 660, 50, 50,null); //melee weapon icon
			} else {
				g2d.drawImage(rangedWeapon,690, 660, 50, 50,null); //ranged weapon icon
			}
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.drawString("SCORE:", 878, 32);
			g2d.drawString(Engine.GetScore(), 940, 32);
			g2d.drawString("HEALTH", 55, 700);
			g2d.drawString(Engine.GetPlayer().GetHealth() + "/" +  Engine.GetPlayer().GetMaxHealth(), 125, 700);
			g2d.drawString("AMMO", 850, 700);
			g2d.drawString(Engine.GetPlayer().getAmmo() + "/" +  Engine.GetPlayer().getMaxAmmo(), 920, 700);
			g2d.drawString("TIME LEFT:", 25, 35);
			g2d.drawString(Engine.GetRemainingTime(), 120, 35);
			
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
		
		else if(Engine.GetState() ==5) {
			Menu Menu = Engine.GetPauseMenu();
			g2d.setColor(Color.BLACK);
			g2d.setFont(titleFont);
			g2d.drawString("PAUSED",1024/2-100,100);
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
		
		else if(Engine.GetState() == 6) {
			Menu Menu = Engine.GetCloseMenu();
			g2d.setColor(Color.BLACK);
			g2d.setFont(titleFont);
			g2d.drawString("Are you sure?",1024/2-130,100);
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
	
	}
}