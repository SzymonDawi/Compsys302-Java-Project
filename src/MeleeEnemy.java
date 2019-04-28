import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MeleeEnemy extends Enemy{
	public MeleeEnemy(int x, int y, String direction) {
		initMeleeEnemyAnimations();
		type = "normal";
		Direction = direction;
		Health = 6;
		MaxHealth = 6;
		Damage = 0.05;
		Alive = true;
		Speed = 1;
		X = x;
		Y = y;
		W = 64;
		H = 64;
		aggro = false;
	}
	
	//loads Animations 
	private void initMeleeEnemyAnimations() {		
			
		BufferedImage currentSpriteSheet = loadPlayer.loadSprite("Enemy/melee_enemy"); 
		
		Sprite currentEnemySprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> walkForwardSprites = new ArrayList<BufferedImage>(); 
		walkForwardSprites.add(currentEnemySprite.getSprite(0, 64, 32, 32));  //frame 1
		standFront = new Animation(walkForwardSprites); //stand facing front animation
		standFront.setSpeed(100000);
		standFront.start();
	
		walkForwardSprites.add(currentEnemySprite.getSprite(32, 64, 32, 32)); //frame 2
		walkFront = new Animation(walkForwardSprites); //walk facing front animation
		walkFront.setSpeed(200);
		walkFront.start();
		
		
		ArrayList<BufferedImage> walkBackSprites = new ArrayList<BufferedImage>(); 
		walkBackSprites.add(currentEnemySprite.getSprite(0, 96, 32, 32));  //frame 1
		standBack = new Animation(walkBackSprites); //stand facing back animation
		standBack.setSpeed(100000);
		standBack.start();
		
		walkBackSprites.add(currentEnemySprite.getSprite(32, 96, 32, 32)); //frame 2
		walkBack = new Animation(walkBackSprites); //walk facing back animation
		walkBack.setSpeed(200);
		walkBack.start();
		
		
		ArrayList<BufferedImage> walkRightSprites = new ArrayList<BufferedImage>(); 
		walkRightSprites.add(currentEnemySprite.getSprite(0, 0, 32, 32));  //frame 1
		standRight = new Animation(walkRightSprites); //stand facing right animation
		standRight.setSpeed(100000);
		standRight.start();
		
		walkRightSprites.add(currentEnemySprite.getSprite(32, 0, 32, 32)); //frame 2
		walkRight = new Animation(walkRightSprites); //walk facing right animation
		walkRight.setSpeed(200);
		walkRight.start();
		
		
		ArrayList<BufferedImage> walkLeftSprites = new ArrayList<BufferedImage>(); 
		walkLeftSprites.add(currentEnemySprite.getSprite(0, 32, 32, 32));  //frame 1
		standLeft = new Animation(walkLeftSprites); //walk facing right animation
		standLeft.setSpeed(100000);
		standLeft.start();
		
		walkLeftSprites.add(currentEnemySprite.getSprite(32, 32, 32, 32)); //frame 2
		walkLeft = new Animation(walkLeftSprites); //walk facing right animation
		walkLeft.setSpeed(200);
		walkLeft.start();
	}
	
}
