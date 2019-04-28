import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class boss extends Enemy{

	private int crosshairX;
	private int crosshairY;
	
	public boss(int x, int y, String direction) {
		initAnimations();
		type = "boss";
		Direction = direction;
		Health = 50;
		MaxHealth = 50;
		Damage = 1;
		Alive = true;
		Speed = 1;
		X = x;
		Y = y;
		crosshairX = x;
		crosshairY = y;
		W = 96;
		H = 96;
		aggro = false;
	}
	
	//Mechanics of the boss attacks
	//This method computes the movement of the boss Crosshair mechanic
	//it takes the target x and y and the tickcount and uses these to
	//Calculate the movement
	public void lockOnPlayer (int xTarget, int yTarget, int TickCount) {
		if (!aggro ) {
			return;
		}
		
		int Xdifference = crosshairX - xTarget;
		int Ydifference =  crosshairY - yTarget;
		
		String movementPriority;
		
		if((Math.abs(Xdifference)< Math.abs(Ydifference)) && Math.abs(Xdifference)>Speed) {
			movementPriority = "x";
		} else if(Math.abs(Ydifference)>Speed){
			movementPriority = "y";
		}else if(Math.abs(Xdifference)>Speed) {	
			movementPriority = "x";
		} else {
			return;
		}
		
		if (movementPriority == "x") {
			if(Xdifference < 0) {
				crosshairX +=5;
			} else {
				crosshairX -=5;
			}
				
		} else {
			if(Ydifference < 0) {
				crosshairY+=5;
			} else {
				crosshairY-=5;
			}
		}	
	}
	
	public int getCrosshairX() {
		return crosshairX;
	}
	
	public int getCrosshairY() {
		return crosshairY;
	}
	
	
	//init for the boss animations
	private void initAnimations() {
		
		BufferedImage currentSpriteSheet = loadPlayer.loadSprite("Enemy/boss"); 
		
		Sprite currentBossSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> walkForwardSprites = new ArrayList<BufferedImage>(); 
		walkForwardSprites.add(currentBossSprite.getSprite(0, 64, 32, 32));  //frame 1
		standFront = new Animation(walkForwardSprites); //stand facing front animation
		standFront.setSpeed(100000);
		standFront.start();
	
		walkForwardSprites.add(currentBossSprite.getSprite(32, 64, 32, 32)); //frame 2
		walkFront = new Animation(walkForwardSprites); //walk facing front animation
		walkFront.setSpeed(200);
		walkFront.start();
		
		
		ArrayList<BufferedImage> walkBackSprites = new ArrayList<BufferedImage>(); 
		walkBackSprites.add(currentBossSprite.getSprite(0, 96, 32, 32));  //frame 1
		standBack = new Animation(walkBackSprites); //stand facing back animation
		standBack.setSpeed(100000);
		standBack.start();
		
		walkBackSprites.add(currentBossSprite.getSprite(32, 96, 32, 32)); //frame 2
		walkBack = new Animation(walkBackSprites); //walk facing back animation
		walkBack.setSpeed(200);
		walkBack.start();
		
		
		ArrayList<BufferedImage> walkRightSprites = new ArrayList<BufferedImage>(); 
		walkRightSprites.add(currentBossSprite.getSprite(0, 0, 32, 32));  //frame 1
		standRight = new Animation(walkRightSprites); //stand facing right animation
		standRight.setSpeed(100000);
		standRight.start();
		
		walkRightSprites.add(currentBossSprite.getSprite(32, 0, 32, 32)); //frame 2
		walkRight = new Animation(walkRightSprites); //walk facing right animation
		walkRight.setSpeed(200);
		walkRight.start();
		
		
		ArrayList<BufferedImage> walkLeftSprites = new ArrayList<BufferedImage>(); 
		walkLeftSprites.add(currentBossSprite.getSprite(0, 32, 32, 32));  //frame 1
		standLeft = new Animation(walkLeftSprites); //walk facing right animation
		standLeft.setSpeed(100000);
		standLeft.start();
		
		walkLeftSprites.add(currentBossSprite.getSprite(32, 32, 32, 32)); //frame 2
		walkLeft = new Animation(walkLeftSprites); //walk facing right animation
		walkLeft.setSpeed(200);
		walkLeft.start();
	}
}
