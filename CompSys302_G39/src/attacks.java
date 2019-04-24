import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class attacks extends Entity {

	spriteLoader loadSprite = new spriteLoader();
	
	//list of attack sprites
	Sprite playerMeleeAttack = new Sprite( loadSprite.loadSprite("player/melee_attack")); 
	
	private int DirectionToIndex(String Direction) {
		int temp = 0;
		switch (Direction) {
		case "Right":
			temp = 1;
		break;
		
		case "Backwards":
			temp = 2;
		break;
		
		case "Left":
			temp = 3;
		break;
		
		case "Forward":
			temp = 4;
		break;	
		}
		return temp;
	}
	
	public BufferedImage getAttackSprite(String attack, String Direction) {
		int index = DirectionToIndex(Direction);
		BufferedImage	temp = null;
	
		if (attack == "playerMeleeAttack") {
			temp = playerMeleeAttack.getSprite(index*32 - 32, 0, 32, 32);
		}
		return temp;
	}
	
}

