
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
	
	private BufferedImage friendlyProjectile;
	private BufferedImage enemyProjectile;
	private BufferedImage currentIcon;
	private BufferedImage meleeWeapon;
	private BufferedImage rangedWeapon;
	
	private attacks attacks = new attacks();
	
	private Color HUD_Background = new Color(0f,0f,0f,.35f ); //new color of 35% opacity
	private Color hurt = new Color(1f,0f,0f,.50f ); //new color of 25% opacity
	private Font buttonFont = new Font("Georgia", Font.BOLD,20);
	private Font titleFont = new Font("Georgia", Font.BOLD,40);
	private Font HUDFont = new Font("Georgia", Font.BOLD,15);
	
	
	private int i;
	
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
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Render(g);
	}
	
	private void Render(Graphics g) {
		Graphics2D g2d= (Graphics2D) g;
		if(Engine.GetState() == 0) {
			//Rendering Main menu
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
			//Rendering Options Menu
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
			//Rendering game scene
			
			//Map
			if(Engine.GetCentreMap()) {
				g2d.drawImage(Engine.Getlevel().LoadMap(), (1024 - Engine.Getlevel().GetMaxX())/2,(768-Engine.Getlevel().GetMaxY())/2,f);
			}
			else {
				g2d.drawImage(Engine.Getlevel().LoadMap(),0,0,f);
			}
			
			//Enemies
			for(i=0;i <Engine.GetNumberOfEnemies(); i++) {
				
				if(Engine.GetEnemy(i).getType() == "normal") {
					Enemy E = Engine.GetEnemy(i);
					
					g2d.drawImage(E.getEnemyAnimation().Sprite, E.GetX(),E.GetY(), 64,64,null);
					enemySprite = createImage(getWidth(),getHeight());
					drawEntity(enemySprite.getGraphics(), E.getEnemyAnimation());
				}else {
					boss B = (boss) Engine.GetEnemy(i);
					
					g2d.drawImage(B.getEnemyAnimation().Sprite, B.GetX(),B.GetY(), 64,64,null);
					enemySprite = createImage(getWidth(),getHeight());
					drawEntity(enemySprite.getGraphics(), B.getEnemyAnimation());
	
					if(Engine.GetBossFightStatus()) {
						g2d.setColor(Color.red);
						g2d.fillRect(310,12,(int) (4.8*(((float)B.GetHealth()/(float)B.GetMaxHealth())*100)), 30);		
						
						currentIcon = iconLoader.loadSprite("MenusAndIcons/HUD_weaponBox");
						g2d.drawImage(currentIcon,250, 6, 50, 50,null);
						
						currentIcon = iconLoader.loadSprite("MenusAndIcons/bossIcon");
						g2d.drawImage(currentIcon,254, 10, 40, 40,null);
						
						currentIcon = iconLoader.loadSprite("MenusAndIcons/bossHealthBar");
						g2d.drawImage(currentIcon,300, 12, 500, 30,null); //health icon
						
						if(Engine.GetBossLockedOnStatus()) {
							currentIcon = iconLoader.loadSprite("Enemy/bossCrosshairLocked");
							g2d.drawImage(currentIcon,B.getCrosshairX(),B.getCrosshairY(), 64, 64,null); 
						} else {
							currentIcon = iconLoader.loadSprite("Enemy/bossCrosshairNormal");
							g2d.drawImage(currentIcon,B.getCrosshairX(),B.getCrosshairY(), 64, 64,null); 
						}
					}
				}
				
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
			if(Engine.isPlayerHit()) {
				g2d.setColor(hurt);
				g2d.fillRect(0,0,1024,768);
			}
			if (Engine.getPlayerAttacking()) {
				if(Engine.GetPlayer().getWeaponType() == "melee" || Engine.GetPlayer().getAmmo() >0) {
				g2d.drawImage(attacks.getAttackSprite("playerMeleeAttack", Engine.GetCurrentPlayerDirrection()), Engine.GetPlayerAttackLocation("x"),Engine.GetPlayerAttackLocation("y"), 64,64,null);
				}
			}
			
			//Obstacles
			for(i=0;i <Engine.GetNumberOfObstacles(); i++) {
				Obstacle O = Engine.GetObstacle(i);
				g2d.drawImage(O.GetCurrentSprite(), O.GetX(),O.GetY(), O.GetWidth(), O.GetHeight(),f);	
			}
			
			//projectiles
			friendlyProjectile = iconLoader.loadSprite("player/player_projectile");
			enemyProjectile = iconLoader.loadSprite("enemy/enemy_projectile"); 
			for(i =0 ; i <Engine.GetNumberOfProjectiles(); i++) {
				Projectile P = Engine.GetProjectile(i);
				if(P.getExists()) {
					if(Engine.GetProjectile(i).getFriendly()) {
						g2d.drawImage(friendlyProjectile,P.GetX(),P.GetY(), P.GetWidth(), P.GetHeight(),null);
					} else {
						g2d.drawImage(enemyProjectile,P.GetX(),P.GetY(), P.GetWidth()+10, P.GetHeight()+10,null);
					}
				}
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
			g2d.drawImage(currentIcon,610, 650, 70, 70,null); //inventory box icon
			
			if(Engine.GetPlayer().getHasKey() ) {
				currentIcon = iconLoader.loadSprite("MenusAndIcons/Inventory_key");
				g2d.drawImage(currentIcon,620, 660, 50, 50,null);
			}
			
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
			g2d.drawString((int)Engine.GetPlayer().GetHealth() + "/" +  Engine.GetPlayer().GetMaxHealth(), 125, 700);
			g2d.drawString("AMMO", 850, 700);
			g2d.drawString(Engine.GetPlayer().getAmmo() + "/" +  Engine.GetPlayer().getMaxAmmo(), 920, 700);
			g2d.drawString("TIME LEFT:", 25, 35);
			g2d.drawString(Engine.GetRemainingTime(), 120, 35);
			
			if(Engine.GetTutorialRoom()) {
				//drawing the controls
				currentIcon = iconLoader.loadSprite("MenusAndIcons/MovementKeys");
				g2d.drawImage(currentIcon,30, 200, 75, 45,null); 
				currentIcon = iconLoader.loadSprite("MenusAndIcons/attackKey");
				g2d.drawImage(currentIcon,35, 350, 60, 30,null); 
				currentIcon = iconLoader.loadSprite("MenusAndIcons/weaponKey");
				g2d.drawImage(currentIcon,45, 500, 30, 30,null); 
				currentIcon = iconLoader.loadSprite("MenusAndIcons/exitKey");
				g2d.drawImage(currentIcon,950, 275, 30, 30,null); 
				currentIcon = iconLoader.loadSprite("MenusAndIcons/pauseKey");
				g2d.drawImage(currentIcon,950, 400, 30, 30,null); 
				
				g2d.setColor(Color.BLACK);
				g2d.drawString("The robots have taken over the town!!!",340, 30); 
				g2d.drawString("The boss has locked itself inside the house",335, 45); 
				g2d.drawString("You must find the KEY and destroy the boss",335, 60); 
				g2d.drawString(": MOVE",130, 230); 
				g2d.drawString(": ATTACK",120, 370); 
				g2d.drawString(": SWAP WEAPONS",100, 520); 
				g2d.drawString("QUIT : ",870, 300); 
				g2d.drawString("PAUSE : ",860, 415); 
				
			}
			
		}	
		
		else if(Engine.GetState() ==3) {
			//Rendering Sound Menu
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
			//Rendering Socre Menu
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
			}
		}
		
		else if(Engine.GetState() ==5) {
			//Rendering Pause Menu
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
			}
		}
		
		else if(Engine.GetState() ==6) {
			//Rendering end Screen
			Menu Menu = Engine.GetDeadMenu();
			for(i=0;i< Menu.GetNumberOfButtons(); i++) {
				g2d.setColor(Color.cyan);
				if(Menu.GetButton(i).Selected()) {
					g2d.setColor(Color.magenta);
				}
				g2d.fillRect(Menu.GetButton(i).GetX(), Menu.GetButton(i).GetY(), 200, 50);
				g2d.setColor(Color.BLACK);
				g2d.setFont(buttonFont);
				g2d.drawString(Menu.GetButton(i).GetName(), Menu.GetButton(i).GetX()+70, Menu.GetButton(i).GetY()+28);
				g2d.setColor(Color.BLACK);
				g2d.setFont(titleFont);
				g2d.drawString("GAME OVER ",1024/2-130,100);
				g2d.drawString(Engine.GetPlayer().getDeathMessage(),1024/2-110,500);
				g2d.drawString("Final Score: "+ Engine.GetScore(),1024/2-200,300);
			}
		}
		
		else if(Engine.GetState() == 7) {
			//Rendering exit Menu
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