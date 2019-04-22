import java.awt.image.BufferedImage;

public class Player extends Character{
	spriteLoader playerSpriteSheet = new spriteLoader();
	BufferedImage spriteSheet = playerSpriteSheet.loadSprite("player");
	//Sprite playerSprite = new Sprite(spriteSheet);
	
	public Player() {
		Health = 100;
		Damage = 100;
		Alive = true;
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
	
}
