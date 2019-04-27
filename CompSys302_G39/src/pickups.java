import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class pickups extends Entity {

	spriteLoader loadPlayer = new spriteLoader();
	protected int scoreValue;
	private Animation coinAnimation;
	private Animation ammoAnimation;
	private Animation healthAnimation;
	private String type;
	
private void initPickupAnimations() {
		
		
		BufferedImage coin = loadPlayer.loadSprite("pickups/coin"); 
		Sprite coins= new Sprite(coin);
		ArrayList<BufferedImage> coinAnimationSprites = new ArrayList<BufferedImage>();
		coinAnimationSprites.add(coins.getSprite(0, 0, 32, 32));  //frame 1
		coinAnimationSprites.add(coins.getSprite(32, 0, 32, 32)); //frame 2
		coinAnimationSprites.add(coins.getSprite(64, 0, 32, 32)); //frame 3
		coinAnimationSprites.add(coins.getSprite(96, 0, 32, 32)); //frame 4
		coinAnimationSprites.add(coins.getSprite(128, 0, 32, 32)); //frame 5
		coinAnimation = new Animation(coinAnimationSprites); //coin animation
		coinAnimation.setSpeed(200);
		coinAnimation.start();
		
		BufferedImage ammo = loadPlayer.loadSprite("pickups/Ammo"); 
		Sprite Ammo= new Sprite(ammo);
		ArrayList<BufferedImage> ammoAnimationSprites = new ArrayList<BufferedImage>();
		ammoAnimationSprites.add(Ammo.getSprite(0, 0, 32, 32));  //frame 1
		ammoAnimationSprites.add(Ammo.getSprite(32, 0, 32, 32)); //frame 2
		ammoAnimation = new Animation(ammoAnimationSprites); //coin animation
		ammoAnimation.setSpeed(600);
		ammoAnimation.start();
		
		BufferedImage health = loadPlayer.loadSprite("pickups/health"); 
		Sprite healthSprite= new Sprite(health);
		ArrayList<BufferedImage> healthAnimationSprites = new ArrayList<BufferedImage>();
		healthAnimationSprites.add(healthSprite.getSprite(0, 0, 32, 32));  //frame 1
		healthAnimationSprites.add(healthSprite.getSprite(32, 0, 32, 32)); //frame 2
		healthAnimation = new Animation(healthAnimationSprites); //coin animation
		healthAnimation.setSpeed(600);
		healthAnimation.start();
	}
		
	pickups(String type, int x, int y){
		X = x;
		Y = y;
		initPickupAnimations();
		this.type = type;
	}
	
	public Animation getPickupAnimation() {
		Animation temp = null;
		switch(type) {
		case "coin":
			temp = coinAnimation;
		break;
		case "ammo":
			temp = ammoAnimation;
		break;
		case "health":
			temp = healthAnimation;
		break;
		}
	
		return temp;
	}
	
	
	public String getType() {
		return type;
	}
	
	public int getPickupValue() {
		int temp = 0;
		switch(type) {
		case "coin":
			temp = 100;
		break;
		case "ammo":
			temp = 20;
		break;
		case "health":
			temp = 10;
		break;
		}
		
	
		return temp;
	}
}
