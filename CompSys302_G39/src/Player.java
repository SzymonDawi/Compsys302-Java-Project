import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Character{
	
	spriteLoader loadPlayer = new spriteLoader();
	
	Sprite currentSprite;
	Animation attackFront;
	Animation attackBack;
	Animation attackLeft;
	Animation attackRight;
	Animation walkFront;
	Animation walkBack;
	Animation walkLeft;
	Animation walkRight;
	
	private void initPlayerAnimations() {
		
		
		BufferedImage currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Forward"); 
		Sprite currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackFrontSprites = new ArrayList<BufferedImage>();
		attackFrontSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		attackFrontSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		attackFrontSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		attackFront = new Animation(attackFrontSprites); //attack facing forwards animation
		attackFront.setSpeed(AttSpeed);
		
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Attack_Backwards"); 
		currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> attackBackSprites = new ArrayList<BufferedImage>(); 
		attackBackSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
		attackBackSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		attackBackSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		attackBackSprites.add(currentPlayerSprite.getSprite(96, 0, 32, 32)); //frame 4
		attackBack = new Animation(attackFrontSprites); //attack facing backwards animation
		attackBack.setSpeed(AttSpeed);
		
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
		
		currentSpriteSheet = loadPlayer.loadSprite("player/Player_Walk_Forward"); 
		currentPlayerSprite = new Sprite(currentSpriteSheet);
		ArrayList<BufferedImage> walkForwardSprites = new ArrayList<BufferedImage>(); 
		walkForwardSprites.add(currentPlayerSprite.getSprite(0, 0, 32, 32));  //frame 1
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
		walkLeftSprites.add(currentPlayerSprite.getSprite(32, 0, 32, 32)); //frame 2
		walkLeftSprites.add(currentPlayerSprite.getSprite(64, 0, 32, 32)); //frame 3
		walkLeftSprites.add(currentPlayerSprite.getSprite(96, 0, 32, 32)); //frame 4
		walkLeft = new Animation(walkLeftSprites); //walk facing right animation
		walkLeft.setSpeed(200);
		walkLeft.start();
	}
	
	
	public Player() {
		initPlayerAnimations();
		Health = 100;
		Damage = 100;
		Alive = true;
		AttSpeed = 200;
		X = 300;
		Y = 300;
		W = 11;
		H = 11;
	}
	
	public void TakeDamage(int i) {
		Health = Health - i;
	}
	
	public void Kill() {
		Alive = false;
	}
	
	public boolean IsAlive() {
		return Alive;
	}
	
	@Override
	public void SetAttSpeed(int NewAttSpeed) {
		AttSpeed = NewAttSpeed;
		attackRight.setSpeed(AttSpeed);
		attackLeft.setSpeed(AttSpeed);
		attackFront.setSpeed(AttSpeed);
		attackBack.setSpeed(AttSpeed);
	}
	
	
	public Animation getCurrentSprite(String Dirrection) {
		Animation temp = walkFront;
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
		return temp;
	}
	
	
}
