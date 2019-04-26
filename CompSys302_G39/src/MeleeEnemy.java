import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MeleeEnemy extends Enemy{

	MeleeEnemy() {
		Health = 100;
		Damage = 20;
	}
	
	private void initMeleeEnemyAnimations() {		
		
		BufferedImage currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Forward"); 
		Sprite currentEnemySprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackFrontSprites = new ArrayList<BufferedImage>();
		attackFrontSprites.add(currentEnemySprite.getSprite(0, 0, 32, 32));  //frame 1
		attackFrontSprites.add(currentEnemySprite.getSprite(32, 0, 32, 32)); //frame 2
		attackFrontSprites.add(currentEnemySprite.getSprite(64, 0, 32, 32)); //frame 3
		attackFront = new Animation(attackFrontSprites); //attack facing forwards animation
		attackFront.setSpeed(AttSpeed);
		attackFront.start();
		
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Backwards"); 
		currentEnemySprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackBackSprites = new ArrayList<BufferedImage>(); 
		attackBackSprites.add(currentEnemySprite.getSprite(0, 0, 32, 32));  //frame 1
		attackBackSprites.add(currentEnemySprite.getSprite(32, 0, 32, 32)); //frame 2
		attackBackSprites.add(currentEnemySprite.getSprite(64, 0, 32, 32)); //frame 3
		attackBackSprites.add(currentEnemySprite.getSprite(96, 0, 32, 32)); //frame 4
		attackBack = new Animation(attackBackSprites); //attack facing backwards animation
		attackBack.setSpeed(AttSpeed);
		attackBack.start();
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Left");  
		currentEnemySprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackLeftSprites = new ArrayList<BufferedImage>();
		attackLeftSprites.add(currentEnemySprite.getSprite(0, 0, 32, 32));  //frame 1
		attackLeftSprites.add(currentEnemySprite.getSprite(32, 0, 32, 32)); //frame 2
		attackLeftSprites.add(currentEnemySprite.getSprite(64, 0, 32, 32)); //frame 3
		attackLeftSprites.add(currentEnemySprite.getSprite(96, 0, 32, 32)); //frame 4
		attackLeftSprites.add(currentEnemySprite.getSprite(128, 0, 32, 32)); //frame 5
		attackLeft = new Animation(attackLeftSprites); //attack facing left animation
		attackLeft.setSpeed(AttSpeed);
		attackLeft.start();
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Right");  
		currentEnemySprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackRightSprites = new ArrayList<BufferedImage>();
		attackRightSprites.add(currentEnemySprite.getSprite(0, 0, 32, 32));  //frame 1
		attackRightSprites.add(currentEnemySprite.getSprite(32, 0, 32, 32)); //frame 2
		attackRightSprites.add(currentEnemySprite.getSprite(64, 0, 32, 32)); //frame 3
		attackRightSprites.add(currentEnemySprite.getSprite(96, 0, 32, 32)); //frame 4
		attackRightSprites.add(currentEnemySprite.getSprite(128, 0, 32, 32)); //frame 5
		attackRight = new Animation(attackRightSprites); //attack facing right animation
		attackRight.setSpeed(AttSpeed);
		attackRight.start();
		
		
		currentSpriteSheet = loadPlayer.loadSprite("Enemy/melee_enemy"); 
		
		currentEnemySprite = new Sprite(currentSpriteSheet);
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
	
	
	
	public MeleeEnemy(int x, int y, String direction) {
		initMeleeEnemyAnimations();
		Direction = direction;
		Health = 6;
		MaxHealth = 6;
		Damage = 1;
		Alive = true;
		Speed = 5;
		X = x;
		Y = y;
		W = 40;
		H = 50;
		aggro = false;
	}
	
	
}
