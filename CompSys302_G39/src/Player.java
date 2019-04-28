import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Character{
	
	protected int ammo;
	protected int maxAmmo;
	protected boolean hasKey;
	protected String currentWeapon;
	private int MainMapX;
	private int MainMapY;
	private String deathMessage;
	
	public Player() {
		initPlayerAnimations();
		deathMessage = "YOU DIED";
		hasKey = false;
		maxAmmo = 20;
		ammo = 20;
		Health = 20;
		MaxHealth = 20;
		Damage = 2;
		Alive = true;
		currentWeapon = "melee";
		AttSpeed = 100;

		X = 300;
		Y = 300;
		W = 30;
		H = 60;
		
		BoundsX= 0;
		BoundsY= 0;
		BoundsW= W;
		BoundsH= H;
		
		SpecialX = 20 ;
		SpecialY = 20;
		SpecialW = 70;
		SpecialH = 100;
	}
	
	public void Kill() {
		Alive = false;
		Health =0;
	}
	
	public boolean IsAlive() {
		return Alive;
	}

	public double attack() {
		if (currentWeapon == "ranged") {
			if(ammo>0) {
			ammo -= 1;
			} else {
				return 0;
			}
		} else {
			//melee attack animation
		}
		return Damage;
	}
	
	//Setters
	@Override
	public void SetAttSpeed(int NewAttSpeed) {
		AttSpeed = NewAttSpeed;
		attackRight.setSpeed(AttSpeed);
		attackLeft.setSpeed(AttSpeed);
		attackFront.setSpeed(AttSpeed);
		attackBack.setSpeed(AttSpeed);
	}
	
	public void setHasKey (boolean Key) {
		hasKey = Key;
	}
	
	public void setWeaponType (String weaponType) {
		currentWeapon = weaponType;
	}
	
	public void SetAmmo(int AmmoDelta) {
		ammo += AmmoDelta;
		if (ammo > maxAmmo) {
			ammo = maxAmmo;
		}else if (ammo< 0) {
			ammo = 0;
		}
	}
	
	public void SetMaxAmmo (int MaxAmmoDelta) {
		maxAmmo += MaxAmmoDelta;
	}
	
	public void SetDeathMessage(String newDeathMessage) {
		deathMessage = newDeathMessage;
	}
	
	public void SetMainMapX() {
		MainMapX = X;
	}
	
	public void SetMainMapY() {
		MainMapY = Y;
	}
	
	public void SetMainMapX(int X) {
		MainMapX = X;
	}
	
	public void SetMainMapY(int Y) {
		MainMapY = Y;
	}
	
	//Getters
	public boolean getHasKey () {
		return hasKey;
	}
	
	public int GetMainMapX() {
		return MainMapX;
	}
	
	public int GetMainMapY() {
		return MainMapY;
	}
	
	public int getMaxAmmo () {
		return maxAmmo;
	}
	
	public int getAmmo () {
		return ammo;
	}
	
	public String getDeathMessage() {
		return deathMessage;
	}
	
	public String getWeaponType () {
		return currentWeapon;
	}
	
	@Override
	public Rectangle GetSpecialBounds() {
		return new Rectangle(X-10,Y-10,W+50,H+40);
	}
	public Animation getCurrentSprite(String Dirrection, boolean standingStill, boolean isAttacking) {
		Animation temp = walkFront;
		
		if (isAttacking == false) {
			if(standingStill) {
				switch(Dirrection) {
				case "Left":
					temp = standLeft;
				
					break;
			
				case "Backwards":
					temp = standBack;
					break;
			
				case "Forward":
					temp = standFront;
					break;
			
				case "Right":
					temp = standRight;
					break;
				}
			} else {
				switch(Dirrection) {
				case "Left":
					temp = walkLeft;
					break;
			
				case "Backwards":
					temp = walkBack;
					break;
			
				case "Forward":
					temp = walkFront;
				break;
			
				case "Right":
					temp = walkRight;
				break;
				}
			}
		} else {
			switch(Dirrection) {
				case "Left":
					temp = attackLeft;
				break;
	
				case "Backwards":
					temp = attackBack;
				break;
	
				case "Forward":
					temp = attackFront;
				break;
	
				case "Right":
					temp = attackRight;
				break;
			
			}
		}
		
		return temp;
	}
	
	private void initPlayerAnimations() {		
		
		BufferedImage currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Forward"); 
		Sprite currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackFrontSprites = new ArrayList<BufferedImage>();
		attackFrontSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		attackFrontSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		attackFrontSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		attackFront = new Animation(attackFrontSprites); //attack facing forwards animation
		attackFront.setSpeed(AttSpeed);
		attackFront.start();
		
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Backwards"); 
		currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackBackSprites = new ArrayList<BufferedImage>(); 
		attackBackSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		attackBackSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		attackBackSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		attackBackSprites.add(currentPlayerSprite.getSprite(96, 0, 32, 32)); //frame 4
		attackBack = new Animation(attackBackSprites); //attack facing backwards animation
		attackBack.setSpeed(AttSpeed);
		attackBack.start();
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Left");  
		currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackLeftSprites = new ArrayList<BufferedImage>();
		attackLeftSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		attackLeftSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		attackLeftSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		attackLeftSprites.add(currentPlayerSprite.getSprite(96, 0, 32, 32)); //frame 4
		attackLeftSprites.add(currentPlayerSprite.getSprite(128, 0, 32, 32)); //frame 5
		attackLeft = new Animation(attackLeftSprites); //attack facing left animation
		attackLeft.setSpeed(AttSpeed);
		attackLeft.start();
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Right");  
		currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackRightSprites = new ArrayList<BufferedImage>();
		attackRightSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		attackRightSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		attackRightSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		attackRightSprites.add(currentPlayerSprite.getSprite(96, 0, 32, 32)); //frame 4
		attackRightSprites.add(currentPlayerSprite.getSprite(128, 0, 32, 32)); //frame 5
		attackRight = new Animation(attackRightSprites); //attack facing right animation
		attackRight.setSpeed(AttSpeed);
		attackRight.start();
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Walk_Forward"); 
		currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> walkForwardSprites = new ArrayList<BufferedImage>(); 
		walkForwardSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		standFront = new Animation(walkForwardSprites); //stand facing front animation
		standFront.setSpeed(100000);
		standFront.start();
	
		walkForwardSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		walkForwardSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		walkForwardSprites.add(currentPlayerSprite.getSprite(96, 0, 32, 32)); //frame 4
		walkFront = new Animation(walkForwardSprites); //walk facing front animation
		walkFront.setSpeed(200);
		walkFront.start();
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Walk_Backwards"); 
		currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> walkBackSprites = new ArrayList<BufferedImage>(); 
		walkBackSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		standBack = new Animation(walkBackSprites); //stand facing back animation
		standBack.setSpeed(100000);
		standBack.start();
		walkBackSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		walkBackSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		walkBackSprites.add(currentPlayerSprite.getSprite(96, 0, 32, 32)); //frame 4
		walkBack = new Animation(walkBackSprites); //walk facing back animation
		walkBack.setSpeed(200);
		walkBack.start();
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Walk_Right"); 
		currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> walkRightSprites = new ArrayList<BufferedImage>(); 
		walkRightSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		standRight = new Animation(walkRightSprites); //stand facing right animation
		standRight.setSpeed(100000);
		standRight.start();
		walkRightSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		walkRightSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		walkRightSprites.add(currentPlayerSprite.getSprite(96, 0, 32, 32)); //frame 4
		walkRight = new Animation(walkRightSprites); //walk facing right animation
		walkRight.setSpeed(200);
		walkRight.start();
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Walk_Left"); 
		currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> walkLeftSprites = new ArrayList<BufferedImage>(); 
		walkLeftSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		standLeft = new Animation(walkLeftSprites); //walk facing right animation
		standLeft.setSpeed(100000);
		standLeft.start();
		walkLeftSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		walkLeftSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		walkLeftSprites.add(currentPlayerSprite.getSprite(96, 0, 32, 32)); //frame 4
		walkLeft = new Animation(walkLeftSprites); //walk facing right animation
		walkLeft.setSpeed(200);
		walkLeft.start();
	}
}