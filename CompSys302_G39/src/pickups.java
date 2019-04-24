import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class pickups extends Entity {

	spriteLoader loadPlayer = new spriteLoader();
	protected int scoreValue;
	Animation coinAnimation;
	String type;
	
private void initPickupAnimations() {
		
		
		BufferedImage coin = loadPlayer.loadSprite("pickups/coin"); 
		Sprite coins= new Sprite(coin);
		ArrayList<BufferedImage> coinAnimationSprites = new ArrayList<BufferedImage>();
		coinAnimationSprites.add(coins.getSprite(0, 0, 32, 32));  //frame 1
		coinAnimationSprites.add(coins.getSprite(32, 0, 32, 32)); //frame 2
		coinAnimationSprites.add(coins.getSprite(64, 0, 32, 32)); //frame 3
		coinAnimationSprites.add(coins.getSprite(96, 0, 32, 32)); //frame 3
		coinAnimationSprites.add(coins.getSprite(128, 0, 32, 32)); //frame 3
		coinAnimation = new Animation(coinAnimationSprites); //coin animation
		
	}
		
	pickups(String type, int x, int y){
		X = x;
		Y = y;
		initPickupAnimations();
		this.type = type;
	}
	
	public Animation getPickupSprite(String pickUp) {
		Animation temp = null;
		switch(pickUp) {
		case "coin":
			temp = coinAnimation;
		break;
		}
	
		return temp;
	}
	
	public int getPickupValue() {
		int temp = 0;
		switch(type) {
		case "coin":
			temp = 100;
		break;
		}
	
		return temp;
	}
}
